package com.example.aprendendoandroid.ui.activity

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import br.com.aprendendoandroid.extensions.formataParaMoedaBrasileira
import br.com.aprendendoandroid.ui.activity.CHAVE_PRODUTO
import com.example.aprendendoandroid.R
import com.example.aprendendoandroid.database.AppDatabase
import com.example.aprendendoandroid.databinding.ActivityDetalhesProdutosBinding
import com.example.aprendendoandroid.extensions.tentaCarregarImagem
import com.example.aprendendoandroid.model.Produto

class DetalhesdoProduto : AppCompatActivity() {

    private lateinit var produto: Produto

    private val binding by lazy {
        ActivityDetalhesProdutosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Detalhes do Produto"
        tentaCarregarProduto()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (::produto.isInitialized) {
            val db = AppDatabase.instance(this)
            val produtoDao = db.produtoDao()
            when (item.itemId) {
                R.id.menu_detalhes_remover -> {
                    produtoDao.remover(produto)
                    finish()
                }
                R.id.menu_detalhes_editar -> {

                }
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
           produto = produtoCarregado
            preencheCampos(produtoCarregado)
        } ?: finish()
    }

    private fun preencheCampos(produtoCarregado: Produto) {
        with(binding) {
            activityDetalhesProdutoImagem.tentaCarregarImagem(produtoCarregado.imagem)
            activityDetalhesProdutoNome.text = produtoCarregado.nome
            activityDetalhesProdutoDescricao.text = produtoCarregado.descricao
            activityDetalhesProdutoValor.text =
                produtoCarregado.valor.formataParaMoedaBrasileira()
        }
    }
}