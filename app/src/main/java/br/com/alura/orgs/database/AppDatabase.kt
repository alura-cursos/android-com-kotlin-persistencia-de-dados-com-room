package br.com.alura.orgs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.orgs.database.converter.Converters
import br.com.alura.orgs.database.dao.ProdutoDao
import br.com.alura.orgs.model.Produto

@Database(entities = [Produto::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao
}