package com.example.marvelbrowser.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelbrowser.model.Character
import com.example.marvelbrowser.model.CharacterDataWrapper
import com.example.marvelbrowser.network.MarvelApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception

/**
 * App's list view main view model.
 */
class MainViewModel(context: Context) : ViewModel(), KodeinAware {
    override val kodein: Kodein by closestKodein(context)

    private val marvelApiService: MarvelApi by instance()

    private val characterLiveData: MutableLiveData<List<Character>> = MutableLiveData()

    private val scope = CoroutineScope(Job())

    /**
     * Get main character list.
     */
    fun getCharacterList() = scope.launch {

        try {
            val response = marvelApiService.getCharacterList()
            Timber.w("Response: $response")
            val characterList = response.data.result
            characterLiveData.postValue(characterList)
        } catch (e: HttpException) {
            e.printStackTrace()
            Timber.e("Error when getting characters ${e.response()}")
        }

    }
}