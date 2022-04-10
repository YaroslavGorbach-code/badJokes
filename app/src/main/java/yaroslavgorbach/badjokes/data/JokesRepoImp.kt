package yaroslavgorbach.badjokes.data

import android.util.Log
import yaroslavgorbach.badjokes.data.local.model.JokeEntity
import yaroslavgorbach.badjokes.data.remote.JokesService

class JokesRepoImp(private val jokesApi: JokesService) : JokesRepo {
    override suspend fun observe(): List<JokeEntity> {
        val d = jokesApi.jokes().jokes
        return d!!.map(::JokeEntity)
    }
}