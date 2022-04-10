package yaroslavgorbach.badjokes.feature.jokes.model

import yaroslavgorbach.badjokes.data.local.model.JokeEntity

data class JokesViewState(
    val jokes: List<JokeEntity> = emptyList(),
    val isLoading: Boolean = true,
) {
    companion object {
        val Empty = JokesViewState()
    }
}
