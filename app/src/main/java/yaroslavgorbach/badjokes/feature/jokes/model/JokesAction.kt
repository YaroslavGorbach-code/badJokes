package yaroslavgorbach.badjokes.feature.jokes.model

import yaroslavgorbach.badjokes.data.local.model.JokeEntity

sealed class JokesAction {
    class RemoveJoke(val joke: JokeEntity) : JokesAction()
}