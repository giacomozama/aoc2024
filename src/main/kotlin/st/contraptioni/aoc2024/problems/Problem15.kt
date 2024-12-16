package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem15 : Problem<Problem15.Input, Int, Int>() {

    class Input(
        val board: Array<IntArray>,
        val movements: IntArray,
        val startY: Int,
        val startX: Int
    )

    override fun parseInput(rawInput: String): Input {
        val lines = rawInput.lines()
        val boardWidth = lines[0].length
        val boardHeight = lines.indexOf("")

        val board = Array(boardWidth) { IntArray(boardHeight) }
        var startX = 0
        var startY = 0

        for (y in 0 until boardHeight) {
            for (x in lines[y].indices) {
                board[y][x] = when (lines[y][x]) {
                    '.' -> TILE_EMPTY
                    'O' -> TILE_BOX
                    '#' -> TILE_WALL
                    else -> {
                        startX = x
                        startY = y
                        TILE_EMPTY
                    }
                }
            }
        }

        val movements = lines.drop(boardHeight + 1).flatMap { l ->
            l.map {
                when (it) {
                    '^' -> DIR_UP
                    '>' -> DIR_RIGHT
                    'v' -> DIR_DOWN
                    else -> DIR_LEFT
                }
            }
        }.toIntArray()

        return Input(board, movements, startY, startX)
    }

    override fun solvePart1(input: Input): Int {
        val board = Array(input.board.size) { input.board[it].copyOf() }

        var robotY = input.startY
        var robotX = input.startX

        for (movement in input.movements) {
            val (dy, dx) = DIRS[movement]

            var curY = robotY + dy
            var curX = robotX + dx
            while (board[curY][curX] == TILE_BOX) {
                curY += dy
                curX += dx
            }

            if (board[curY][curX] == TILE_WALL) continue

            board[curY][curX] = TILE_BOX

            robotY += dy
            robotX += dx
            board[robotY][robotX] = TILE_EMPTY
        }

        var ans = 0

        for (y in 1..board.size - 2) {
            for (x in 1..board[0].size - 2) {
                if (board[y][x] == TILE_BOX) {
                    ans += y * 100 + x
                }
            }
        }

        return ans
    }

    override fun solvePart2(input: Input): Int {
        val board = Array(input.board.size) { IntArray(input.board[0].size * 2) }

        for (y in input.board.indices) {
            for (x in input.board[0].indices) {
                when (input.board[y][x]) {
                    TILE_EMPTY -> {
                        board[y][x * 2] = TILE_EMPTY
                        board[y][x * 2 + 1] = TILE_EMPTY
                    }
                    TILE_WALL -> {
                        board[y][x * 2] = TILE_WALL
                        board[y][x * 2 + 1] = TILE_WALL
                    }
                    TILE_BOX -> {
                        board[y][x * 2] = TILE_BOX_L
                        board[y][x * 2 + 1] = TILE_BOX_R
                    }
                }
            }
        }

        fun canMove(y: Int, x: Int, dy: Int, dx: Int): Boolean = when {
            board[y + dy][x + dx] == TILE_WALL -> false
            board[y + dy][x + dx] == TILE_EMPTY -> true
            dy == 0 -> canMove(y, x + dx, 0, dx)
            board[y + dy][x] == TILE_BOX_L -> canMove(y + dy, x, dy, 0) && canMove(y + dy, x + 1, dy, 0)
            else -> canMove(y + dy, x - 1, dy, 0) && canMove(y + dy, x, dy, 0)
        }

        fun move(y: Int, x: Int, dy: Int, dx: Int) {
            when {
                board[y][x] == TILE_EMPTY -> {
                    // do nothing
                }
                dy == 0 -> {
                    move(y, x + dx, 0, dx)
                    board[y][x + dx] = board[y][x]
                    board[y][x] = TILE_EMPTY
                }
                board[y][x] == TILE_BOX_L -> {
                    move(y + dy, x + 1, dy, 0)
                    move(y + dy, x, dy, 0)
                    board[y + dy][x + 1] = TILE_BOX_R
                    board[y + dy][x] = TILE_BOX_L
                    board[y][x + 1] = TILE_EMPTY
                    board[y][x] = TILE_EMPTY
                }
                else -> {
                    move(y + dy, x - 1, dy, 0)
                    move(y + dy, x, dy, 0)
                    board[y + dy][x - 1] = TILE_BOX_L
                    board[y + dy][x] = TILE_BOX_R
                    board[y][x - 1] = TILE_EMPTY
                    board[y][x] = TILE_EMPTY
                }
            }
        }

        var robotY = input.startY
        var robotX = input.startX * 2

        for (movement in input.movements) {
            val (dy, dx) = DIRS[movement]

            if (canMove(robotY, robotX, dy, dx)) {
                move(robotY + dy, robotX + dx, dy, dx)
                robotY += dy
                robotX += dx
            }
        }

        var ans = 0

        for (y in 1..board.size - 2) {
            for (x in 1..board[0].size - 2) {
                if (board[y][x] == TILE_BOX_L) {
                    ans += y * 100 + x
                }
            }
        }

        return ans
    }

    companion object {
        private const val DIR_UP = 0
        private const val DIR_RIGHT = 1
        private const val DIR_DOWN = 2
        private const val DIR_LEFT = 3

        private const val TILE_EMPTY = 0
        private const val TILE_WALL = 1
        private const val TILE_BOX = 2
        private const val TILE_BOX_L = 2
        private const val TILE_BOX_R = 3

        private val DIRS = arrayOf(intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1))
    }
}
