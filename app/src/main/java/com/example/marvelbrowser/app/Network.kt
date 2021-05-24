package com.example.marvelbrowser.app

import com.example.marvelbrowser.data.network.MarvelApi
import com.example.myapplication.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest

fun String.md5(): ByteArray = MessageDigest.getInstance("MD5").digest(this.toByteArray())
fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }

class Network {

    val service: MarvelApi = buildRetrofit().create(MarvelApi::class.java)

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

    private fun buildRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(getAuthInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

}