package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        const val DETAIL_ID_EXTRA = "detail_id_extra"
    }

    private val TAG = "MainActivity"

    private val networkModule = Network(this)
    private val scope = CoroutineScope(Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrieveList()
    }

    private fun retrieveList() = scope.launch {
        val response = networkModule.service.getCharacterList(0, 100)
        if (response.isSuccessful) {
            displayData(response.body()?.data!!.results)
        }

    }

    private fun displayData(characterList: List<Character>) = scope.launch(Dispatchers.Main) {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1)
        adapter.addAll(characterList.map { it.name })

        val listView = findViewById<ListView>(R.id.character_list)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(DETAIL_ID_EXTRA, characterList[position].id)
            startActivity(intent)
        }

    }
}

