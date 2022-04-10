package yaroslavgorbach.badjokes.data.remote.model

import com.google.gson.annotations.SerializedName

data class JokeResponse(
    @SerializedName("error")
    val error: Boolean? = null,

    @SerializedName("jokes")
    val jokes: List<Joke>? = null,
)

data class Joke(
    @SerializedName("type")
    private val _type: String? = null,

    @SerializedName("joke")
    val joke: String? = null,

    @SerializedName("safe")
    val isSafe: Boolean? = null,

    @SerializedName("setup")
    val setup: String? = null,

    @SerializedName("delivery")
    val delivery: String? = null
) {
    val type: JokeType?
        get() = when (_type) {
            JokeType.SINGLE.type -> JokeType.SINGLE
            JokeType.TWOPART.type -> JokeType.TWOPART
            else -> null
        }
}
