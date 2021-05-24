package com.example.marvelbrowser.domain.usecases

import com.example.marvelbrowser.data.repositories.CharacterDataRepository
import com.example.marvelbrowser.data.repositories.CharacterRepository

class GetCharacter(private val characterRepository: CharacterRepository = CharacterDataRepository()) {
    suspend fun execute(input: Input) = characterRepository.getCharacter(input.id)

    data class Input(
        val id: Int
    )
}