package com.example.marvelbrowser.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelbrowser.domain.entities.Character
import com.example.myapplication.R
import com.example.marvelbrowser.presentation.presenters.MainPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MainPresenter.View {

    companion object {
        const val DETAIL_ID_EXTRA = "detail_id_extra"
    }

    private val TAG = "MainActivity"

    private val scope = CoroutineScope(Job())
    private val presenter = MainPresenter(this)

    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scope.launch { presenter.retrieveList() }
    }


    private fun goToDetail(id: Int) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DETAIL_ID_EXTRA, id)
        startActivity(intent)
    }

    override suspend fun onListRetrieved(list: List<Character>) {
        scope.launch(Dispatchers.Main) {
            adapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1)
            adapter.addAll(list.map { it.name })

            val listView = findViewById<ListView>(R.id.character_list)
            listView.adapter = adapter
            listView.setOnItemClickListener { parent, view, position, id ->
                goToDetail(list[position].id)
            }
        }
    }
}

