package yaroslavgorbach.badjokes.data.local.model

import yaroslavgorbach.badjokes.data.remote.model.Joke
import yaroslavgorbach.badjokes.data.remote.model.JokeResponse
import yaroslavgorbach.badjokes.data.remote.model.JokeType

data class JokeEntity(
    private val jokeResponse: Joke
) {
    val type: JokeType? = jokeResponse.type
    val joke: String? = jokeResponse.joke
    val isSafe: Boolean? = jokeResponse.isSafe
    val setup: String? = jokeResponse.setup
    val delivery: String? = jokeResponse.delivery
}
