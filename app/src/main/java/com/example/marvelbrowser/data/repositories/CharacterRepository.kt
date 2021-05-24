package com.example.marvelbrowser.data.repositories

import com.example.marvelbrowser.domain.entities.Character

interface CharacterRepository {
    suspend fun getCharacterList(): List<Character>
    suspend fun getCharacter(id: Int): Character?
}