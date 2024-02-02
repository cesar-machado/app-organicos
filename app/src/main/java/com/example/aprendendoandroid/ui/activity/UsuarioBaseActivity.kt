package com.example.aprendendoandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.example.aprendendoandroid.database.AppDatabase
import com.example.aprendendoandroid.extensions.vaiPara
import com.example.aprendendoandroid.model.Usuarios
import com.example.aprendendoandroid.preferences.dataStore
import com.example.aprendendoandroid.preferences.usuarioLogadoPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

abstract class UsuarioBaseActivity : AppCompatActivity() {
    private val usuarioDao by lazy {
        AppDatabase.instance(this).UsuarioDao()
    }

    private val _usuario: MutableStateFlow<Usuarios?> = MutableStateFlow(null)
    protected val usuario: StateFlow<Usuarios?> = _usuario

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

    private suspend fun buscaUsuario(usuarioId: String) : Usuarios?  {
        return usuarioDao
            .buscaPorId(usuarioId)
            .firstOrNull().also {
                _usuario.value = it
            }
    }

    protected fun deslogarUsuario() {
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences.remove(usuarioLogadoPreferences)
            }
        }
    }

    private fun vaiParaLogin() {
        vaiPara(LoginActivity::class.java) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        finish()
    }
}


