package com.example.marvelbrowser.network

import com.example.marvelbrowser.model.CharacterDataWrapper
import retrofit2.http.GET

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getCharacterList(): CharacterDataWrapper
}