package com.example.marvelbrowser.presentation.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelbrowser.domain.entities.Character
import com.example.myapplication.R
import com.example.marvelbrowser.app.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailActivity: AppCompatActivity() {

    private val networkModule = Network()
    private val scope = CoroutineScope(Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val name = intent.getIntExtra(MainActivity.DETAIL_ID_EXTRA, 0)

        getCharacter(name)
    }

    private fun getCharacter(id: Int) = scope.launch {
        val response = networkModule.service.getCharacter(id)
        if (response.isSuccessful) {
            displayData(response.body()?.data?.results!!.first())
        } else {
            Log.e("Detail", response.message())
        }
    }

    private fun displayData(character: Character) = scope.launch(Dispatchers.Main){
        findViewById<TextView>(R.id.title).text = character.name
    }
}