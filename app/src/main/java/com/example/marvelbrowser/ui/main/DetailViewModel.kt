package com.example.marvelbrowser.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.marvelbrowser.data.CharacterStoreController
import com.example.marvelbrowser.model.Character
import org.jetbrains.annotations.TestOnly
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

/**
 * Character detail view model.
 */
class DetailViewModel(context: Context) : ViewModel(), KodeinAware {

    override val kodein: Kodein by closestKodein(context)

    private val characterStoreController: CharacterStoreController by instance()

    fun getCharacterData(characterId: Int): Character? = characterStoreController.getCharacterData(characterId)
}