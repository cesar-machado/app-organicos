package com.example.aprendendoandroid.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.aprendendoandroid.database.AppDatabase
import com.example.aprendendoandroid.databinding.ActivityFormProdutoBinding
import com.example.aprendendoandroid.databinding.FormularioImagemBinding
import com.example.aprendendoandroid.extensions.tentaCarregarImagem
import com.example.aprendendoandroid.model.Produto
import java.math.BigDecimal

class FormProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title= "Cadastrar Produto"
        configurarBtnSalvar()
        binding.activityFormImg.setOnClickListener {
            val bindingFormImg = FormularioImagemBinding.inflate(layoutInflater)
            bindingFormImg.formularioImagemBotaoCarregar.setOnClickListener {
                val url = bindingFormImg.formularioImagemUrl.text.toString()
                bindingFormImg.formularioImagemImageview.tentaCarregarImagem(url)
            }
            AlertDialog.Builder(this)
                .setView(bindingFormImg.root)
                .setPositiveButton("Confirmar") { _, _ ->
                    url = bindingFormImg.formularioImagemUrl.text.toString()
                    binding.activityFormImg.tentaCarregarImagem(url)
                }
                .setNegativeButton("Cancelar") { _, _ ->

                }
                .show()
        }
    }

    private fun configurarBtnSalvar() {
        val botaoSalvar = binding.activityFormBtnSalvar
        val db = AppDatabase.instance(this)
        val produtoDao = db.produtoDao()
        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            produtoDao.salva(produtoNovo)
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = binding.activityFormProdutoNome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.activityFormProdutoDescricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.activityFormProdutoValor
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
            valor = valor,
            imagem = url
        )
    }
}