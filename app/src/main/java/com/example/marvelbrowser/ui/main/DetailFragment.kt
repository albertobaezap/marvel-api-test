package com.example.marvelbrowser.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.marvelbrowser.databinding.DetailFragmentBinding
import com.example.marvelbrowser.di.mainViewModel
import com.example.marvelbrowser.ui.adapter.CharacterListAdapter
import com.squareup.picasso.Picasso
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import timber.log.Timber

class DetailFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewModel: MainViewModel by mainViewModel()

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

    private fun setupViews() {
        with(binding) {

            viewModel.getCharacterLiveData().observe(viewLifecycleOwner, {
                val character = it.first { it.id == safeArgs.characterId }
                Timber.d("Loaded character $character")
                characterDetailName.text = character.name
                characterDetailDescription.text = character.description

                //Avoid non-valid images
                if (!character.thumbnail.path.contains(CharacterListAdapter.INVALID_IMAGE_PATH)) {
                    val thumbnailUrl =
                        "${character.thumbnail.path}.${character.thumbnail.extension}"
                    Picasso.with(root.context)
                        //There is an error when trying to load HTTP images from the source, so we'll change protocols
                        .load(thumbnailUrl.replace("http", "https"))
                        .into(characterDetailImage)
                }
            })
        }
    }

}