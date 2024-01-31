package com.example.aprendendoandroid.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.aprendendoandroid.databinding.ActivityFormUsuariosBinding
import com.example.aprendendoandroid.databinding.ActivityLoginBinding
import com.example.aprendendoandroid.model.Produto
import com.example.aprendendoandroid.model.Usuarios
import kotlinx.coroutines.launch
import java.math.BigDecimal

class CadastroUsuarioActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormUsuariosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title= "Cadastrar usuario"
    }

    private fun configurarBtnCadastrar() {
        binding.activityFormUsuariosBtnCadastrar.setOnClickListener {
            val novoUsuario = criaUsuario()
            Log.i("CadastroUsuario", "onCreate: $novoUsuario")
            finish()
        }

    }

    private fun criaUsuario(): Usuarios {
        val usuario = binding.activityFormUsuariosUsuario.text.toString()
        val nome = binding.activityFormUsuariosNome.text.toString()
        val senha = binding.activityFormUsuariosSenha.text.toString()
        return Usuarios(usuario, nome, senha)
    }

    
}