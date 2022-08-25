package com.example.aprendendoandroid.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.aprendendoandroid.R
import com.example.aprendendoandroid.dao.ProdutosDao
import com.example.aprendendoandroid.databinding.ActivityFormProdutoBinding
import com.example.aprendendoandroid.model.Produto
import java.math.BigDecimal

class FormProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configurarBtnSalvar()
    }

    private fun configurarBtnSalvar() {
        val botaoSalvar = binding.activityFormBtnSalvar
        val dao = ProdutosDao()
        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            dao.add(produtoNovo)
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = binding.formNome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.formDescricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.formValor
        val valorEmTexto = campoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

//    Forma com o binding padrão
//    private fun criaProduto(): Produto {
//        val campoNome = findViewById<EditText>(R.id.form_nome)
//        val nome = campoNome.text.toString()
//        val campoDescricao = findViewById<EditText>(R.id.form_descricao)
//        val descricao = campoDescricao.text.toString()
//        val campoValor = findViewById<EditText>(R.id.form_valor)
//        val valorEmTexto = campoValor.text.toString()
//        val valor = if (valorEmTexto.isBlank()) {
//            BigDecimal.ZERO
//        } else {
//            BigDecimal(valorEmTexto)
//        }

        return Produto(
            nome = nome,
            descricao = descricao,
            valor = valor
        )
    }
}