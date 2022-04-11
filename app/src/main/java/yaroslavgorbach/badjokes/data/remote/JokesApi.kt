package yaroslavgorbach.badjokes.data.remote

import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import yaroslavgorbach.badjokes.data.remote.model.JokeResponse
import yaroslavgorbach.cbadJoke.core.data.retrofit

interface JokesService {
    @GET("joke/Any")
    suspend fun jokes(
        @Query("amount") amount: Int? = 10
    ): JokeResponse
}

@Suppress("unused")
fun JokesService(
    okHttpClient: OkHttpClient = OkHttpClient(),
    converterFactory: GsonConverterFactory
): JokesService {
    val retrofit = retrofit(
        okHttpClient,
        converterFactory
    )
    return retrofit.create(JokesService::class.java)
}
