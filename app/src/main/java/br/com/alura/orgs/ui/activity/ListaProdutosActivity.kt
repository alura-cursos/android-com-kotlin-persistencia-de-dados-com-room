package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaProdutosActivityBinding
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

private const val TAG = "ListaProdutos"

class ListaProdutosActivity : AppCompatActivity() {

    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }
    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instancia(this)
        val produtoDao = db.produtoDao()
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.i(TAG, "onResume: throwable $throwable")
            Toast.makeText(
                this@ListaProdutosActivity,
                "Ocorreu um problema",
                Toast.LENGTH_SHORT
            ).show()
        }
        val scope = MainScope()
        scope.launch(job) {
            repeat(1000) {
                Log.i(TAG, "onResume: coroutine está em execução $it")
                delay(1000)
            }
        }
        scope.launch(handler) {
            MainScope().launch(handler) {
                throw Exception("lançando exception na coroutine em outro scope")
            }
            throw IllegalArgumentException("lançando exception na coroutine")
            val produtos = withContext(Dispatchers.IO) {
                produtoDao.buscaTodos()
            }
            adapter.atualiza(produtos)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun configuraFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
    }

}