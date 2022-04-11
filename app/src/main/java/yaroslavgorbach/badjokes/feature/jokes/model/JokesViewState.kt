package yaroslavgorbach.badjokes.feature.jokes.model

import yaroslavgorbach.badJokes.common_ui.utill.UiMessage
import yaroslavgorbach.badjokes.data.local.model.JokeEntity

data class JokesViewState(
    val jokes: List<JokeEntity> = emptyList(),
    val isLoading: Boolean = true,
    val message: UiMessage<JokesUiMessage>? = null
) {
    companion object {
        val Empty = JokesViewState()
    }
}
