package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem6 : Problem<Problem6.Input, Int, Int>() {

    class Input(
        val grid: Array<BooleanArray>,
        val startY: Int,
        val startX: Int
    )

    override fun parseInput(rawInput: String): Input {
        var startY = -1
        var startX = -1
        val grid = Array(130) { BooleanArray(130) }

        for ((y, line) in rawInput.lines().withIndex()) {
            for ((x, c) in line.withIndex()) {
                if (c == '#') {
                    grid[y][x] = true
                } else if (c == '^') {
                    startY = y
                    startX = x
                }
            }
        }

        return Input(grid, startY, startX)
    }

    override fun solvePart1(input: Input): Int {
        var facing = 0
        val visited = Array(130) { BooleanArray(130) }
        var ans = 0
        var y = input.startY
        var x = input.startX

        while (true) {
            if (!visited[y][x]) {
                visited[y][x] = true
                ans++
            }

            val (dy, dx) = DIRS[facing]
            val fy = y + dy
            val fx = x + dx

            if (fy !in 0..129 || fx !in 0..129) break

            if (input.grid[fy][fx]) {
                facing = (facing + 1) % 4
            } else {
                y = fy
                x = fx
            }
        }

        return ans
    }

    override fun solvePart2(input: Input): Int {
        val visited = Array(4) { Array(130) { IntArray(130) } }

        var k = 0
        fun checkLoop(startY: Int, startX: Int, startDir: Int): Boolean {
            k++
            var facing = startDir
            var y = startY
            var x = startX

            while (true) {
                if (visited[facing][y][x] >= k) return true
                visited[facing][y][x] = k

                val (dy, dx) = DIRS[facing]
                val fy = y + dy
                val fx = x + dx
                if (fy !in 0..129 || fx !in 0..129) return false
                if (input.grid[fy][fx]) {
                    facing = (facing + 1) % 4
                } else {
                    y = fy
                    x = fx
                }
            }
        }

        var facing = 0
        var y = input.startY
        var x = input.startX
        var ans = 0

        val available = Array(130) { BooleanArray(130) { true } }
        available[input.startY][input.startX] = false

        while (true) {
            visited[facing][y][x] = Int.MAX_VALUE

            val (dy, dx) = DIRS[facing]
            val fy = y + dy
            val fx = x + dx

            if (fy !in 0..129 || fx !in 0..129) break

            if (input.grid[fy][fx]) {
                facing = (facing + 1) % 4
            } else {
                if (available[fy][fx]) {
                    available[fy][fx] = false
                    input.grid[fy][fx] = true
                    if (checkLoop(y, x, (facing + 1) % 4)) ans++
                    input.grid[fy][fx] = false
                }
                y = fy
                x = fx
            }
        }

        return ans
    }

    companion object {

        private val DIRS = arrayOf(intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1))
    }
}
