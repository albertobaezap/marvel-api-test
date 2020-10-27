package com.example.marvelbrowser.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    companion object {
        private const val REQUEST_LIMIT = 100
    }

    override val kodein: Kodein by closestKodein(context)

    private val marvelApiService: MarvelApi by instance()

    private val characterLiveData: MutableLiveData<List<Character>> = MutableLiveData()
    private val dataReady: MutableLiveData<Boolean> = MutableLiveData()

    private val scope = CoroutineScope(Job())

    fun getCharacterLiveData(): LiveData<List<Character>> = characterLiveData

    /**
     * Get main character list.
     */
    fun getCharacterList() = scope.launch {

        try {
            var offset = 0
            var count = 0
            var total = REQUEST_LIMIT

            while (count < total) {

                try {
                    marvelApiService.getCharacterList(offset, REQUEST_LIMIT).let {
                        if (it.isSuccessful) {
                            val wrapper = it.body()!!

                            offset += REQUEST_LIMIT
                            total = wrapper.data.total
                            count += wrapper.data.count
                            characterLiveData.postValue(wrapper.data.results)
                        }
                    }
                } catch (e: JsonSyntaxException) {
                    e.printStackTrace()
                    Timber.e("Error parsing an object")
                }
            }

        } catch (e: HttpException) {
            e.printStackTrace()
            Timber.e("Error when getting characters ${e.response()}")
        }

    }
}