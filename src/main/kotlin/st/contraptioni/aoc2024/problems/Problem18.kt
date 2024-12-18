package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem
import java.util.*

class Problem18 : Problem<List<Problem18.Coord>, Int, String>() {

    @JvmInline
    value class Coord private constructor(private val value: Int) {
        constructor(x: Int, y: Int) : this(x * 100 + y)

        val x get() = value / 100
        val y get() = value % 100
    }

    override fun parseInput(rawInput: String): List<Coord> {
        return rawInput.lines().map {
            Coord(it.substringBefore(",").toInt(), it.substringAfter(",").toInt())
        }
    }

    override fun solvePart1(input: List<Coord>): Int {
        val start = Coord(0, 0)
        val end = Coord(70, 70)

        val isVisited = Array(SIZE) { BooleanArray(SIZE) }
        isVisited[start.x][start.y] = true
        for (i in 0..1023) {
            val coord = input[i]
            isVisited[coord.x][coord.y] = true
        }

        val queue = LinkedList<Coord>()
        queue.offer(start)

        var dist = 0
        while (queue.isNotEmpty()) {
            repeat(queue.size) {
                val cur = queue.poll()
                if (cur == end) return dist

                val x = cur.x
                val y = cur.y

                if (y > 0 && !isVisited[x][y - 1]) {
                    val nb = Coord(x, y - 1)
                    isVisited[nb.x][nb.y] = true
                    queue.offer(nb)
                }

                if (x > 0 && !isVisited[x - 1][y]) {
                    val nb = Coord(x - 1, y)
                    isVisited[nb.x][nb.y] = true
                    queue.offer(nb)
                }

                if (y < SIZE - 1 && !isVisited[x][y + 1]) {
                    val nb = Coord(x, y + 1)
                    isVisited[nb.x][nb.y] = true
                    queue.offer(nb)
                }

                if (x < SIZE - 1 && !isVisited[x + 1][y]) {
                    val nb = Coord(x + 1, y)
                    isVisited[nb.x][nb.y] = true
                    queue.offer(nb)
                }
            }

            dist++
        }

        return -1
    }

    override fun solvePart2(input: List<Coord>): String {
        val lastVisited = Array(SIZE) { IntArray(SIZE) }
        var k = 0

        fun canExit(x: Int, y: Int): Boolean {
            if (x !in 0 until SIZE || y !in 0 until SIZE || lastVisited[x][y] == k) return false
            if (x == 70 && y == 70) return true
            lastVisited[x][y] = k
            return canExit(x + 1, y) || canExit(x, y + 1) || canExit(x - 1, y) || canExit(x, y - 1)
        }

        var l = 1025
        var r = input.size

        while (l < r) {
            k++
            val m = l + (r - l) / 2

            for (i in 0 until m) {
                val coord = input[i]
                lastVisited[coord.x][coord.y] = k
            }

            if (canExit(0, 0)) {
                l = m + 1
            } else {
                r = m
            }
        }

        val ans = input[l - 1]
        return "${ans.x},${ans.y}"
    }

    companion object {
        private const val SIZE = 71
    }
}
