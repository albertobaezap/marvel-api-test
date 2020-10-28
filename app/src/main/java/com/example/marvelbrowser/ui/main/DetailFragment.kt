package com.example.marvelbrowser.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.marvelbrowser.data.CharacterStoreController
import com.example.marvelbrowser.databinding.DetailFragmentBinding
import com.example.marvelbrowser.model.Character
import com.example.marvelbrowser.model.ItemType
import com.example.marvelbrowser.ui.adapter.CharacterListAdapter
import com.example.marvelbrowser.ui.adapter.ItemExpandableListAdapter
import com.squareup.picasso.Picasso
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import timber.log.Timber

/**
 * Detail fragment for an individual [Character]
 */
class DetailFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val characterStoreController: CharacterStoreController by instance()

    private lateinit var binding: DetailFragmentBinding

    private val safeArgs: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DetailFragmentBinding.inflate(inflater, container, false).apply { binding = this }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViews()
    }

    /**
     * Populate all the necessary views.
     */
    private fun setupViews() {
        with(binding) {

            characterStoreController.getCharacterData(safeArgs.characterId)?.let { character ->
                characterDetailName.text = character.name

                if (character.description.isNotEmpty()) {
                    characterDetailDescription.text = character.description
                } else {
                    characterDetailDescription.visibility = View.GONE
                }

                configureExpandableList(character)

                //Avoid non-valid images
                if (!character.thumbnail.path.contains(CharacterListAdapter.INVALID_IMAGE_PATH)) {
                    val thumbnailUrl =
                        "${character.thumbnail.path}.${character.thumbnail.extension}"
                    Picasso.with(root.context)
                        //There is an error when trying to load HTTP images from the source, so we'll change protocols
                        .load(thumbnailUrl.replace("http", "https"))
                        .into(characterDetailImage)
                }
            }
        }
    }

    /**
     * Configure the custom expandable list with the [ItemType] categories.
     */
    private fun configureExpandableList(character: Character) {
        with(binding) {
            val titleList = ItemType.values().map { it.name }.toList()
            val dataList = hashMapOf(
                ItemType.COMIC.name to character.comics.items.map { it.name },
                ItemType.STORY.name to character.stories.items.map { it.name },
                ItemType.EVENT.name to character.events.items.map { it.name },
                ItemType.SERIES.name to character.series.items.map { it.name }
            )
            comicListView.setAdapter(ItemExpandableListAdapter(requireContext(), titleList, dataList) {
                //TODO: Do something else here.
                Toast.makeText(requireContext(), "WIP", Toast.LENGTH_SHORT).show()
            })
        }
    }
}