package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem4 : Problem<Array<CharArray>, Int, Int>() {

    override fun parseInput(rawInput: String): Array<CharArray> {
        return rawInput.lines().map { it.toCharArray() }.toTypedArray()
    }

    override fun solvePart1(input: Array<CharArray>): Int {
        fun check(y: Int, x: Int, dy: Int, dx: Int): Boolean {
            return y + dy * 3 in input.indices &&
                x + dx * 3 in input[0].indices &&
                (0..3).all { input[y + it * dy][x + it * dx] == "XMAS"[it] }
        }

        var ans = 0

        for (y in input.indices) {
            for (x in input[0].indices) {
                ans += if (check(y, x, -1, 0)) 1 else 0
                ans += if (check(y, x, -1, 1)) 1 else 0
                ans += if (check(y, x, 0, 1)) 1 else 0
                ans += if (check(y, x, 1, 1)) 1 else 0
                ans += if (check(y, x, 1, 0)) 1 else 0
                ans += if (check(y, x, -1, -1)) 1 else 0
                ans += if (check(y, x, 0, -1)) 1 else 0
                ans += if (check(y, x, 1, -1)) 1 else 0
            }
        }

        return ans
    }

    override fun solvePart2(input: Array<CharArray>): Int {
        var ans = 0

        for (y in 1 until input.lastIndex) {
            for (x in 1 until input[0].lastIndex) {
                if (input[y][x] != 'A') continue
                val tl = input[y - 1][x - 1]
                val tr = input[y - 1][x + 1]
                val bl = input[y + 1][x - 1]
                val br = input[y + 1][x + 1]
                val d1 = tl == 'M' && br == 'S' || tl == 'S' && br == 'M'
                val d2 = tr == 'M' && bl == 'S' || tr == 'S' && bl == 'M'
                if (d1 && d2) ans++
            }
        }

        return ans
    }
}
