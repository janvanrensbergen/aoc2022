package be.moac.aoc2022

fun main() {
    val input: List<String> = "/day08_input.txt".readLines { it }

    println("Part one: ${timed(10) { Day08 partOne input }}")
    println("Part two: ${timed(1) { Day08 partTwo input }}")
}


object Day08 {

    infix fun partOne(input: List<String>): Int =
        with(input.field) { this.trees.count { tree -> this.isVisible(tree) } }

    infix fun partTwo(input: List<String>): Int =
        with(input.field) { this.innerTrees.maxOf { tree -> this.calculateScenicScore(tree) } }

}

private val List<String>.field: TreeField
    get() {
        val trees = this.flatMapIndexed { y, row ->
            row.mapIndexed { x, value -> Tree(Coordinate(x, y), value.digitToInt()) }
        }.toList()

        return TreeField(trees)
    }


private data class TreeField(val trees: List<Tree> = emptyList()) {
    private val sizeX: Int = trees.maxOf { it.coordinate.x }
    private val sizeY: Int = trees.maxOf { it.coordinate.y }

    val innerTrees =
        trees.filterNot { it.coordinate.x == 0 || it.coordinate.x == sizeX || it.coordinate.y == 0 || it.coordinate.y == sizeY }

    fun calculateScenicScore(tree: Tree): Int {
        val surroundingTrees = trees.filter { it != tree && (it.coordinate.x == tree.coordinate.x || it.coordinate.y == tree.coordinate.y) }

        val left = surroundingTrees.asSequence().filter { it.coordinate.x < tree.coordinate.x }.sortedByDescending { it.coordinate.x }
            .takeWhileInclusive { it.height < tree.height }.count()
        val right = surroundingTrees.asSequence().filter { it.coordinate.x > tree.coordinate.x }.sortedBy { it.coordinate.x }
            .takeWhileInclusive { it.height < tree.height }.count()
        val top = surroundingTrees.asSequence().filter { it.coordinate.y < tree.coordinate.y }.sortedByDescending { it.coordinate.y }
            .takeWhileInclusive { it.height < tree.height }.count()
        val bottom = surroundingTrees.asSequence().filter { it.coordinate.y > tree.coordinate.y }.sortedBy { it.coordinate.y }
            .takeWhileInclusive { it.height < tree.height }.count()
        return left * right * top * bottom
    }

    fun isVisible(tree: Tree) =
        when {
            tree.coordinate.x == 0 || tree.coordinate.x == sizeX -> true
            tree.coordinate.y == 0 || tree.coordinate.y == sizeY -> true
            else -> tree.isVisibleInternal()
        }

    private fun Tree.isVisibleInternal(): Boolean {
        val surroundingTrees = trees.filter { it != this && (it.coordinate.x == this.coordinate.x || it.coordinate.y == this.coordinate.y) }
        return surroundingTrees.isVisibleRight(this) || surroundingTrees.isVisibleLeft(this) || surroundingTrees.isVisibleTop(this) || surroundingTrees.isVisibleBottom(
            this
        )
    }

    private fun List<Tree>.isVisibleLeft(tree: Tree) = this.filter { it.coordinate.x < tree.coordinate.x }.all { it.height < tree.height }
    private fun List<Tree>.isVisibleRight(tree: Tree) = this.filter { it.coordinate.x > tree.coordinate.x }.all { it.height < tree.height }
    private fun List<Tree>.isVisibleTop(tree: Tree) = this.filter { it.coordinate.y < tree.coordinate.y }.all { it.height < tree.height }
    private fun List<Tree>.isVisibleBottom(tree: Tree) = this.filter { it.coordinate.y > tree.coordinate.y }.all { it.height < tree.height }
}

private data class Coordinate(val x: Int, val y: Int)
private data class Tree(val coordinate: Coordinate, val height: Int)
