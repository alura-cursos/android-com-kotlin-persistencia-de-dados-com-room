package br.com.alura.orgs.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Produto(
    val id: Long = 0L,
    val nome: String,
    val descricao: String,
    val valor: BigDecimal,
    val imagem: String? = null
) : Parcelable
