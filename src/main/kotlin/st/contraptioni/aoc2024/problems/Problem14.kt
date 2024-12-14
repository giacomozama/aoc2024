package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem14 : Problem<List<IntArray>, Int, Int>() {

    override fun parseInput(rawInput: String): List<IntArray> {
        return """p=(\d+),(\d+) v=(-?\d+),(-?\d+)""".toRegex()
            .findAll(rawInput)
            .map { res -> IntArray(4) { res.groupValues[it + 1].toInt() } }
            .toList()
    }

    private fun euclideanMod(a: Int, b: Int) = if (a < 0) (a % b + b) % b else a % b

    override fun solvePart1(input: List<IntArray>): Int {
        var tl = 0
        var tr = 0
        var br = 0
        var bl = 0

        for ((sx, sy, dx, dy) in input) {
            val fx = euclideanMod(sx + dx * 100, WIDTH)
            val fy = euclideanMod(sy + dy * 100, HEIGHT)
            when {
                fx < WIDTH / 2 && fy < HEIGHT / 2 -> tl++
                fx > WIDTH / 2 && fy < HEIGHT / 2 -> tr++
                fx > WIDTH / 2 && fy > HEIGHT / 2 -> br++
                fx < WIDTH / 2 && fy > HEIGHT / 2 -> bl++
            }
        }

        return tl * tr * br * bl
    }

    override fun solvePart2(input: List<IntArray>): Int {
        // the assumption here is that a state where robots are forming the picture of a tree
        // would require a large number of robots to be orthogonally adjacent, an otherwise
        // very unlikely condition

        val robots = Array(input.size) { intArrayOf(input[it][0], input[it][1]) }
        val visited = Array(HEIGHT) { IntArray(WIDTH) }
        val board = Array(HEIGHT) { IntArray(WIDTH) }

        var t = 0

        fun dfs(x: Int, y: Int): Int {
            if (y !in board.indices || x !in board[0].indices || visited[y][x] == t || board[y][x] == 0) return 0
            visited[y][x] = t
            return 1 + dfs(x, y - 1) + dfs(x + 1, y) + dfs(x, y + 1) + dfs(x - 1, y)
        }

        for ((x, y) in robots) board[y][x]++

        // our input works with values as low as 13
        val threshold = 40

        while (robots.none { (y, x) -> dfs(x, y) > threshold }) {
            t++
            for ((i, robot) in robots.withIndex()) {
                board[robot[1]][robot[0]]--
                robot[0] = euclideanMod(robot[0] + input[i][2], WIDTH)
                robot[1] = euclideanMod(robot[1] + input[i][3], HEIGHT)
                board[robot[1]][robot[0]]++
            }
        }

        return t
    }

    companion object {
        private const val WIDTH = 101
        private const val HEIGHT = 103
    }
}
