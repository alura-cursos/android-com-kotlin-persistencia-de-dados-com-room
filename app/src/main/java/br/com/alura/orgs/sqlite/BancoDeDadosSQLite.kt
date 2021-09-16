package br.com.alura.orgs.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.sqlite.ProdutoContrato.ProdutoEntrada
import br.com.alura.orgs.sqlite.ProdutoContrato.SQL_CRIA_TABELA_PRODUTOS
import br.com.alura.orgs.sqlite.ProdutoContrato.SQL_REMOVE_BANCO_SE_EXISTE
import java.math.BigDecimal

// Contrato das especificações do banco de dados
object ProdutoContrato {

    // Representação da tabela produto
    object ProdutoEntrada : BaseColumns {
        const val NOME_TABELA = "produtos"
        const val COLUNA_NOME = "nome"
        const val COLUNA_DESCRICAO = "descricao"
    }

    // SQL para criar tabela de produto
    const val SQL_CRIA_TABELA_PRODUTOS =
        "CREATE TABLE ${ProdutoEntrada.NOME_TABELA} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${ProdutoEntrada.COLUNA_NOME} TEXT," +
                "${ProdutoEntrada.COLUNA_DESCRICAO} TEXT)"

    // SQL para remover tabela de produtos se existir
    const val SQL_REMOVE_BANCO_SE_EXISTE =
        "DROP TABLE IF EXISTS ${ProdutoEntrada.NOME_TABELA}"
}

// Classe que auxilia o uso da API do SQLite
class ProdutoDbHelper(context: Context) :
    SQLiteOpenHelper(context, NOME_BANCO_DE_DADOS, null, VERSAO_BANCO_DE_DADOS) {

    // cria o banco de dados
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CRIA_TABELA_PRODUTOS)
    }

    // atualiza o banco de dados quando há uma nova versão
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_REMOVE_BANCO_SE_EXISTE)
        onCreate(db)
    }

    // constantes para definir versão e nome do banco de dados
    companion object {
        const val VERSAO_BANCO_DE_DADOS = 1
        const val NOME_BANCO_DE_DADOS = "orgs.db"
    }

    // implementação de inserção no banco de dados
    fun salva(produto: Produto) {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(ProdutoEntrada.COLUNA_NOME, produto.nome)
            put(ProdutoEntrada.COLUNA_DESCRICAO, produto.descricao)
        }

        db?.insert(
            ProdutoEntrada.NOME_TABELA,
            null,
            values
        )
    }

    // implementação para buscar registros no banco de dados
    fun buscaProdutos(): List<Produto> {
        val db = readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            ProdutoEntrada.COLUNA_NOME,
            ProdutoEntrada.COLUNA_DESCRICAO
        )

        val cursor = db.query(
            ProdutoEntrada.NOME_TABELA,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val produtos = mutableListOf<Produto>()

        with(cursor) {
            while (cursor.moveToNext()) {
                val id = getLong(
                    getColumnIndexOrThrow(
                        BaseColumns._ID
                    )
                )
                val nome = getString(
                    getColumnIndexOrThrow(
                        ProdutoEntrada.COLUNA_NOME
                    )
                )
                val descricao = getString(
                    getColumnIndexOrThrow(
                        ProdutoEntrada.COLUNA_DESCRICAO
                    )
                )
                produtos.add(
                    Produto(
                        id = id,
                        nome = nome,
                        descricao = descricao,
                        valor = BigDecimal.ZERO
                    )
                )
            }
        }
        return produtos
    }

}