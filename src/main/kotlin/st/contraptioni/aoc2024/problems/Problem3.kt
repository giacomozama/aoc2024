package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem3 : Problem<String, Int, Int>() {

    override fun parseInput(rawInput: String): String {
        return rawInput
    }

    private fun parseMul(result: MatchResult): Int {
        return result.groupValues[1].toInt() * result.groupValues[2].toInt()
    }

    override fun solvePart1(input: String): Int {
        return """mul\(([1-9]\d{0,2}),([1-9]\d{0,2})\)""".toRegex().findAll(input).sumOf(::parseMul)
    }

    override fun solvePart2(input: String): Int {
        val pattern = """don't\(\)|do\(\)|mul\(([1-9]\d{0,2}),([1-9]\d{0,2})\)""".toRegex()
        var ans = 0
        var enabled = true
        for (result in pattern.findAll(input)) {
            when (result.groupValues[0]) {
                "do()" -> {
                    enabled = true
                }
                "don't()" -> {
                    enabled = false
                }
                else -> {
                    if (enabled) {
                        ans += parseMul(result)
                    }
                }
            }
        }
        return ans
    }
}
