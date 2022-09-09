package com.example.aprendendoandroid.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.aprendendoandroid.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM PRODUTO")
    fun buscaTodos() : List<Produto>

    @Insert
    fun salva(produto: Produto)
}