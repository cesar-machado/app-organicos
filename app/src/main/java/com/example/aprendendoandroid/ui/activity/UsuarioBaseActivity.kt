package com.example.aprendendoandroid.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.example.aprendendoandroid.database.AppDatabase
import com.example.aprendendoandroid.extensions.vaiPara
import com.example.aprendendoandroid.model.Usuarios
import com.example.aprendendoandroid.preferences.dataStore
import com.example.aprendendoandroid.preferences.usuarioLogadoPreferences
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

abstract class UsuarioBaseActivity : AppCompatActivity() {
    private val usuarioDao by lazy {
        AppDatabase.instance(this).UsuarioDao()
    }

    protected var usuario: Usuarios? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            verificaUsuarioLogado()
        }
    }

    private suspend fun verificaUsuarioLogado() {
        dataStore.data.collect { preferences ->
            preferences[usuarioLogadoPreferences]?.let { usuarioId ->
                buscaUsuario(usuarioId)
            } ?: vaiParaLogin()
        }
    }

    private fun buscaUsuario(usuarioId: String) = lifecycleScope.launch {
        usuario = usuarioDao.buscaPorId(usuarioId).firstOrNull()
    }

    protected fun deslogarUsuario() {
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences.remove(usuarioLogadoPreferences)
            }
        }
    }

    private fun vaiParaLogin() {
        vaiPara(LoginActivity::class.java)
        finish()
    }
}


