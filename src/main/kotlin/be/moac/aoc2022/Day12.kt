package be.moac.aoc2022


fun main() {
    val input: List<String> = "/day12_input.txt".readLines { it }

    println("Part one: ${timed(0) { Day12 partOne input }}")
    println("Part two: ${timed(0) { Day12 partTwo input }}")
}

object Day12 {

    infix fun partOne(input: List<String>): Long {
        val heightMap =input.parse()
        heightMap.neighBoursOf(Point(Coord(1,-1), 'b'.code)).print()

        return 1
    }
    infix fun partTwo(input: List<String>): Long = partOne(input)
}


private fun List<String>.parse(): HeightMap {
    var start = Point(Coord(-1,-1), 0)
    var end = Point(Coord(-1,-1), 0)
    val map = this.flatMapIndexed{ y, line->
        line.mapIndexed { x, c ->
            when(c) {
                'S' -> {start=Point(Coord(x, -y) ,'a'.code); Point(Coord(x, -y) ,'a'.code)}
                'E' -> {end=Point(Coord(x, -y), 'z'.code);  Point(Coord(x, -y), 'z'.code)}
                else -> Point(Coord(x, -y), c.code)
            }
        }
    }.associateBy { it.coord }
    return HeightMap(start, end, map)
}


private data class HeightMap(
    val start: Point,
    val finish: Point,
    val map: Map<Coord, Point>
) {
    private val up = Coord(0, 1)
    private val down = Coord(0, -1)
    private val left = Coord(-1, 0)
    private val right = Coord(1, 0)

    fun neighBoursOf(point: Point) =
        listOf(left, down, right, up)
            .map { point.coord + it }
            .mapNotNull { map[it] }
            .filter { point.height <= it.height }

}
private data class Coord(val x: Int, val y: Int) {
    operator fun plus(coord: Coord) =
        Coord(this.x + coord.x, this.y + coord.y)

}
private data class Point(val coord: Coord, val height: Int)