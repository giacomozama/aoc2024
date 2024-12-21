package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem
import java.util.*

class Problem20 : Problem<Problem20.Input, Int, Int>() {

    class Input(
        val isWall: Array<BooleanArray>,
        val start: Coord
    )

    @JvmInline
    value class Coord private constructor(private val value: Int) {
        constructor(x: Int, y: Int) : this(x * 1000 + y)

        val x get() = value / 1000
        val y get() = value % 1000
    }

    override fun parseInput(rawInput: String): Input {
        val lines = rawInput.lines()

        val isWall = Array(lines.size) { BooleanArray(lines[0].length) }
        var startX = 0
        var startY = 0

        for ((y, line) in lines.withIndex()) {
            for ((x, c) in line.withIndex()) {
                when (c) {
                    '#' -> {
                        isWall[y][x] = true
                    }
                    'S' -> {
                        startX = x
                        startY = y
                    }
                }
            }
        }

        return Input(isWall, Coord(startX, startY))
    }

    private var result1 = -1
    private var result2 = -1

    private fun solveCombined(input: Input) {
        val path = mutableListOf<Coord>()

        val visited = hashSetOf(input.start)
        val queue = LinkedList<Coord>()
        queue.offer(input.start)

        outer@ while (queue.isNotEmpty()) {
            val size = queue.size
            for (i in 1..size) {
                val cur = queue.poll()
                path.add(cur)
                for ((dy, dx) in DIRS) {
                    val nb = Coord(cur.x + dx, cur.y + dy)
                    if (nb.y in input.isWall.indices && nb.x in input.isWall[0].indices && visited.add(nb)) {
                        if (!input.isWall[nb.y][nb.x]) {
                            queue.offer(nb)
                        }
                    }
                }
            }
        }


        val pathCoordIndex = path.indices.associateBy { path[it] }

        result1 = 0

        for (cur in path) {
            val curIndex = pathCoordIndex[cur]!!
            for ((dy, dx) in DIRS) {
                val nb = Coord(cur.x + 2 * dx, cur.y + 2 * dy)
                pathCoordIndex[nb]?.let { if (it - curIndex > 100) result1++ }
            }
        }

        result2 = result1

        for (cur in path) {
            val curIndex = pathCoordIndex[cur]!!
            for (d in 3..20) {
                for (a in 0..d) {
                    val coords = setOf(
                        Coord(cur.x + a, cur.y + d - a),
                        Coord(cur.x - a, cur.y + d - a),
                        Coord(cur.x + a, cur.y - d + a),
                        Coord(cur.x - a, cur.y - d + a)
                    )
                    for (coord in coords) {
                        pathCoordIndex[coord]?.let { if (it - curIndex > 98 + d) result2++ }
                    }
                }
            }
        }
    }

    override fun solvePart1(input: Input): Int {
        if (result1 == -1) solveCombined(input)
        return result1
    }

    override fun solvePart2(input: Input): Int {
        if (result2 == -1) solveCombined(input)
        return result2
    }

    companion object {
        private val DIRS = arrayOf(intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1))
    }
}
