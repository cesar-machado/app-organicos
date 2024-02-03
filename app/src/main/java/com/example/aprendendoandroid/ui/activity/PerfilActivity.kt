package com.example.aprendendoandroid.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.aprendendoandroid.R
import com.example.aprendendoandroid.database.AppDatabase
import com.example.aprendendoandroid.databinding.ActivityLoginBinding
import com.example.aprendendoandroid.databinding.ActivityPerfilBinding
import com.example.aprendendoandroid.preferences.dataStore
import com.example.aprendendoandroid.preferences.usuarioLogadoPreferences
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class PerfilActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityPerfilBinding.inflate(layoutInflater)
    }

    private val usuarioDao by lazy {
        AppDatabase.instance(this).UsuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Detalhes do Usuário"
        buscandoUsuario()
        configurandoBtnSair()
    }

    private fun buscandoUsuario() {
        lifecycleScope.launch {
            usuario.filterNotNull().collect { usuario ->
    //                Log.i("usuario", "onCreate: $")

                "Usuario : ${usuario.nome}".also { binding.activityPerfilNome.text = it }
                "Nome : ${usuario.id}".also { binding.activityPerfilUsuario.text = it }
            }
        }
    }

    private fun configurandoBtnSair() {
        val logout = binding.activityPerfilBtnSair
        logout.setOnClickListener{
            deslogarUsuario()
        }
    }

}