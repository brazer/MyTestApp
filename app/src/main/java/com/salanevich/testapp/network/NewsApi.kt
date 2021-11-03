package com.salanevich.testapp.network

import com.salanevich.testapp.network.response.NewsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun fetchNews(
        @Query("max") count: Int,
        @Query("page") page: Int,
        @Query("nullable") nullable: Boolean,
        @Query("lang") lang: String,
        @Query("token") token: String
    ): NewsResponse

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("max") count: Int,
        @Query("page") page: Int,
        @Query("nullable") nullable: Boolean,
        @Query("lang") lang: String,
        @Query("in") where: String?,
        @Query("from") from: String?,
        @Query("to") to: String?,
        @Query("sortby") sorting: String,
        @Query("token") token: String
    ): NewsResponse

    companion object {
        private const val BASE_URL = "https://gnews.io/api/v4/"
        operator fun invoke(): NewsApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().also { client ->
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

}