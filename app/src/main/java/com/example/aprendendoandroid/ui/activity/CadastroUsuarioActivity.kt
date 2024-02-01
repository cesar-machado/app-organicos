package com.example.aprendendoandroid.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.aprendendoandroid.database.AppDatabase
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
    private val dao by lazy {
        AppDatabase.instance(this).UsuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title= "Cadastrar usuario"
        configurarBtnCadastrar()
    }

    private fun configurarBtnCadastrar() {
        binding.activityFormUsuariosBtnCadastrar.setOnClickListener {
            val novoUsuario = criaUsuario()
            Log.i("CadastroUsuario", "onCreate: $novoUsuario")
            lifecycleScope.launch{
                try {
                    dao.cadastrar(novoUsuario)
                    finish()
                }catch (e: Exception) {
                    Log.e("CadastroUsuario", "configuraBotaoCadastrar: ", e)
                    Toast.makeText(
                        this@CadastroUsuarioActivity,
                        "Falha ao cadastrar usuário",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun criaUsuario(): Usuarios {
        val usuario = binding.activityFormUsuariosUsuario.text.toString()
        val nome = binding.activityFormUsuariosNome.text.toString()
        val senha = binding.activityFormUsuariosSenha.text.toString()
        return Usuarios(usuario, nome, senha)
    }

    
}