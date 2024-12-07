package st.contraptioni.aoc2024.problems

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import st.contraptioni.aoc2024.Problem

class Problem7 : Problem<List<Problem7.Calibration>, Long, Long>() {

    class Calibration(val result: Long, val operators: List<Int>)

    override fun parseInput(rawInput: String): List<Calibration> {
        return rawInput.lines().map { l ->
            val result = l.substringBefore(": ").toLong()
            val operators = l.substringAfter(": ").split(" ").map { it.toInt() }
            Calibration(result, operators)
        }
    }

    private fun solveForOperations(input: List<Calibration>, vararg operators: (Long, Int) -> Long): Long {
        fun check(calibration: Calibration, acc: Long, i: Int): Boolean {
            if (i == calibration.operators.size) return calibration.result == acc
            if (acc > calibration.result) return false
            return operators.any { check(calibration, it(acc, calibration.operators[i]), i + 1) }
        }

        return runBlocking(Dispatchers.Default) {
            input.map { async { if (check(it, it.operators[0].toLong(), 1)) it.result else 0 } }.awaitAll().sum()
        }
    }

    override fun solvePart1(input: List<Calibration>): Long {
        return solveForOperations(input, Long::plus, Long::times)
    }

    override fun solvePart2(input: List<Calibration>): Long {
        fun concat(a: Long, b: Int): Long {
            var d = 10L
            while (d < b) d *= 10
            return a * d + b
        }

        return solveForOperations(input, Long::plus, Long::times, ::concat)
    }
}
