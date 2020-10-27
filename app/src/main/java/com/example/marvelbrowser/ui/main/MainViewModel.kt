package com.example.marvelbrowser.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelbrowser.data.CharacterStoreController
import com.example.marvelbrowser.model.Character
import com.example.marvelbrowser.network.MarvelApi
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import retrofit2.HttpException
import timber.log.Timber

/**
 * App's list view main view model.
 */
class MainViewModel(context: Context) : ViewModel(), KodeinAware {

    override val kodein: Kodein by closestKodein(context)

    private val characterStoreController: CharacterStoreController by instance()

    fun getCharacterLiveData(): LiveData<List<Character>> = characterStoreController.getCharacterListLiveData()
}