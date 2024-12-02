package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem2 : Problem<List<List<Int>>, Int, Int>() {

    override fun parseInput(rawInput: String): List<List<Int>> {
        return rawInput.lines().map { l -> l.split(" ").map { it.toInt() } }
    }

    private fun checkReport(report: List<Int>): Boolean {
        var isIncreasing = false
        var isDecreasing = false
        for (i in 1 until report.size) {
            if (report[i - 1] == report[i]) return false
            isIncreasing = isIncreasing || report[i] > report[i - 1]
            isDecreasing = isDecreasing || report[i] < report[i - 1]
            if (isIncreasing && isDecreasing) return false
            if (isIncreasing) {
                if (report[i] - report[i - 1] > 3) return false
            } else if (isDecreasing) {
                if (report[i - 1] - report[i] > 3) return false
            }
        }
        return true
    }

    override fun solvePart1(input: List<List<Int>>): Int = input.count(::checkReport)

    override fun solvePart2(input: List<List<Int>>): Int = input.count { report ->
        report.indices.any {
            checkReport(report.toMutableList().apply { removeAt(it) })
        }
    }
}
