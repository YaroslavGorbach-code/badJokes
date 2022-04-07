package koropapps.criptoquiz.base.utill

fun <T> Collection<T>.firstOr(default: T) = firstOrNull() ?: default
