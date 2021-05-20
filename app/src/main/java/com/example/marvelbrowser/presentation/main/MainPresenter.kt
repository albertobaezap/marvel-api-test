package com.example.marvelbrowser.presentation.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelbrowser.app.base.BasePresenter
import com.example.marvelbrowser.app.base.BaseView
import com.example.marvelbrowser.data.CharacterStoreController
import com.example.marvelbrowser.domain.entities.Character
import com.example.marvelbrowser.domain.usecases.GetCharacterList
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import retrofit2.HttpException
import timber.log.Timber

/**
 * App's list view main view model.
 */
class MainPresenter(context: Context, view: View): BasePresenter<MainPresenter.View>(), KodeinAware {

    override val kodein: Kodein by closestKodein(context)

    private val getCharacterList: GetCharacterList by instance()
    private val scope = CoroutineScope(Job())

    // Static character list
    private var characterList: MutableList<Character> = mutableListOf()

    interface View {
        fun displayCharacterList(characterList: List<Character>)
    }

    init {
        this.view = view
    }

    /**
     * Get main character list.
     */
     fun loadCharacterList() = scope.launch {
        val characterList = getCharacterList.execute()
        Timber.d("Character list received $characterList and view? $view")
        view?.displayCharacterList(characterList)
    }

    /**
     * Gets an individual character data.
     * @param characterId Required character's identifier.
     */
    fun getCharacterData(characterId: Int): Character? =
        characterList.firstOrNull { it.id == characterId }
}