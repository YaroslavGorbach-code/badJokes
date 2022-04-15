package yaroslavgorbach.badjokes.feature.jokes.model

import yaroslavgorbach.badjokes.data.local.model.JokeEntity
import yaroslavgorbach.badjokes.data.remote.model.Joke

sealed class JokesUiMessage {
    object LoadingFailed : JokesUiMessage()
    class ShareJoke(val joke: JokeEntity) : JokesUiMessage()
}
