package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem19 : Problem<Pair<Set<String>, List<String>>, Int, Long>() {

    override fun parseInput(rawInput: String): Pair<Set<String>, List<String>> {
        val lines = rawInput.lines()
        return lines[0].split(", ").toSet() to lines.drop(2)
    }

    override fun solvePart1(input: Pair<Set<String>, List<String>>): Int {
        val (towels, designs) = input
        val maxTowelLength = towels.maxOf { it.length }

        var ans = 0

        for (design in designs) {
            val dp = BooleanArray(design.length + 1)
            dp[0] = true

            for (i in 1..design.length) {
                for (l in 1..minOf(maxTowelLength, i)) {
                    if (design.substring(i - l, i) in towels && dp[i - l]) {
                        dp[i] = true
                        break
                    }
                }
            }

            if (dp.last()) ans++
        }

        return ans
    }

    override fun solvePart2(input: Pair<Set<String>, List<String>>): Long {
        val (towels, designs) = input
        val maxTowelLength = towels.maxOf { it.length }

        var ans = 0L

        for (design in designs) {
            val dp = LongArray(design.length + 1)
            dp[0] = 1

            for (i in 1..design.length) {
                for (l in 1..minOf(maxTowelLength, i)) {
                    if (design.substring(i - l, i) in towels) {
                        dp[i] += dp[i - l]
                    }
                }
            }

            ans += dp.last()
        }

        return ans
    }
}
