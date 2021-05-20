package com.example.marvelbrowser.presentation.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.marvelbrowser.app.base.BasePresenter
import com.example.marvelbrowser.data.CharacterStoreController
import com.example.marvelbrowser.domain.entities.Character
import com.example.marvelbrowser.domain.usecases.GetCharacter
import com.example.marvelbrowser.presentation.main.MainPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

/**
 * Character detail view model.
 */
class DetailPresenter(context: Context, view: View) :  BasePresenter<DetailPresenter.View>(), KodeinAware {

    override val kodein: Kodein by closestKodein(context)

    private val getCharacter: GetCharacter by instance()
    private val scope = CoroutineScope(Job())

    interface View {
        fun displayCharacter(character: CharacterViewModel)
    }

    init {
        this.view = view
    }

    fun getCharacter(id: Int) = scope.launch {
        val character = getCharacter.execute(GetCharacter.Input(id))
        view?.displayCharacter(character.toViewModel())
    }
}