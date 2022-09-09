package com.example.aprendendoandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.aprendendoandroid.database.converter.Converters
import com.example.aprendendoandroid.database.dao.ProdutoDao
import com.example.aprendendoandroid.model.Produto


@Database(entities = [Produto::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class   AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao

    companion object {
        fun instance(context: Context) : AppDatabase {
             return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "orgs.db"
            ).allowMainThreadQueries()
                .build()
        }
    }
}