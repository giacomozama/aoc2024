package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem25 : Problem<List<Array<BooleanArray>>, Int, Int>() {

    override fun parseInput(rawInput: String): List<Array<BooleanArray>> {
        return rawInput.lines().chunked(8) { l -> Array(7) { y -> BooleanArray(5) { l[y][it] == '#' } } }
    }

    override fun solvePart1(input: List<Array<BooleanArray>>): Int {
        val locks = mutableListOf<Array<BooleanArray>>()
        val keys = mutableListOf<IntArray>()

        for (spec in input) {
            if (spec[0][0]) {
                locks.add(spec)
                continue
            }

            val key = IntArray(5) {
                var height = 0
                for (y in 5 downTo 1) {
                    if (!spec[y][it]) break
                    height++
                }
                height
            }

            keys.add(key)
        }

        val lockHeight = IntArray(5)

        return locks.sumOf { lock ->
            for (x in 0..4) {
                lockHeight[x] = 0
                for (y in 1..5) {
                    if (!lock[y][x]) break
                    lockHeight[x]++
                }
            }

            keys.count { key -> (0..4).all { lockHeight[it] + key[it] < 6 } }
        }
    }

    override fun solvePart2(input: List<Array<BooleanArray>>) = 0
}
