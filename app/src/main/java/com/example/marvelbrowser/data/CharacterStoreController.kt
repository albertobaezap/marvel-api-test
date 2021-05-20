package com.example.marvelbrowser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marvelbrowser.network.MarvelApi
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import com.example.marvelbrowser.domain.entities.Character
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

/**
 * Store for handling all operations related to characters. Provides a static storage for the character list.
 */
class CharacterStoreController(private val marvelApiService: MarvelApi) {

    companion object {
        private const val REQUEST_LIMIT = 50
    }

    private val scope = CoroutineScope(Job())

    // Static character list
    private var characterList: MutableList<com.example.marvelbrowser.domain.entities.Character> = mutableListOf()
    private val characterListLiveData: MutableLiveData<List<Character>> = MutableLiveData()

    init {
        //Load list on init
        loadCharacterList()
    }

    // Expose the actual list
    fun getCharacterListLiveData(): LiveData<List<Character>> = characterListLiveData

    /**
     * Get main character list.
     */
    private fun loadCharacterList() = scope.launch {

        try {
            var offset = 0
            var count = 0
            var total = REQUEST_LIMIT

            /**
             * Since we can only retrieve batches of 100 character max, we have to repeat this operation to fill the list
             * until we've got everyone.
             */
            while (count < total) {

                try {
                    marvelApiService.getCharacterList(offset, REQUEST_LIMIT).let {
                        if (it.isSuccessful) {
                            val wrapper = it.body()!!

                            //Update the total values for the next iteration
                            offset += REQUEST_LIMIT
                            total = wrapper.data.total
                            count += wrapper.data.count

                            //Update the list
                            characterList.addAll(wrapper.data.results)

                            //Expose the live data
                            characterListLiveData.postValue(characterList)
                        } else {
                            Timber.e("There was a problem retrieving the list ${it.message()}")
                            characterListLiveData.postValue(null)
                            return@launch
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

    /**
     * Gets an individual character data.
     * @param characterId Required character's identifier.
     */
    fun getCharacterData(characterId: Int): Character? =
        characterList.firstOrNull { it.id == characterId }
}