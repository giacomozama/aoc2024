package st.contraptioni.aoc2024

import java.io.File
import kotlin.system.exitProcess
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

    for (problemNo in problemsToRun) {
        val rawInput = File("input/$problemNo.txt").readText().trimEnd('\n')
        val result1: Any
        val result2: Any
        val time = measureTime {
            Problem.INDEX[problemNo - 1]().solve(rawInput) { r1, r2 ->
                result1 = r1
                result2 = r2
            }
        }
        println("Problem $problemNo - Part 1: $result1, Part 2: $result2, Time: $time")
    }
}
