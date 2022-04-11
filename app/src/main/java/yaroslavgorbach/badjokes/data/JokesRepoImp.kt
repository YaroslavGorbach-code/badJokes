package yaroslavgorbach.badjokes.data

import yaroslavgorbach.badjokes.data.local.model.JokeEntity
import yaroslavgorbach.badjokes.data.remote.JokesService
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JokesRepoImp @Inject constructor(private val jokesApi: JokesService) : JokesRepo {

    private var cashedJokes: MutableList<JokeEntity> = ArrayList()

    override suspend fun getJokes(size: Int): Result<List<JokeEntity>> {
        if (cashedJokes.size <= 5) {
            try {
                cashedJokes.addAll(jokesApi.jokes().jokes?.map(::JokeEntity) ?: emptyList())
            } catch (e: IOException) {
                return Result.failure(Throwable())
            }
        }
        val jokes = cashedJokes.take(size)
        cashedJokes = cashedJokes.drop(size).toMutableList()
        return Result.success(jokes)
    }
}