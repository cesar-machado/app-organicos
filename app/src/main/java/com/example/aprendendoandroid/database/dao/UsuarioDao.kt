package com.example.aprendendoandroid.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aprendendoandroid.model.Usuarios
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Insert
    suspend fun cadastrar(usuario: Usuarios)

    @Query("""
        SELECT * FROM Usuarios
        WHERE id = :usuarioId
        AND senha = :senha""")
    suspend fun autentica(
        usuarioId: String,
        senha: String
    ): Usuarios?

    @Query("SELECT * FROM Usuarios WHERE id = :usuarioId")
    fun buscaPorId(usuarioId: String): Flow<Usuarios>

}