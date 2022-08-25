package com.example.aprendendoandroid.dao

import com.example.aprendendoandroid.model.Produto
import java.math.BigDecimal

class ProdutosDao {

    fun add(produto: Produto) {
        produtos.add(produto)
    }

    fun buscarTodos(): List<Produto> {
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produto>(
            Produto(
                nome = "Cestas de Frutas",
                descricao = "Laranja.uva,maça",
                valor = BigDecimal("19.00")
            )
        )
    }
}