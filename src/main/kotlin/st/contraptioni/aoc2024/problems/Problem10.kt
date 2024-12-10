package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem10 : Problem<Array<IntArray>, Int, Int>() {

    override fun parseInput(rawInput: String): Array<IntArray> {
        val lines = rawInput.lines()
        return Array(lines.size) { y -> IntArray(lines[y].length) { lines[y][it].digitToInt() } }
    }

    override fun solvePart1(input: Array<IntArray>): Int {
        val visited = Array(input.size) { IntArray(input[0].size) }
        var trail = 1
        var ans = 0

        fun dfs(y: Int, x: Int) {
            if (visited[y][x] == trail) return
            visited[y][x] = trail
            val value = input[y][x]
            if (value == 9) {
                ans++
                return
            }
            if (y > 0 && input[y - 1][x] == value + 1) dfs(y - 1, x)
            if (x > 0 && input[y][x - 1] == value + 1) dfs(y, x - 1)
            if (y < input.lastIndex && input[y + 1][x] == value + 1) dfs(y + 1, x)
            if (x < input[0].lastIndex && input[y][x + 1] == value + 1) dfs(y, x + 1)
        }

        for (y in input.indices) {
            for (x in input[0].indices) {
                if (input[y][x] == 0) {
                    dfs(y, x)
                    trail++
                }
            }
        }

        return ans
    }

    override fun solvePart2(input: Array<IntArray>): Int {
        var ans = 0

        fun dfs(y: Int, x: Int) {
            val value = input[y][x]
            if (value == 9) {
                ans++
                return
            }
            if (y > 0 && input[y - 1][x] == value + 1) dfs(y - 1, x)
            if (x > 0 && input[y][x - 1] == value + 1) dfs(y, x - 1)
            if (y < input.lastIndex && input[y + 1][x] == value + 1) dfs(y + 1, x)
            if (x < input[0].lastIndex && input[y][x + 1] == value + 1) dfs(y, x + 1)
        }

        for (y in input.indices) {
            for (x in input[0].indices) {
                if (input[y][x] == 0) {
                    dfs(y, x)
                }
            }
        }

        return ans
    }
}
