package yaroslavgorbach.badJokes.base.utill

fun <T> Collection<T>.firstOr(default: T) = firstOrNull() ?: default
