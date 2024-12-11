package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem11 : Problem<IntArray, Long, Long>() {

    override fun parseInput(rawInput: String): IntArray {
        return rawInput.split(" ").map { it.toInt() }.toIntArray()
    }

    private fun solveForIterations(input: IntArray, iterations: Int): Long {
        val memo = hashMapOf<Long, Long>()

        fun calc(n: Long, remaining: Int): Long {
            if (remaining == 0) return 1

            return memo.getOrPut(n * 100L + remaining) {
                if (n == 0L) {
                    calc(1, remaining - 1)
                } else {
                    var r = n
                    var digitCount = 0
                    while (r > 0) {
                        r /= 10
                        digitCount++
                    }

                    if (digitCount and 1 == 0) {
                        var mask = 1
                        for (k in 1..digitCount / 2) mask *= 10
                        calc(n / mask, remaining - 1) + calc(n % mask, remaining - 1)
                    } else {
                        calc(n * 2024, remaining - 1)
                    }
                }
            }
        }

        return input.sumOf { calc(it.toLong(), iterations) }
    }

    override fun solvePart1(input: IntArray): Long {
        return solveForIterations(input, 25)
    }

    override fun solvePart2(input: IntArray): Long {
        return solveForIterations(input, 75)
    }
}
