package com.example.marvelbrowser.data.repositories.remote

import com.example.marvelbrowser.domain.entities.Character
import com.example.marvelbrowser.app.Network

class CharacterRemoteDataSource(private val network: Network = Network()) {

    companion object {
        const val OFFSET = 0
        const val LIMIT = 100
    }

    suspend fun getCharacterList(): List<Character> {
        val response = network.service.getCharacterList(OFFSET, LIMIT)
        return if (response.isSuccessful) {
            response.body()?.data!!.results
        } else emptyList()
    }

    suspend fun getCharacter(id: Int): Character? {
        val response = network.service.getCharacter(id)
        return if (response.isSuccessful) {
            response.body()?.data!!.results.first()
        } else null
    }

}