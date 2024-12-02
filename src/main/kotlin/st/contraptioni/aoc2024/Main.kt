package st.contraptioni.aoc2024

import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val problemsToRun = mutableSetOf<Int>()
    for (arg in args) {
        val problemNo = arg.toIntOrNull()?.takeIf { it in 1..24 } ?: run {
            println("Invalid problem number: $arg")
            exitProcess(1)
        }
        problemsToRun.add(problemNo)
    }

    if (problemsToRun.isEmpty()) {
        problemsToRun.addAll(1..24)
    }

    for (problemNo in problemsToRun) {
        val rawInput = File("input/$problemNo.txt").readText()
        val problem = Problem.INDEX[problemNo - 1]()
        println("Problem $problemNo part 1: ${problem.solvePart1(rawInput)}")
        println("Problem $problemNo part 2: ${problem.solvePart2(rawInput)}")
    }
}
