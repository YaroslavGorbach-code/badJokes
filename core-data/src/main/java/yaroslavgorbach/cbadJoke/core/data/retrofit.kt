package yaroslavgorbach.cbadJoke.core.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun retrofit(
    okHttpClient: OkHttpClient,
    converterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder().apply {
        baseUrl(JOKES_API_URL)
        client(okHttpClient)
        addConverterFactory(converterFactory)
    }.build()
}


private const val JOKES_API_URL = " https://v2.jokeapi.dev/"
