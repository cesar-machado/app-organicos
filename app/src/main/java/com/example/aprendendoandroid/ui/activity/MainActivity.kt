package com.example.aprendendoandroid.ui.activity


import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.aprendendoandroid.R
import com.example.aprendendoandroid.model.Produto
import com.example.aprendendoandroid.ui.recyclerview.adapter.ListaProdutosAdapter
import java.math.BigDecimal

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val nome = findViewById<TextView>(R.id.nome)
//        nome.text = "Cesta de frutas"
//        val descricao = findViewById<TextView>(R.id.descricao)
//        descricao.text = "Laranja, manga e maçã"
//        val valor = findViewById<TextView>(R.id.valor)
//        valor.text = "19.99"
        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.adapter = ListaProdutosAdapter(
            context = this, produtos = listOf(
                Produto(
                    nome = "Frutas",
                    descricao = "Fruta Vermelha",
                    valor = BigDecimal("19.00")
                ),
                Produto(
                    nome = "Frutas",
                    descricao = "Fruta Vermelha",
                    valor = BigDecimal("19.00")
                )
            )
        )
    }
}