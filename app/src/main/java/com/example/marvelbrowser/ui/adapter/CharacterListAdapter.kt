package com.example.marvelbrowser.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelbrowser.GenericDiff
import com.example.marvelbrowser.databinding.CharacterListItemBinding
import com.example.marvelbrowser.model.Character
import com.squareup.picasso.Picasso

/**
 * Adapter class for the [Character] list.
 */
class CharacterListAdapter(private val onItemClicked: (Int) -> Unit) :
    RecyclerView.Adapter<CharacterListAdapter.CharacterListItemHolder>() {

    companion object {
        const val INVALID_IMAGE_PATH = "image_not_available"
        const val LOAD_IMAGE_TAG = "load_image"
    }

    // Inner character list with a diff method to skip duplicate items.
    var characterList: List<Character> = mutableListOf()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(GenericDiff(field, value) { it.id })
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListItemHolder =
        CharacterListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).run { CharacterListItemHolder(this, onItemClicked) }

    override fun onBindViewHolder(holder: CharacterListItemHolder, position: Int) =
        holder.bindCharacter(characterList[position])

    override fun getItemCount(): Int = characterList.count()


    //Overriding these methods help with image loading and scrolling
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class CharacterListItemHolder(
        private val binding: CharacterListItemBinding,
        private val onItemClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        // Binds a Character to a view.
        fun bindCharacter(character: Character) {
            with(binding) {
                characterItemName.text = character.name

                //Avoid non-valid images
                if (!character.thumbnail.path.contains(INVALID_IMAGE_PATH)) {
                    val thumbnailUrl =
                        "${character.thumbnail.path}.${character.thumbnail.extension}"
                    Picasso.with(root.context)
                        //There is an error when trying to load HTTP images from the source, so we'll change protocols
                        .load(thumbnailUrl.replace("http", "https"))
                        .fit()
                        .tag(LOAD_IMAGE_TAG)
                        .into(characterItemThumbnail)
                    characterItemThumbnail.visibility = View.VISIBLE
                }

                //Go to detail view
                root.setOnClickListener {
                    onItemClicked(character.id)
                }
            }
        }
    }
}