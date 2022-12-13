package be.moac.aoc2022

fun main() {
    val input: List<Long> = "/day13_input.txt".readLines { it.toLong() }

    println("Part one: ${timed { Day13 partOne input }}")
    println("Part two: ${timed { Day13 partTwo input }}")
}

object Day13 {

    infix fun partOne(input: List<Long>): Long = TODO()
    infix fun partTwo(input: List<Long>): Long = TODO()

}


private val regex = "\\[(.+)\\]".toRegex()

fun String.parseData(): PacketData<*> =
    when {
        regex.matches(this) -> PacketData.Nested(listOf(regex.find(this)!!.groupValues[1].parseData()))
        this.contains("],[") -> PacketData.Nested(this.split("],[").map { """[${it.removePrefix("[").removeSuffix("]")}]""".parseData() })
        else ->  when {
            this.isBlank() || this == "[]"-> PacketData.IntData(emptyList())
            this.contains(",") -> PacketData.IntData(this.split(",").map { it.trim().toInt() })
            else -> PacketData.IntData(listOf(this.trim().toInt()))
        }
    }



sealed interface PacketData<T> {
    val data: List<T>

    data class Nested(override val data: List<PacketData<*>>): PacketData<PacketData<*>>
    data class IntData(override val data: List<Int>) : PacketData<Int>
}


