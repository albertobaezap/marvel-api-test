package com.example.marvelbrowser.data.character.remote

import com.example.marvelbrowser.domain.entities.Character
import com.example.marvelbrowser.network.MarvelApi

class CharacterRemoteDataSource(private val marvelApiService: MarvelApi) {

    companion object {
        private const val REQUEST_LIMIT = 50
    }

   suspend fun getCharacterList(): List<Character> =
        marvelApiService.getCharacterList(0, REQUEST_LIMIT).body()!!.data.results

    suspend fun getCharacter(id: Int): Character =
        marvelApiService.getCharacter(id).body()!!.data.results.first()

}