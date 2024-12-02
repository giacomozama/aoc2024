package st.contraptioni.aoc2024

import java.io.File
import kotlin.time.Duration
import kotlin.time.measureTime

private fun printRow(id: Int, result1: Any, result2: Any, time: Duration) {
    println("╠═════╬═════════════════════╬═════════════════════╬════════════════╣")
    val text = "║ " + id.toString().padEnd(3, ' ') + " ║ " +
        result1.toString().padEnd(19, ' ') + " ║ " +
        result2.toString().padEnd(19, ' ') + " ║ " +
        time.toString().padEnd(14, ' ') + " ║"
    println(text)
}

private fun <I : Any, R1 : Any, R2 : Any> solveProblem(id: Int, problem: Problem<I, R1, R2>) {
    val rawInput = File("input/$id.txt").readText().trimEnd('\n')
    val parsedInput = problem.parseInput(rawInput)
    val result1: Any
    val result2: Any
    val time = measureTime {
        result1 = problem.solvePart1(parsedInput)
        result2 = problem.solvePart2(parsedInput)
    }
    printRow(id, result1, result2, time)
}

fun main() {
    println("╔══════════════════════════════════════════════════════════════════╗")
    println("║ ADVENT OF CODE -------------------------------------------- 2024 ║")
    println("╠═════╦═════════════════════╦═════════════════════╦════════════════╣")
    println("║  #  ║    PART 1 RESULT    ║    PART 2 RESULT    ║      TIME      ║")

    for (id in 1..24) {
        solveProblem(id, Problem.INDEX[id - 1]())
    }

    println("╚═════╩═════════════════════╩═════════════════════╩════════════════╝")
}
