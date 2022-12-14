package be.moac.aoc2022

import java.util.*
import kotlin.collections.ArrayDeque

fun main() {
    val input: List<String> = "/day13_input.txt".readLines { it }

    println("Part one: ${timed { Day13 partOne input }}")
    println("Part two: ${timed { Day13 partTwo input }}")
}

object Day13 {

    infix fun partOne(input: List<String>): Long {
       return input.windowed(3, 3, true)
           .asSequence()
            .mapIndexed { index, it -> index + 1 to (it[0].parseData() < it[1].parseData()) }
            .filter { (_, result) -> result }
            .sumOf { it.first }
            .toLong()
    }

    infix fun partTwo(input: List<String>): Long {
        val sorted = (input + "[[2]]" + "[[6]]")
            .filterNot { it.isBlank() }
            .map { it.parseData() }
            .sorted()
            .onEach { it ->it.print() }

        val firstDecoderKey = sorted.indexOf("[[2]]".parseData()) + 1
        val secondDecoderKey = sorted.indexOf("[[6]]".parseData()) + 1
        return firstDecoderKey.times(secondDecoderKey).toLong()
    }

}


fun String.parseData(): PacketData<*> {
    val queue = this.fold(ArrayDeque<PacketData<*>>() to "") { (queue, current), char ->
        when (char) {
            '[' -> {
                queue.addLast(PacketData.Nested(emptyList()))
                queue to ""
            }

            ',' -> {
                if (current.isNotBlank()) {
                    when (val last = queue.removeLast()) {
                        is PacketData.IntData -> TODO()
                        is PacketData.Nested -> queue.addLast(last.copy(data = last.data + PacketData.IntData(current.toInt())))
                    }
                }
                queue to ""
            }

            ']' -> {
                if (current.isNotBlank()) {
                    when (val last = queue.removeLast()) {
                        is PacketData.IntData -> TODO()
                        is PacketData.Nested -> queue.addLast(last.copy(data = last.data + PacketData.IntData(current.toInt())))
                    }
                }

                val last = queue.removeLast()
                when {
                    queue.isEmpty() -> queue.addLast(last)
                    else -> {
                        val l = queue.removeLast()
                        when (l) {
                            is PacketData.IntData -> TODO()
                            is PacketData.Nested -> queue.addLast(l.copy(data = l.data + last))
                        }
                    }
                }
                queue to ""
            }

            else -> queue to "$current$char"
        }
    }
    return queue.first.first() //.print()
}

fun PacketData<*>.print(): PacketData<*> {
    fun join(data: PacketData<*>): String {
        return when (data) {
            is PacketData.IntData -> data.data.joinToString(prefix = "", separator = ",", postfix = "") { "$it" }
            is PacketData.Nested -> data.data.joinToString(prefix = "[", separator = ",", postfix = "]") { join(it) }
        }
    }

    println(join(this))
    return this
}


sealed interface PacketData<T> : Comparable<PacketData<*>>{

    override operator fun compareTo(other: PacketData<*>): Int {
        fun compare(left: PacketData<*>, right: PacketData<*> ): Int {
            val fold = left.data.foldIndexed(0) { index, result, next ->
                val otherNext = right.data.getOrNull(index)
                when {
                    result != 0 -> result
                    otherNext == null -> 1
                    next is IntData && otherNext is IntData -> next.compareTo(otherNext)
                    next is Nested && otherNext is Nested -> compare(next, otherNext)
                    next is IntData && otherNext is Nested -> compare(Nested(listOf(next)), otherNext)
                    next is Nested && otherNext is IntData -> compare(next, Nested(listOf(otherNext)))
                    else -> 0
                }
            }
            return if(fold != 0) fold else if (left.data.size == right.data.size) 0 else left.data.size.compareTo(right.data.size)
        }
        val result = compare(this, other)
        return if(result != 0) result else this.data.size.compareTo(other.data.size)
    }

    val data: List<T>

    data class Nested(override val data: List<PacketData<*>>) : PacketData<PacketData<*>> {
        override fun compareTo(other: PacketData<*>): Int {
            return super.compareTo(other)
        }
    }

    data class IntData(val value: Int) : PacketData<Int> {
        override val data: List<Int>
            get() = listOf(value)

        operator fun compareTo(other: IntData): Int {
            return value.compareTo(other.value)
        }
    }
}


