package koropapps.criptoquiz.base.utill

suspend inline fun <T> T.alsoSuspend(crossinline block: suspend (T) -> Unit) = also { block(it) }

fun calculatePercentage(value: Int, totalValue: Int) =
    if (value > 0) (value.toFloat() / (totalValue.toFloat())) else 0.0f