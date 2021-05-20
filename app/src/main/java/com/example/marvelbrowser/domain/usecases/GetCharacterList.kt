package com.example.marvelbrowser.domain.usecases

import com.example.marvelbrowser.domain.entities.Character
import com.example.marvelbrowser.domain.repositories.CharacterRepository

class GetCharacterList(private val characterRepository: CharacterRepository) {
    suspend fun execute(): List<Character> = characterRepository.getCharacterList()

}