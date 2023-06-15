import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.round

fun convertMinuteToHourMinute(
    minutes: Int?
) : Pair<Int, Int> {
    if (minutes == null) return Pair(0, 0) // TODO better null checks

    val hours = TimeUnit.MINUTES.toHours(minutes.toLong())
    val remainingMinutes = minutes.minus(TimeUnit.HOURS.toMinutes(hours))

    return Pair(hours.toInt(), remainingMinutes.toInt())
}

fun convertDecimalToFraction(decimal: Float?) : String {
    if (decimal == null) return "null"

    val intVal = floor(decimal)
    val fVal = decimal - intVal

    if (fVal == 0f) return "${intVal.toLong()}"

    val pVal = 1000000000L
    val gcdVal = gcd(round(fVal * pVal).toLong(), pVal)

    val num = round(fVal * pVal) / gcdVal
    val denom = pVal / gcdVal
    val nom = (intVal * denom + num).toLong()

    return "$nom/$denom"

}

private fun gcd(a: Long, b: Long) : Long {
    var n1 = abs(a)
    var n2 = abs(b)
    while (n1 != n2) {
        if (n1 > n2)
            n1 -= n2
        else
            n2 -= n1
    }
    return n1
}