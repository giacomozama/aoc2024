package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem22 : Problem<IntArray, Long, Int>() {

    override fun parseInput(rawInput: String): IntArray {
        return rawInput.lines().map { it.toInt() }.toIntArray()
    }

    private fun calcNext(previous: Long): Long {
        var ans = (previous xor previous * 64L) % 16_777_216L
        ans = ans xor ans / 32L
        return (ans xor ans * 2048L) % 16_777_216L
    }

    private var result1 = -1L
    private var result2 = -1

    private fun solveCombined(input: IntArray) {
        result1 = 0

        val bananas = Array(19) { Array(19) { Array(19) { IntArray(19) } } }
        val visited = Array(19) { Array(19) { Array(19) { IntArray(19) } } }
        var k = 0

        for (n in input) {
            k++

            var a: Int

            var prev = n % 10
            var cur = calcNext(n.toLong())
            var b = (cur % 10 - prev).toInt()

            prev = (cur % 10).toInt()
            cur = calcNext(cur)
            var c = (cur % 10 - prev).toInt()

            prev = (cur % 10).toInt()
            cur = calcNext(cur)
            var d = (cur % 10 - prev).toInt()

            for (i in 4..2000) {
                prev = (cur % 10).toInt()
                cur = calcNext(cur)

                a = b
                b = c
                c = d
                d = (cur % 10 - prev).toInt()

                if (visited[a + 9][b + 9][c + 9][d + 9] == k) continue
                visited[a + 9][b + 9][c + 9][d + 9] = k
                bananas[a + 9][b + 9][c + 9][d + 9] += (cur % 10).toInt()
            }

            result1 += cur
        }

        var ans = 0

        for (a in 0..18) {
            for (b in 0..18) {
                for (c in 0..18) {
                    for (d in 0..18) {
                        ans = maxOf(ans, bananas[a][b][c][d])
                    }
                }
            }
        }

        result2 = ans
    }

    override fun solvePart1(input: IntArray): Long {
        if (result1 == -1L) solveCombined(input)
        return result1
    }

    override fun solvePart2(input: IntArray): Int {
        if (result2 == -1) solveCombined(input)
        return result2
    }
}
