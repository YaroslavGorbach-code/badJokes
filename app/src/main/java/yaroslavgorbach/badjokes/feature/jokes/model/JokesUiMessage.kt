package yaroslavgorbach.badjokes.feature.jokes.model

sealed class JokesUiMessage {
    object LoadingFailed : JokesUiMessage()
}
