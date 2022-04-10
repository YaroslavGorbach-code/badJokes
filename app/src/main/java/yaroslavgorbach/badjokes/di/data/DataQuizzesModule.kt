package yaroslavgorbach.badjokes.di.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import yaroslavgorbach.badjokes.data.JokesRepo
import yaroslavgorbach.badjokes.data.JokesRepoImp
import yaroslavgorbach.badjokes.data.remote.JokesService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataQuizzesModule {

    @Singleton
    @Provides
    fun provideRepo(service: JokesService): JokesRepo {
        return JokesRepoImp(service)
    }

    @Singleton
    @Provides
    fun provideJokesService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): JokesService {
        return JokesService(okHttpClient = okHttpClient, gsonConverterFactory)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Singleton
    @Provides
    fun gsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

}