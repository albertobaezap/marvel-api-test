package com.example.marvelbrowser.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.marvelbrowser.databinding.DetailFragmentBinding
import com.example.marvelbrowser.domain.entities.Character
import com.example.marvelbrowser.domain.entities.ItemType
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

/**
 * Detail fragment for an individual [Character]
 */
class DetailFragment : Fragment(), DetailPresenter.View, KodeinAware {

    override val kodein: Kodein by closestKodein()

    private lateinit var detailPresenter: DetailPresenter
    private lateinit var binding: DetailFragmentBinding

    private val safeArgs: DetailFragmentArgs by navArgs()
    private val scope = CoroutineScope(Job())

    override fun onSaveInstanceState(outState: Bundle) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        DetailFragmentBinding.inflate(inflater, container, false).apply { binding = this }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViews()
    }

    /**
     * Populate all the necessary views.
     */
    private fun setupViews() {
        detailPresenter = DetailPresenter(requireContext(), this)
        detailPresenter.getCharacter(safeArgs.characterId)
    }

    override fun displayCharacter(character: CharacterViewModel) {
        scope.launch(Dispatchers.Main) {
            with(binding) {
                characterDetailName.text = character.name

                if (character.description.isNotEmpty()) {
                    characterDetailDescription.text = character.description
                } else {
                    characterDetailDescription.visibility = View.GONE
                }
                configureExpandableList(character)
                Picasso.with(root.context)
                    //There is an error when trying to load HTTP images from the source, so we'll change protocols
                    .load(character.image)
                    .into(characterDetailImage)
            }
        }
    }

    /**
     * Configure the custom expandable list with the [ItemType] categories.
     */
    private fun configureExpandableList(character: CharacterViewModel) {
        with(binding) {
            /*val titleList = ItemType.values().map { it.name }.toList()
            val dataList = hashMapOf(
                ItemType.COMIC.name to character.comics.items.map { it.name },
                ItemType.STORY.name to character.stories.items.map { it.name },
                ItemType.EVENT.name to character.events.items.map { it.name },
                ItemType.SERIES.name to character.series.items.map { it.name }
            )
            comicListView.setAdapter(ItemExpandableListAdapter(requireContext(), titleList, dataList) {
                //TODO: Do something else here.
                Toast.makeText(requireContext(), "WIP", Toast.LENGTH_SHORT).show()
            })*/
        }
    }
}