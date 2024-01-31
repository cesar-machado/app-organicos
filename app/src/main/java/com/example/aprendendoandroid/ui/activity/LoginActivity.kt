package com.example.aprendendoandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aprendendoandroid.databinding.ActivityLoginBinding
import com.example.aprendendoandroid.extensions.vaiPara

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        btnCadastrar()
    }
    private fun btnCadastrar() {
        binding.activityLoginBotaoCadastra.setOnClickListener {
            vaiPara(CadastroUsuarioActivity::class.java)
        }
    }
//    private fun vaiParaCadastroUsuario() {
//        val intent = Intent(this, CadastroUsuarioActivity::class.java)
//        startActivity(intent)
//    }
}