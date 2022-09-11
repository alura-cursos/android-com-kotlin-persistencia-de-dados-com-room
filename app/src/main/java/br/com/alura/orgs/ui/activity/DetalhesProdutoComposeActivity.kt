package br.com.alura.orgs.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.extensions.formataParaMoedaBrasileira
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.activity.ui.theme.montserratFontFamily
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.appcompattheme.AppCompatTheme
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import java.math.BigDecimal

class DetalhesProdutoComposeActivity : ComponentActivity() {

    private var produtoId: Long = 0L
    private val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tentaCarregarProduto()

        setContent {
            AppCompatTheme {
                var produtoState: Produto? by remember {
                    mutableStateOf(
                        produtoDao.buscaPorId(
                            produtoId
                        )
                    )
                }

                Surface {
                    PreencheCampos(produtoState)
                    BuscaProduto(onResume = {
                        produtoState = produtoDao.buscaPorId(produtoId)
                    })
                }
            }
        }
    }

    @Composable
    private fun PreencheCampos(produtoState: Produto?) {
        produtoState?.let { produtoCarrgeado ->
            DetalhesProdutoTela(produtoCarrgeado,
                onEditProdutoClick = {
                    Intent(this, FormularioProdutoActivity::class.java).apply {
                        putExtra(CHAVE_PRODUTO_ID, produtoId)
                        startActivity(this)
                    }
                },
                onDeleteProdutoClick = {
                    produtoState.let { produtoDao.remove(it) }
                    finish()
                })
        } ?: finish()
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

}

@Composable
private fun BuscaProduto(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onResume: () -> Unit,
) {
    val currentOnResume by rememberUpdatedState(onResume)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                currentOnResume()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetalhesProdutoTela(
    produto: Produto,
    onEditProdutoClick: () -> Unit = {},
    onDeleteProdutoClick: () -> Unit = {}
) {
    Scaffold(
        topBar = { DetalhesAppBar(onEditProdutoClick, onDeleteProdutoClick) },
        content = { DetalhesConteudo(produto) }
    )

}

@Composable
fun DetalhesAppBar(
    onEditProdutoClick: () -> Unit = {},
    onDeleteProdutoClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                stringResource(id = R.string.title_activity_detalhes_produto_compose),
                color = androidx.compose.ui.graphics.Color.White
            )
        },
        actions = {
            IconButton(onClick = {
                onEditProdutoClick()
            }) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Editar produto",
                    tint = androidx.compose.ui.graphics.Color.White
                )
            }
            IconButton(onClick = { onDeleteProdutoClick() }) {
                Icon(
                    painterResource(R.drawable.ic_action_remover),
                    contentDescription = "Deletar produto",
                    tint = androidx.compose.ui.graphics.Color.White
                )
            }
        },
        //backgroundColor = colorResource(id = R.color.colorSecondary)
    )
}


@OptIn(ExperimentalTextApi::class)
@Composable
fun DetalhesConteudo(produto: Produto) {
    Column {
        val imageHeight = 200.dp
        val boxheight = 230.dp

        Box(modifier = Modifier.height(boxheight)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(produto.imagem)
                    .build(),

                placeholder = rememberDrawablePainter(
                    ContextCompat.getDrawable(
                        LocalContext.current,
                        R.drawable.placeholder
                    )
                ),
                error = painterResource(id = R.drawable.erro),
                contentDescription = "Imagem do Produto",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(imageHeight),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )

            val provider = GoogleFont.Provider(
                providerAuthority = "com.google.android.gms.fonts",
                providerPackage = "com.google.android.gms",
                certificates = R.array.com_google_android_gms_fonts_certs
            )


            val montserratFontFamily = FontFamily(
                Font(
                    googleFont = GoogleFont("Montserrat"),
                    fontProvider = provider,
                    weight = FontWeight.Normal
                ),

                androidx.compose.ui.text.font.Font(
                    R.font.montserrat_extrabold,
                    weight = FontWeight.Bold
                )
            )


            Surface(
                shape = CircleShape,
                color = Color.White,
                elevation = 10.dp,
                modifier = Modifier
                    .offset(y = 180.dp, x = 16.dp)

            ) {
                Text(
                    text = produto.valor.formataParaMoedaBrasileira(),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(android.R.color.holo_green_dark),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        }


        Column {
            Text(
                text = produto.nome,
                fontSize = 28.sp,
                color = colorResource(android.R.color.tab_indicator_text),
                fontFamily =
                montserratFontFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                )

            Text(
                text = produto.descricao,
                fontSize = 20.sp,
                color = colorResource(android.R.color.tab_indicator_text),
                fontFamily = montserratFontFamily,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DetalhesAppBarPreview() {
    AppCompatTheme {
        DetalhesAppBar()
    }
}

@Preview(showBackground = true)
@Composable
fun DetalhesConteudoPreview() {

    DetalhesConteudo(
        Produto(
            nome = LoremIpsum(10).values.first(),
            descricao = LoremIpsum(50).values.first(),
            imagem = "",
            valor = BigDecimal(42)
        )
    )
}