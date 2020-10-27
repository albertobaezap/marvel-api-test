package com.example.marvelbrowser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelbrowser.databinding.CharacterListItemBinding
import com.example.marvelbrowser.model.Character
import com.squareup.picasso.Picasso
import timber.log.Timber

class CharacterListAdapter : RecyclerView.Adapter<CharacterListAdapter.CharacterListItemHolder>() {

    companion object {
        private const val INVALID_IMAGE_PATH = "image_not_available"
    }

    var characterList: MutableList<Character> = mutableListOf()

    fun append(items: List<Character>) {
        characterList.addAll(characterList.count(), items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListItemHolder =
        CharacterListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).run { CharacterListItemHolder(this) }

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

    class CharacterListItemHolder(private val binding: CharacterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

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
                        .into(characterItemThumbnail)
                }

                //Go to detail view
                root.setOnClickListener {
                    Timber.d("Detail view for ${character.name}")
                }
            }
        }
    }
}