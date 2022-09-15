package com.example.aprendendoandroid.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.aprendendoandroid.ui.activity.CHAVE_PRODUTO_ID
import com.example.aprendendoandroid.database.AppDatabase
import com.example.aprendendoandroid.database.dao.ProdutoDao
import com.example.aprendendoandroid.databinding.ActivityFormProdutoBinding
import com.example.aprendendoandroid.extensions.tentaCarregarImagem
import com.example.aprendendoandroid.model.Produto
import com.example.aprendendoandroid.ui.dialog.FormImgDialog
import java.math.BigDecimal

class FormProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var produtoId = 0L
    private val produtoDao: ProdutoDao by lazy {
        AppDatabase.instance(this).produtoDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar Produto"
        configurarBtnSalvar()
        binding.activityFormImg.setOnClickListener {
            FormImgDialog(this)
                .mostra(url) { imagem ->
                    url = imagem
                    binding.activityFormImg.tentaCarregarImagem(url)
                }
        }
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    override fun onResume() {
        super.onResume()
        tentaBuscarProdutos()
    }

    private fun tentaBuscarProdutos() {
        produtoDao.buscarPorId(produtoId)?.let {
            preencheCampos(it)
        }
    }

    private fun preencheCampos(produto: Produto) {
        title = "Alterar Produto"
        url = produto.imagem
        binding.activityFormImg.tentaCarregarImagem(produto.imagem)
        binding.activityFormProdutoNome.setText(produto.nome)
        binding.activityFormProdutoDescricao.setText(produto.descricao)
        binding.activityFormProdutoValor.setText(produto.valor.toPlainString())
    }

    private fun configurarBtnSalvar() {
        val botaoSalvar = binding.activityFormBtnSalvar
        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
//            Aqui eu aprendi a colacar o update porém com onConflict não é necessário
//            if (produtoId > 0) {
//                produtoDao.alterar(produtoNovo)
//            } else {
//                produtoDao.salva(produtoNovo)
//            }
            produtoDao.salva(produtoNovo)
            finish()
        }
    }

//    private fun validateProduto() {
//        val campoNome = binding.activityFormProdutoNome
//        val nome = campoNome.text.toString().trim()
//
//        if(nome.isEmpty()) {
//            campoNome.setError("Precisa de um texto")
//        }
//
//
//    }

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
            uid = produtoId,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }
}