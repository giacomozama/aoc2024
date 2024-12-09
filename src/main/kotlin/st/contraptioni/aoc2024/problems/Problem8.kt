package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem8 : Problem<Array<CharArray>, Int, Int>() {

    override fun parseInput(rawInput: String): Array<CharArray> {
        return rawInput.lines().map { it.toCharArray() }.toTypedArray()
    }

    private inline fun forEachMatchingPair(input: Array<CharArray>, action: (Int, Int, Int, Int) -> Unit) {
        val byFrequency = hashMapOf<Char, MutableList<Int>>()
        for (y in input.indices) {
            for (x in input.indices) {
                val c = input[y][x]
                if (c == '.') continue
                byFrequency.getOrPut(c) { mutableListOf() }.add(y * 100 + x)
            }
        }

        for (locs in byFrequency.values) {
            for (i in locs.indices) {
                for (j in i + 1 until locs.size) {
                    val y1 = locs[i] / 100
                    val x1 = locs[i] % 100
                    val y2 = locs[j] / 100
                    val x2 = locs[j] % 100

                    action(y1, x1, y2, x2)
                }
            }
        }
    }

    override fun solvePart1(input: Array<CharArray>): Int {
        var ans = 0
        val isAntinode = Array(input.size) { BooleanArray(input[0].size) }

        forEachMatchingPair(input) { y1, x1, y2, x2 ->
            val fy1 = 2 * y1 - y2
            val fx1 = 2 * x1 - x2

            if (fy1 in input.indices && fx1 in input[0].indices && !isAntinode[fy1][fx1]) {
                isAntinode[fy1][fx1] = true
                ans++
            }

            val fy2 = 2 * y2 - y1
            val fx2 = 2 * x2 - x1

            if (fy2 in input.indices && fx2 in input[0].indices && !isAntinode[fy2][fx2]) {
                isAntinode[fy2][fx2] = true
                ans++
            }
        }

        return ans
    }

    override fun solvePart2(input: Array<CharArray>): Int {
        var ans = 0
        val isAntinode = Array(input.size) { BooleanArray(input[0].size) }

        forEachMatchingPair(input) { y1, x1, y2, x2 ->
            var fy1 = y1
            var fx1 = x1

            while (fy1 in input.indices && fx1 in input[0].indices) {
                if (!isAntinode[fy1][fx1]) {
                    isAntinode[fy1][fx1] = true
                    ans++
                }

                fy1 -= y2 - y1
                fx1 -= x2 - x1
            }

            var fy2 = y2
            var fx2 = x2

            while (fy2 in input.indices && fx2 in input[0].indices) {
                if (!isAntinode[fy2][fx2]) {
                    isAntinode[fy2][fx2] = true
                    ans++
                }

                fy2 -= y1 - y2
                fx2 -= x1 - x2
            }
        }

        return ans
    }
}
