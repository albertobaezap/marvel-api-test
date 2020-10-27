package com.example.marvelbrowser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marvelbrowser.model.Character
import com.example.marvelbrowser.network.MarvelApi
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class CharacterStoreController(private val marvelApiService: MarvelApi) {

    companion object {
        private const val REQUEST_LIMIT = 50
    }

    private val scope = CoroutineScope(Job())

    private var characterList: MutableList<Character> = mutableListOf()
    private val characterListLiveData: MutableLiveData<List<Character>> = MutableLiveData()

    init {
        loadCharacterList()
    }

    fun getCharacterListLiveData(): LiveData<List<Character>> = characterListLiveData

    /**
     * Get main character list.
     */
    private fun loadCharacterList() = scope.launch {

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
                            characterList.addAll(wrapper.data.results)
                            Timber.d("Adding new results: ${characterList.count()}")
                            characterListLiveData.postValue(characterList)
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