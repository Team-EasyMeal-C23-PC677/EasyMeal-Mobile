import java.util.concurrent.TimeUnit

fun formatRecipeTime(
    minutes: Int?
) : Pair<Int, Int> {
    if (minutes == null) return Pair(0, 0) // TODO better null checks

    val hours = TimeUnit.MINUTES.toHours(minutes.toLong())
    val remainingMinutes = minutes.minus(TimeUnit.HOURS.toMinutes(hours))

    return Pair(hours.toInt(), remainingMinutes.toInt())
}

fun formatRecipeWarning(missing: List<String?>?): String {
    if (missing == null) return "missing null"

    var result = ""

    val length = missing.size
    if (length > 0) {
        result += "${missing[0]}"
    }
    if (length > 1) {
        result += " + ${missing.size - 1}"
    }

    return result
}