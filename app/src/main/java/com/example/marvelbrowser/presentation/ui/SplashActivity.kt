package com.example.marvelbrowser.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.myapplication.R
import kotlinx.coroutines.*

class SplashActivity: Activity() {

    val scope = CoroutineScope(Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        scope.launch {
            delay(3000)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }

}