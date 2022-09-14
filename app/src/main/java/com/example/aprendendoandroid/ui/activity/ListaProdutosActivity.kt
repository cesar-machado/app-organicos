package com.example.aprendendoandroid.ui.activity


import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import br.com.aprendendoandroid.ui.activity.CHAVE_PRODUTO
import com.example.aprendendoandroid.R
import com.example.aprendendoandroid.database.AppDatabase
import com.example.aprendendoandroid.databinding.ActivityListaProdutosBinding
import com.example.aprendendoandroid.ui.recyclerview.adapter.ListaProdutosAdapter

class ListaProdutosActivity : AppCompatActivity() {

    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Organicos"
        configuraRecyclerView()
        configuraFab()

    }

    override fun onCreateContextMenu(
        menu: ContextMenu, v: View,
        menuInfo: ContextMenu.ContextMenuInfo
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_contexto_flutuante, menu)
    }

//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
//        return when (item.itemId) {
//            R.id.menu_contexto_editar -> {
//                true
//            }
//            R.id.menu_contexto_remover -> {
//                false
//            }
//            else -> super.onContextItemSelected(item)
//        }
//    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instance(this)
        val produtoDao = db.produtoDao()
        adapter.atualiza((produtoDao.buscaTodos()))
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