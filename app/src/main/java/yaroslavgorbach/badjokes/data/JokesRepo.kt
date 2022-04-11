package yaroslavgorbach.badjokes.data

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.badjokes.data.local.model.JokeEntity

interface JokesRepo {
    suspend fun getJokes(size: Int = 5): Result<List<JokeEntity>>
}