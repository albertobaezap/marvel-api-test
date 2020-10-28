package com.example.marvelbrowser.di

import com.example.marvelbrowser.BuildConfig
import com.example.marvelbrowser.md5
import com.example.marvelbrowser.network.MarvelApi
import com.example.marvelbrowser.toHex
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
