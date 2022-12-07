package be.moac.aoc2022

import kotlin.RuntimeException

fun main() {
    val input: List<String> = "/day07_input.txt".readLines { it }

    println("Part one: ${timed { Day07 partOne input }}")
    println("Part two: ${timed { Day07 partTwo input }}")
}

private val cd = "\\$ cd (.+)".toRegex()
private val ls = "\\$ ls".toRegex()
private val dir = "dir (.+)".toRegex()
private val fileRegex = "(\\d+) (.+)".toRegex()

object Day07 {

    infix fun partOne(input: List<String>): Long =
        input.filesystem
            .dirs
            .filter { it.size < 100_000 }
            .sumOf { it.size }


    infix fun partTwo(input: List<String>): Long {
        val filesystem = input.filesystem
        val spaceNeeded = 30_000_000 - filesystem.freeSpace

        return filesystem.dirs
            .asSequence()
            .sortedBy { it.size }
            .first { it.size > spaceNeeded }
            .size
    }

}

private val List<String>.filesystem get() =
    this.fold(FileSystem()) { fileSystem, line ->
        when {
            cd.matches(line) -> fileSystem.goto(line.dir)
            ls.matches(line) -> fileSystem
            else -> fileSystem.add(line)
        }
    }

private fun FileSystem.print() {
    fun print(suffix: String, dir: Dir) {
        println("$suffix ${dir.name} (dir)")
        dir.children.forEach { print("  $suffix", it) }
        dir.files.forEach { println("  $suffix ${it.name} (file, size=${it.size})") }
    }
    print("- ", this.rootDir)
}


private val String.dir: String get() = cd.find(this)!!.groupValues.last()
private val String.file: File get() {
    val (size, name) = fileRegex.find(this)!!.destructured
    return File(name, size.toLong())
}

private data class FileSystem(
    val rootDir: Dir = Dir("/"),
    val currentDir: Dir = rootDir
) {

    val freeSpace get() = 70_000_000 - rootDir.size

    val dirs: List<Dir> get() =
       listOf(rootDir) + rootDir.childs

    val files: Set<File> get() =
        rootDir.allFiles

    fun goto(dir: String) =
        when(dir) {
            "/" -> this.copy(currentDir = rootDir)
            ".." -> this.copy(currentDir = currentDir.up())
            else -> this.copy(currentDir = currentDir.into(dir))
        }

    fun add(value: String): FileSystem {
        when {
            dir.matches(value) -> currentDir.add(Dir(value.removePrefix("dir ")))
            fileRegex.matches(value) -> currentDir.add(value.file)
        }
        return this
    }
}

private data class Dir(
    val name: String,
) {

    var parent: Dir? = null
    var children: List<Dir> = emptyList()
    var files: Set<File> = emptySet()

    val childs get(): List<Dir> =
        children + children.flatMap { it.childs }

    val allFiles get() =
        files + children.flatMap { it.files }

    val size: Long get() =
        files.sumOf { it.size } + children.sumOf { it.size }

    fun up() = parent ?: throw NoParentFoundException()
    fun into(dir: String) =
        children.firstOrNull { it.name == dir } ?:
            Dir(name = dir).apply { parent = this@Dir }.also { children += it }

    fun add(dir: Dir) {
        if(!children.contains(dir)) children += dir.apply { parent = this@Dir }
    }
    fun add(file: File) {
        files += file
    }
}

private data class File(val name:String, val size: Long)

class NoParentFoundException: RuntimeException()
