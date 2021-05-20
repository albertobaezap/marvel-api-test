package com.example.marvelbrowser.data.character

import com.example.marvelbrowser.data.character.remote.CharacterRemoteDataSource
import com.example.marvelbrowser.domain.entities.Character
import com.example.marvelbrowser.domain.repositories.CharacterRepository

class CharacterDataRepository(private val characterRemoteDataSource: CharacterRemoteDataSource): CharacterRepository {
    override suspend fun getCharacterList(): List<Character> {
        return characterRemoteDataSource.getCharacterList()
    }

    override suspend fun getCharacter(id: Int): Character {
       return characterRemoteDataSource.getCharacter(id)
    }
}