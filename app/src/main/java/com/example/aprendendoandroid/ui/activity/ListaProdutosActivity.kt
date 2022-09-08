package com.example.aprendendoandroid.ui.activity


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.aprendendoandroid.ui.activity.CHAVE_PRODUTO
import com.example.aprendendoandroid.R
import com.example.aprendendoandroid.dao.ProdutosDao
import com.example.aprendendoandroid.databinding.ActivityListaProdutosBinding
import com.example.aprendendoandroid.ui.recyclerview.adapter.ListaProdutosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaProdutosActivity : AppCompatActivity() {

    private val dao = ProdutosDao()
    private val adapter = ListaProdutosAdapter(context = this, produtos = dao.buscarTodos())
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title="Organicos"
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscarTodos())
    }

    private fun configuraFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesdoProduto::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO, it)
            }
            startActivity(intent)
        }
    }
}