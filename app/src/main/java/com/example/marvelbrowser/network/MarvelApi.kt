package com.example.marvelbrowser.network

import com.example.marvelbrowser.model.CharacterDataWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getCharacterList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<CharacterDataWrapper>

    @GET("/v1/public/characters/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): Response<CharacterDataWrapper>
}