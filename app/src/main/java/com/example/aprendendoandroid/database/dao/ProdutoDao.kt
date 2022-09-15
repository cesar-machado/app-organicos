package com.example.aprendendoandroid.database.dao

import androidx.room.*
import com.example.aprendendoandroid.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM PRODUTO")
    fun buscaTodos() : List<Produto>

    // Com o onConflict ele altera e não precisa de do update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(produto: Produto)

    @Delete
    fun remover(produto: Produto)

//    @Update
//    fun alterar(produto: Produto)

    @Query("SELECT * FROM Produto WHERE uid = :id")
    fun buscarPorId(id: Long) : Produto?
}