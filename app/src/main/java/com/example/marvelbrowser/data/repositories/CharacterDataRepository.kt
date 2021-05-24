package com.example.marvelbrowser.data.repositories

import com.example.marvelbrowser.domain.entities.Character
import com.example.marvelbrowser.data.repositories.remote.CharacterRemoteDataSource

class CharacterDataRepository(private val characterRemoteDataSource: CharacterRemoteDataSource = CharacterRemoteDataSource()) :
    CharacterRepository {

    override suspend fun getCharacterList(): List<Character> =
        characterRemoteDataSource.getCharacterList()

    override suspend fun getCharacter(id: Int): Character? = characterRemoteDataSource.getCharacter(id)
}