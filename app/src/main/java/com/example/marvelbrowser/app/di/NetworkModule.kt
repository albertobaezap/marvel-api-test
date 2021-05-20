package com.example.marvelbrowser.app.di

import com.example.marvelbrowser.BuildConfig
import com.example.marvelbrowser.app.utils.md5
import com.example.marvelbrowser.app.utils.toHex
import com.example.marvelbrowser.data.character.CharacterDataRepository
import com.example.marvelbrowser.data.character.remote.CharacterRemoteDataSource
import com.example.marvelbrowser.domain.repositories.CharacterRepository
import com.example.marvelbrowser.domain.usecases.GetCharacter
import com.example.marvelbrowser.domain.usecases.GetCharacterList
import com.example.marvelbrowser.network.MarvelApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Kodein module for all the network dependencies.
 */
val networkModule = Kodein.Module("NetworkModule") {

    bind<OkHttpClient>() with singleton {
        OkHttpClient.Builder()
            .addInterceptor(getAuthInterceptor())
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

    bind<Retrofit>() with singleton { getRetrofitInstance(instance()) }
    bind<MarvelApi>() with singleton { getMarvelAppService(instance()) }
    bind<CharacterRemoteDataSource>() with singleton { getCharacterRemoteDataSource(instance()) }
    bind<CharacterRepository>() with singleton { getCharacterRepository(instance()) }
    bind<GetCharacterList>() with singleton { getCharacterList(instance())}
    bind<GetCharacter>() with singleton { getCharacter((instance())) }
}

/**
 * Hardcode credentials.
 */
private fun getAuthInterceptor() =
    Interceptor {
        val request = it.request()

        val timestamp = System.currentTimeMillis()
        val hash = "$timestamp${BuildConfig.PRIVATE_KEY}${BuildConfig.PUBLIC_KEY}".md5()
        val url = request.url.newBuilder()
            .addQueryParameter("ts", timestamp.toString())
            .addQueryParameter("apikey", BuildConfig.PUBLIC_KEY)
            .addQueryParameter("hash", hash.toHex())
            .build()

        val newRequest = request.newBuilder().url(url).build()
        it.proceed(newRequest)
    }

private fun getRetrofitInstance(client: OkHttpClient) =
    Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

private fun getMarvelAppService(retrofit: Retrofit) =
    retrofit.create(MarvelApi::class.java)

private fun getCharacterRemoteDataSource(apiService: MarvelApi) = CharacterRemoteDataSource(apiService)

private fun getCharacterRepository(remoteDataSource: CharacterRemoteDataSource) = CharacterDataRepository(remoteDataSource)

private fun getCharacterList(repository: CharacterRepository) =
    GetCharacterList(repository)

private fun getCharacter(repository: CharacterRepository) =
    GetCharacter(repository)