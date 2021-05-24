package com.example.marvelbrowser.presentation.presenters

import com.example.marvelbrowser.domain.entities.Character
import com.example.marvelbrowser.domain.usecases.GetCharacterList

class MainPresenter(
    private val view: View,
    private val getCharacterList: GetCharacterList = GetCharacterList()
) {

    interface View {
        suspend fun onListRetrieved(list: List<Character>)
    }

    suspend fun retrieveList() {
        val characterList = getCharacterList.execute()
        view.onListRetrieved(characterList)
    }
}