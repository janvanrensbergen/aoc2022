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

fun String.parseData(): PacketData<*> {
    val queue = this.fold(ArrayDeque<PacketData<*>>() to "") { (queue, current), char ->
        when(char) {
            '[' -> {
                queue.addLast(PacketData.Nested(emptyList()))
                queue to ""
            }
            ',' -> {
                if(current.isNotBlank()){
                    when(val last = queue.removeLast()) {
                        is PacketData.IntData -> queue.addLast(last.copy(data = last.data + current.toInt()))
                        is PacketData.Nested -> queue.addLast(last.copy(data = last.data + PacketData.IntData(listOf(current.toInt()))))
                    }
                }
                queue to ""
            }
            ']' -> {
                if(current.isNotBlank()){
                    when(val last = queue.removeLast()) {
                        is PacketData.IntData -> queue.addLast(last.copy(data = last.data + current.toInt()))
                        is PacketData.Nested -> queue.addLast(last.copy(data = last.data + PacketData.IntData(listOf(current.toInt()))))
                    }
                }

                val last = queue.removeLast()
                when {
                    queue.isEmpty() -> queue.addLast(last)
                    else -> {
                        val last = queue.removeLast()
                        when(last) {
                            is PacketData.IntData -> TODO()
                            is PacketData.Nested -> TODO()
                        }
                        queue.last().data.toMutableList().apply { add(last) }
                    }
                }
                queue to ""
            }
            else -> queue to "$current$char"
        }
    }
    return queue.first.first()
}




sealed interface PacketData<T> {
    val data: List<T>

    data class Nested(override val data: List<PacketData<*>>): PacketData<PacketData<*>>
    data class IntData(override val data: List<Int>) : PacketData<Int>
}


