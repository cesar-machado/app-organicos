package com.example.aprendendoandroid.database.dao

import androidx.room.*
import com.example.aprendendoandroid.model.Produto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM PRODUTO")
    fun buscaTodos() : Flow<List<Produto>>

    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId")
    fun buscaTodosDoUsuario(usuarioId: String) : Flow<List<Produto>>

    // Com o onConflict ele altera e não precisa de do update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(produto: Produto)

    @Delete
    suspend fun remover(produto: Produto)

//    @Update
//    fun alterar(produto: Produto)

    @Query("SELECT * FROM Produto WHERE uid = :id")
    fun buscarPorId(id: Long) : Flow<Produto?>
}