package com.example.aprendendoandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.aprendendoandroid.extensions.formataParaMoedaBrasileira
import br.com.aprendendoandroid.ui.activity.CHAVE_PRODUTO_ID
import com.example.aprendendoandroid.R
import com.example.aprendendoandroid.database.AppDatabase
import com.example.aprendendoandroid.databinding.ActivityDetalhesProdutosBinding
import com.example.aprendendoandroid.extensions.tentaCarregarImagem
import com.example.aprendendoandroid.model.Produto

class DetalhesdoProduto : AppCompatActivity() {

    private var produtoId: Long = 0L
    private var produto: Produto? = null

    private val binding by lazy {
        ActivityDetalhesProdutosBinding.inflate(layoutInflater)
    }
    val produtoDao by lazy {
        AppDatabase.instance(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Detalhes do Produto"
        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        buscaProduto()
    }

    private fun buscaProduto() {
        produto = produtoDao.buscarPorId(produtoId)
        produto?.let {
            preencheCampos(it)
        } ?: finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_remover -> {
                produto?.let { produtoDao.remover(it) }
                finish()
            }
            R.id.menu_detalhes_editar -> {
                Intent(this, FormProdutoActivity::class.java).apply {
                    putExtra(CHAVE_PRODUTO_ID, produtoId)
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
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