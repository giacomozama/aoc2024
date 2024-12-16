package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem
import java.util.*
import kotlin.collections.HashSet

class Problem16 : Problem<Problem16.Input, Int, Int>() {

    class Input(
        val isWall: Array<BooleanArray>,
        val startY: Int,
        val startX: Int,
        val endY: Int,
        val endX: Int,
    )

    override fun parseInput(rawInput: String): Input {
        val lines = rawInput.lines()

        val isWall = Array(lines.size) { BooleanArray(lines[0].length) }
        var startY = 0
        var startX = 0
        var endY = 0
        var endX = 0

        for ((y, line) in lines.withIndex()) {
            for ((x, c) in line.withIndex()) {
                when (c) {
                    '#' -> {
                        isWall[y][x] = true
                    }
                    'S' -> {
                        startY = y
                        startX = x
                    }
                    'E' -> {
                        endY = y
                        endX = x
                    }
                }
            }
        }

        return Input(isWall, startY, startX, endY, endX)
    }

    @JvmInline
    private value class Node private constructor(private val value: Int) {
        constructor(y: Int, x: Int, facing: Int) : this(y * 10_000 + x * 10 + facing)

        val y get() = value / 10_000
        val x get() = value % 10_000 / 10
        val facing get() = value % 10
    }

    private operator fun Array<Array<IntArray>>.get(node: Node) = this[node.y][node.x][node.facing]

    private operator fun Array<Array<IntArray>>.set(node: Node, value: Int) {
        this[node.y][node.x][node.facing] = value
    }

    private var result1 = -1
    private var result2 = -1

    // since the solution to part 1 can be easily found while solving part 2, it's more
    // efficient to just solve both parts at the same time
    private fun solveCombined(input: Input) {
        val costs = Array(input.isWall.size) { Array(input.isWall[0].size) { IntArray(4) { Int.MAX_VALUE } } }
        val closest = PriorityQueue<Node>(compareBy { costs[it.y][it.x][it.facing] })
        val visited = hashSetOf<Node>()
        val parents = hashMapOf<Node, HashSet<Node>>()

        fun update(node: Node, nb: Node, cost: Int) {
            if (input.isWall[nb.y][nb.x]) return

            val curCost = costs[node]
            val diff = costs[nb] - cost
            if (curCost > diff) return

            if (curCost == diff) {
                parents[nb]!!.add(node)
            } else {
                parents[nb] = hashSetOf(node)
                costs[nb] = curCost + cost
            }

            closest.offer(nb)
        }

        var cur: Node? = Node(input.startY, input.startX, 1)
        costs[cur!!] = 0

        while (cur != null) {
            if (!visited.add(cur)) {
                cur = closest.poll()
                continue
            }

            val y = cur.y
            val x = cur.x
            val facing = cur.facing
            val (dy, dx) = DIRS[facing]

            update(cur, Node(y + dy, x + dx, facing), 1)
            update(cur, Node(y, x, (facing + 1) % 4), 1000)
            update(cur, Node(y, x, (facing + 3) % 4), 1000)

            // might not work if the end tile is reachable from more than 1 direction
            if (y == input.endY && x == input.endX) break

            cur = closest.poll()
        }

        val uniqueTilesInAnyShortestPath = hashSetOf<Node>()
        val previousNodes = LinkedList<Node>()
        previousNodes.offer(Node(input.endY, input.endX, 1))

        cur = Node(input.endY, input.endX, 0)
        while (cur != null) {
            for (p in parents[cur].orEmpty()) {
                uniqueTilesInAnyShortestPath.add(Node(p.y, p.x, 0))
                previousNodes.offer(p)
            }
            cur = previousNodes.poll()
        }

        result1 = costs[input.endY][input.endX].min()
        result2 = uniqueTilesInAnyShortestPath.size
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
