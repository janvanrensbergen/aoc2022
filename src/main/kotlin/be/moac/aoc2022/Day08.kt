package be.moac.aoc2022

fun main() {
    val input: List<String> = "/day08_input.txt".readLines { it }

    println("Part one: ${timed { Day08 partOne input }}")
    println("Part two: ${timed { Day08 partTwo input }}")
}


object Day08 {

    infix fun partOne(input: List<String>): Int =
        with(input.field) { this.trees.count { (coordinate, tree) -> this.isVisible(coordinate, tree) } }

    infix fun partTwo(input: List<String>): Int =
        with(input.field) { this.innerTrees.maxOf { (coordinate, tree) -> this.calculateScenicScore(coordinate, tree) } }

}

private val List<String>.field: TreeField
    get() {
        val trees = this.flatMapIndexed { y, row -> row.mapIndexed { x, value -> Coordinate(x, y) to Tree(value.digitToInt()) } }.toMap()
        return TreeField(trees)
    }


private data class TreeField(val trees: Map<Coordinate, Tree>) {
    private val sizeX: Int = trees.maxOf { it.key.x }
    private val sizeY: Int = trees.maxOf { it.key.y }

    val innerTrees =
        trees.filterNot { it.key.x == 0 || it.key.x == sizeX || it.key.y == 0 || it.key.y == sizeY }

    fun calculateScenicScore(coordinate: Coordinate, tree: Tree): Int {
        val left = coordinate.leftTo().firstOrNull { it.second.height >= tree.height }?.let {  coordinate.x - it.first.x } ?: coordinate.x
        val right = coordinate.rightTo().firstOrNull { it.second.height >= tree.height }?.let {  it.first.x - coordinate.x } ?: (sizeX - coordinate.x)
        val up = coordinate.upTo().firstOrNull { it.second.height >= tree.height }?.let {  coordinate.y - it.first.y } ?: coordinate.y
        val down = coordinate.downTo().firstOrNull { it.second.height >= tree.height }?.let {  it.first.y - coordinate.y } ?: (sizeY - coordinate.y)

        return left * right * up * down
    }

    private fun Coordinate.leftTo() = (this.x - 1 downTo 0).asSequence().map { Coordinate(it, this.y) to trees[Coordinate(it, this.y)]!! }
    private fun Coordinate.rightTo() = (this.x + 1..sizeX).asSequence().map { Coordinate(it, this.y) to trees[Coordinate(it, this.y)]!! }
    private fun Coordinate.upTo() = (this.y - 1 downTo 0).asSequence().map { Coordinate(this.x, it) to trees[Coordinate(this.x, it)]!! }
    private fun Coordinate.downTo() = (this.y + 1..sizeY).asSequence().map { Coordinate(this.x, it) to trees[Coordinate(this.x, it)]!! }

    fun isVisible(coordinate: Coordinate, tree: Tree) =
        when {
            coordinate.x == 0 || coordinate.x == sizeX -> true
            coordinate.y == 0 || coordinate.y == sizeY -> true
            else -> tree.isVisibleInternal(coordinate)
        }

    private fun Tree.isVisibleInternal(coordinate: Coordinate): Boolean =
        coordinate.leftTo().all { it.second.height < this.height } ||
                coordinate.downTo().all { it.second.height < this.height } ||
                coordinate.rightTo().all { it.second.height < this.height } ||
                coordinate.upTo().all { it.second.height < this.height }

}

private data class Coordinate(val x: Int, val y: Int)
private data class Tree(val height: Int)
