package st.contraptioni.aoc2024

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.time.Duration
import kotlin.time.measureTime

fun main() {
    val part1Results = arrayOfNulls<Any>(25)
    val part2Results = arrayOfNulls<Any>(25)
    val durations = arrayOfNulls<Duration>(25)

    fun printRow(id: Int) {
        println("╠═════╬═════════════════════╬═══════════════════════════════════════════╬════════════════╣")
        val text = "║ " + id.toString().padEnd(3, ' ') + " ║ " +
            part1Results[id - 1].toString().padEnd(19, ' ') + " ║ " +
            part2Results[id - 1].toString().padEnd(41, ' ') + " ║ " +
            durations[id - 1].toString().padEnd(14, ' ') + " ║"
        println(text)
    }

    fun <I : Any, R1 : Any, R2 : Any> solveProblem(id: Int, problem: Problem<I, R1, R2>) {
        durations[id - 1] = measureTime {
            val rawInput = File("input/$id.txt").readText().trimEnd('\n')
            val parsedInput = problem.parseInput(rawInput)
            part1Results[id - 1] = problem.solvePart1(parsedInput)
            part2Results[id - 1] = problem.solvePart2(parsedInput)
        }
    }

    val totalRuntime = measureTime {
        runBlocking(Dispatchers.Default) {
            for (id in 1..25) {
                launch {
                    solveProblem(id, Problem.INDEX[id - 1]())
                }
            }
        }
    }.toString().padEnd(14, ' ')

    println("╔════════════════════════════════════════════════════════════════════════════════════════╗")
    println("║ ADVENT OF CODE ------------------------------------------------------------------ 2024 ║")
    println("╠═════╦═════════════════════╦═══════════════════════════════════════════╦════════════════╣")
    println("║  #  ║    PART 1 RESULT    ║               PART 2 RESULT               ║      TIME      ║")

    for (id in 1..25) {
        printRow(id)
    }

    println("╠═════╩═════════════════════╩═══════════════════════════════════════════╬════════════════╣")
    println("║ TOTAL RUN TIME                                                        ║ $totalRuntime ║")
    println("╚═══════════════════════════════════════════════════════════════════════╩════════════════╝")
}
