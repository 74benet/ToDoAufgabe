package com.example.bookshelf

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// Datenklassen, die die API-Struktur widerspiegeln
data class BookSummary(val id: Int, val author: String, val title: String)
data class BookDetails(
    val id: Int,
    val author: String,
    val year: Int,
    val type: String,
    val publisher: String,
    val language: String,
    val isbn: String,
    val pages: Int,
    val title: String,
    val description: String
)
data class Review(val reviewer: String, val text: String, val stars: Int)

// API-Schnittstelle
interface BookApiService {
    @GET("list")
    suspend fun getBookList(): List<BookSummary>

    @GET("details/{id}")
    suspend fun getBookDetails(@Path("id") id: Int): BookDetails

    @GET("reviews/{id}")
    suspend fun getBookReviews(@Path("id") id: Int): List<Review>
}

// ApiClient mit Logging
object ApiClient {
    private const val BASE_URL = "http://10.0.2.2/"

    // Konfiguration für HttpLoggingInterceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttpClient mit Logging-Interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Retrofit-Instanz
    val service: BookApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookApiService::class.java)
    }
}
