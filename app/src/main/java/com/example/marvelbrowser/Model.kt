package com.example.myapplication

data class CharacterDataWrapper(
    val data: CharacterDataContainer,
)

data class CharacterDataContainer(
    val results: List<Character>
)

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: Image
)

data class Image(
    val path: String,
    val extension: String
)