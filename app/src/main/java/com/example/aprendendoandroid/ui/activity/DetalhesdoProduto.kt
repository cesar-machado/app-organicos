package com.example.aprendendoandroid.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.aprendendoandroid.extensions.formataParaMoedaBrasileira
import br.com.aprendendoandroid.ui.activity.CHAVE_PRODUTO
import com.example.aprendendoandroid.databinding.ActivityDetalhesProdutosBinding
import com.example.aprendendoandroid.extensions.tentaCarregarImagem
import com.example.aprendendoandroid.model.Produto

class DetalhesdoProduto : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhesProdutosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
        }

    private fun tentaCarregarProduto() {
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
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