package st.contraptioni.aoc2024

import java.io.File
import kotlin.system.exitProcess
import kotlin.time.Duration
import kotlin.time.measureTime

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

    println("╔══════════════════════════════════════════════════════════════════╗")
    println("║ ADVENT OF CODE -------------------------------------------- 2024 ║")
    println("╠═════╦═════════════════════╦═════════════════════╦════════════════╣")
    println("║  #  ║    PART 1 RESULT    ║    PART 2 RESULT    ║      TIME      ║")

    fun printRow(id: Int, result1: Any, result2: Any, time: Duration) {
        println("╠═════╬═════════════════════╬═════════════════════╬════════════════╣")
        val text = "║ " + id.toString().padEnd(3, ' ') + " ║ " +
            result1.toString().padEnd(19, ' ') + " ║ " +
            result2.toString().padEnd(19, ' ') + " ║ " +
            time.toString().padEnd(14, ' ') + " ║"
        println(text)
    }

    for (problemId in problemsToRun) {
        val rawInput = File("input/$problemId.txt").readText().trimEnd('\n')
        val result1: Any
        val result2: Any
        val time = measureTime {
            Problem.INDEX[problemId - 1]().solve(rawInput) { r1, r2 ->
                result1 = r1
                result2 = r2
            }
        }
        printRow(problemId, result1, result2, time)
    }

    println("╚═════╩═════════════════════╩═════════════════════╩════════════════╝")
}
