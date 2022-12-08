package be.moac.aoc2022

import java.io.File
import kotlin.time.*
import kotlin.time.DurationUnit.MILLISECONDS

fun String.readResource(): String = object {}.javaClass.getResource(this)?.readText() ?: ""
fun <R> String.readLines(map: (String) -> R = { i -> i as R }): List<R> = File(object {}.javaClass.getResource(this)!!.file)
    .readLines()
    .map(map)


@ExperimentalTime
fun <T> timed(x:Int = 100, block: () -> T): T {
    val timed = measureTimedValue(block)

    val result = (1..x)
        .fold(Timed.from(timed)) { acc, _ ->
            acc.add(measureTimedValue(block))
        }

    println("Min: ${result.min.toDouble(MILLISECONDS)} ms - Max: ${result.max.toDouble(MILLISECONDS)} ms - Average: ${result.total.map { it.inWholeMilliseconds }.average()} ms")

    return result.solution
}

private data class Timed<T>(val min: Duration, val max: Duration, val total: Array<Duration>, val solution: T) {

    @ExperimentalTime
    fun add(value: TimedValue<T>) = Timed(
        min = if (min > value.duration) value.duration else min,
        max = if (max < value.duration) value.duration else max,
        total = total + value.duration,
        solution = value.value
    )

    companion object {
        @ExperimentalTime
        fun <T> from(value: TimedValue<T>) = Timed(
            min = value.duration,
            max = value.duration,
            total = arrayOf(value.duration),
            solution = value.value
        )
    }
}

