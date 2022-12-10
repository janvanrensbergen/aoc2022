package be.moac.aoc2022

import be.moac.aoc2022.Instruction.AddX
import be.moac.aoc2022.Instruction.Noop


fun main() {
    val input: List<String> = "/day10_input.txt".readLines { it }

    println("Part one: ${timed(10) { Day10 partOne input }}")
    println("Part two: ${timed(0) { Day10 partTwo input }}")
}

object Day10 {

    infix fun partOne(input: List<String>): Long =
       run(Cpu(instructions = input.parse())).signalStrength


    infix fun partTwo(input: List<String>): Unit = run(Cpu(instructions = input.parse())).printCrt()
}

private fun List<String>.parse() =
    this.fold(ArrayDeque<Instruction>()) { acc, it ->
        when {
            it.startsWith("addx") -> acc.apply { this.addFirst(AddX(it.removePrefix("addx ").toInt())) }
            else -> acc.apply { this.addFirst(Noop) }
        }
    }

private tailrec fun run(cpu: Cpu): Cpu =
    when {
        cpu.instructions.isEmpty() -> cpu
        else -> run(cpu.cycle())
    }

private data class Cpu(
    val register: Long = 1,
    val cycle: Int = 0,
    val signalStrength: Long = 0,
    val instructions: ArrayDeque<Instruction>,
    val crt: String = "",
) {

    fun cycle(): Cpu {
        return when (val instruction = instructions.removeLast()) {
            is AddX -> this.internalCycle(Noop).internalCycle(instruction)
            Noop -> this.internalCycle(instruction)
        }
    }

    private fun internalCycle(instruction: Instruction): Cpu {
        return this.copy(
            cycle = this.cycle + 1,
            register = instruction.addToRegister(this.register),
            signalStrength = calculateStrength(),
            crt = calculateCrt()
        )
    }

    private fun calculateCrt() =
        when (cycle - (cycle / 40) * 40) {
            in (register - 1..register + 1) -> "$crt#"
            else -> "$crt."
        }

    private fun calculateStrength() =
        if (listOf(20, 60, 100, 140, 180, 220).contains(this.cycle)) {
            this.signalStrength + (this.cycle * this.register)
        } else {
            this.signalStrength
        }


    fun print() = this.also { println("Cycle [$cycle] - Register [$register]") }
    fun printCrt()  {
        crt.windowed(40, 40, true).forEach { println(it) }
    }
}


private sealed interface Instruction {

    fun addToRegister(register: Long) = register

    object Noop : Instruction
    data class AddX(val value: Int) : Instruction {
        override fun addToRegister(register: Long): Long = register + value
    }
}