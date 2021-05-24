package com.example.marvelbrowser.domain.usecases

import com.example.marvelbrowser.data.repositories.CharacterDataRepository
import com.example.marvelbrowser.data.repositories.CharacterRepository

class GetCharacterList(private val characterRepository: CharacterRepository = CharacterDataRepository()) {
    suspend fun execute() = characterRepository.getCharacterList()

}