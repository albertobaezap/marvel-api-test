package com.example.marvelbrowser.domain.entities

data class CharacterDataWrapper(
    val code: Int?,
    val limit: Int?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val data: CharacterDataContainer,
    val etag: String
)