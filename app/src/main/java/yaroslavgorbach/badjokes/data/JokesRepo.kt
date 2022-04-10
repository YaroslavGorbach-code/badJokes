package yaroslavgorbach.badjokes.data

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.badjokes.data.local.model.JokeEntity

interface JokesRepo {
    suspend fun observe(): List<JokeEntity>
}