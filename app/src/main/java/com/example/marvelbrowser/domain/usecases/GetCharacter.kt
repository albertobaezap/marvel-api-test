package com.example.marvelbrowser.domain.usecases

import com.example.marvelbrowser.domain.entities.Character
import com.example.marvelbrowser.domain.repositories.CharacterRepository

class GetCharacter(private val characterRepository: CharacterRepository) {
    suspend fun execute(input: Input): Character = characterRepository.getCharacter(input.id)

    data class Input(
        val id: Int
    )
}