package com.example.marvelbrowser.presentation.detail

import com.example.marvelbrowser.domain.entities.Character

const val INVALID_IMAGE_PATH = "image_not_available"
const val LOAD_IMAGE_TAG = "load_image"

data class CharacterViewModel(
    val name: String,
    val description: String,
    val image: String?
)

fun Character.toViewModel(): CharacterViewModel {
    val thumbnailUrl = if (!thumbnail.path.contains(INVALID_IMAGE_PATH)) {
        "${thumbnail.path}.${thumbnail.extension}".replace("http", "https")
    } else null

    return CharacterViewModel(name, description, thumbnailUrl)
}