package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem
import kotlin.math.abs

class Problem1 : Problem<Array<IntArray>, Int, Int>() {

    override fun parseInput(rawInput: String): Array<IntArray> {
        val lines = rawInput.lines()
        val parsed = Array(2) { IntArray(lines.size) }
        for ((i, line) in lines.withIndex()) {
            parsed[0][i] = line.take(5).toInt()
            parsed[1][i] = line.takeLast(5).toInt()
        }
        return parsed
    }

    override fun solvePart1(input: Array<IntArray>): Int {
        input[0].sort()
        input[1].sort()
        return input[0].indices.sumOf { abs(input[0][it] - input[1][it]) }
    }

    override fun solvePart2(input: Array<IntArray>): Int {
        val count = hashMapOf<Int, Int>()
        for (n in input[1]) {
            count.compute(n) { _, v -> (v ?: 0) + 1 }
        }
        var ans = 0
        for (n in input[0]) {
            ans += n * count.getOrDefault(n, 0)
        }
        return ans
    }
}
