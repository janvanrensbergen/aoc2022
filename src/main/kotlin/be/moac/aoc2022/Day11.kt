package be.moac.aoc2022


fun main() {
    val input: List<String> = "/day11_input.txt".readLines { it }

    println("Part one: ${timed(0) { Day11 partOne input }}")
    println("Part two: ${timed(0) { Day11 partTwo input }}")
}

object Day11 {

    infix fun partOne(input: List<String>): Long {
        val monkeys = input.windowed(7, 7, true).map { it.asMonkey() }.associateBy { it.name }

        tailrec fun playRound(monkeys: Map<String, Monkey>, names: List<String>): Map<String, Monkey> {
            return when {
                names.isEmpty() -> monkeys
                else -> {
                    val (monkey, actions) = monkeys[names.first()]!!.doRound()

                    val result = monkeys.toMutableMap().apply {
                        this[monkey.name] = monkey
                        actions
                            .forEach { (item, toMonkey) ->
                                this[toMonkey]?.let { this[toMonkey] =
                                    it.copy(items = it.items + item)  }
                            }
                    }
                    playRound(result, names.drop(1))
                }
            }
        }

        tailrec fun game(counter: Int, monkeys: Monkeys): Monkeys =
            when(counter) {
                21 -> monkeys
                else -> {
                    println("==== round $counter ====")
                    game(counter +1, playRound(monkeys, monkeys.keys.toList()).print())
                }
            }


       val result = game(1, monkeys)
            .values
            .sortedByDescending { it.numberOfItemsInspected }
            .take(2)



        return result.first().numberOfItemsInspected * result.last().numberOfItemsInspected
    }

    infix fun partTwo(input: List<String>): Long = 1
}

private typealias Monkeys = Map<String, Monkey>
private fun Monkeys.print() =
    this.print { this.entries.joinToString("\n") { "${it.key}: ${it.value.items.map { item -> item.worryLevel }} - ${it.value.numberOfItemsInspected}" } }

private data class Monkey(
    val name: String,
    val items: List<Item> = emptyList(),
    val operation: (Item) -> Item,
    val test: (Item) -> Action,
    val numberOfItemsInspected: Long = 0
) {

    fun doRound(): Outcome {
        fun doIt(items: List<Item>, actions: List<Action> = emptyList()): List<Action> {
            return when {
                items.isEmpty() -> actions
                else -> doIt(items.drop(1), actions + test(operation(items.first())))
            }
        }

        return Outcome(this.copy(items = emptyList(), numberOfItemsInspected = numberOfItemsInspected + items.size), doIt(items))
    }
}

private data class Outcome(val monkey: Monkey, val actions: List<Action>)

private data class Item(val worryLevel: Int) {
    operator fun plus(item: Item) = Item(worryLevel = this.worryLevel + item.worryLevel)
    operator fun times(item: Item) = Item(worryLevel = this.worryLevel * item.worryLevel)
    operator fun div(value: Int) = Item(worryLevel = this.worryLevel / value)
}

private data class Action(val item: Item, val toMonkey: String)

private fun List<String>.asMonkey(): Monkey =
    Monkey(
        name = this[0].trim().removeSuffix(":").lowercase(),
        items = this[1].asItems(),
        operation = this[2].asOperation(),
        test = this.subList(3, 6).asTest(),
    )

private fun String.asItems() =
    this.trim().removePrefix("Starting items: ").trim().split(",").map { Item(it.trim().toInt()) }

private fun String.asOperation(): (Item) -> Item {
    fun String.asItem(old: Item) = if (this == "old") old else Item(this.toInt())
    val (left, operator, right) = this.trim().removePrefix("Operation: new = ").trim().split(" ")

    val operation: (Item) -> Item =
        when (operator) {
            "+" -> { item -> left.asItem(item) + right.asItem(item) }
            "*" -> { item -> left.asItem(item) * right.asItem(item) }
            else -> { item -> item }
        }

    return { item -> operation(item) / 3 }
}

private fun List<String>.asTest(): (Item) -> Action {
    val test = { item: Item -> item.worryLevel.rem(this[0].trim().removePrefix("Test: divisible by ").trim().toInt()) == 0 }
    return { item ->
        when (test(item)) {
            true -> Action(item, this[1].trim().removePrefix("If true: throw to ").trim())
            false -> Action(item, this[2].trim().removePrefix("If false: throw to ").trim())
        }
    }
}