package com.example.aprendendoandroid.ui.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.aprendendoandroid.ui.activity.CHAVE_PRODUTO_ID
import com.example.aprendendoandroid.R
import com.example.aprendendoandroid.database.AppDatabase
import com.example.aprendendoandroid.databinding.ActivityListaProdutosBinding
import com.example.aprendendoandroid.extensions.vaiPara
import com.example.aprendendoandroid.preferences.dataStore
import com.example.aprendendoandroid.preferences.usuarioLogadoPreferences
import com.example.aprendendoandroid.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ListaProdutosActivity : UsuarioBaseActivity() {

    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }
    private val produtoDao by lazy {
        val db = AppDatabase.instance(this)
        db.produtoDao()
    }

    private val usuarioDao by lazy {
        AppDatabase.instance(this).UsuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Organicos"
        configuraRecyclerView()
        configuraFab()
        lifecycleScope.launch {
            launch {
                usuario
                    .filterNotNull()
                    .collect{usuario ->
                    buscaProdutosUsuario(usuario.id)
                }
            }
        }
    }



    private suspend fun buscaProdutosUsuario(usuarioId: String) {
        produtoDao.buscaTodosDoUsuario(usuarioId).collect { produtos ->
            adapter.atualiza(produtos)
        }
    }




    override fun onCreateContextMenu(
        menu: ContextMenu, v: View,
        menuInfo: ContextMenu.ContextMenuInfo
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_lista_produtos_sair_app -> {
                vaiParaPerfil()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun vaiParaPerfil() {
        val intent = Intent(this, PerfilActivity::class.java)
        startActivity(intent)
    }


// UTILIZADO QUANDO NÃO TEM O FLOW PARA ATUALIZAR A ACTIVITY
//    override fun onResume() {
//        super.onResume()
//        val db = AppDatabase.instance(this)
//        val produtoDao = db.produtoDao()
//        adapter.atualiza((produtoDao.buscaTodos()))
//    }

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
                putExtra(CHAVE_PRODUTO_ID, it.uid)
            }
            startActivity(intent)
        }
    }
}