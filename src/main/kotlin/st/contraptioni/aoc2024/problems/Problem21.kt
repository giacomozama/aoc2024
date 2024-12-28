package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem21 : Problem<List<String>, Int, Long>() {

    override fun parseInput(rawInput: String): List<String> {
        return rawInput.lines()
    }

    private var result1 = -1
    private var result2 = -1L

    private fun solveCombined(input: List<String>) {

        fun generatePaths(keys: String, coords: Map<Char, IntArray>, deadX: Int, deadY: Int): Map<String, String> {
            val paths = hashMapOf<String, String>()

            for (start in keys) {
                val (sy, sx) = coords[start]!!

                for (end in keys) {
                    val (ey, ex) = coords[end]!!

                    val dy = ey - sy
                    val v = if (dy < 0) "^".repeat(-dy) else "v".repeat(dy)

                    val dx = ex - sx
                    val h = if (dx < 0) "<".repeat(-dx) else ">".repeat(dx)

                    val possiblePaths = setOf(v + h + "A", h + v + "A").filter { path ->
                        var (y, x) = coords[start]!!
                        path.dropLast(1).none {
                            val (ddy, ddx) = DPAD_DIRS[it]!!
                            y += ddy
                            x += ddx
                            x == deadX && y == deadY
                        }
                    }

                    paths["$start$end"] = if (possiblePaths.size == 1) {
                        possiblePaths[0]
                    } else {
                        val a = KEY_PRIORITY.indexOf(possiblePaths[0][0])
                        val b = KEY_PRIORITY.indexOf(possiblePaths[1][0])
                        if (a < b) possiblePaths[0] else possiblePaths[1]
                    }
                }
            }

            return paths
        }

        val dPadPaths = generatePaths(DPAD_KEYS, DPAD_COORDS, 0, 0)
        val numPadPaths = generatePaths(NUMPAD_KEYS, NUMPAD_COORDS, 0, 3)

        val memo = hashMapOf<String, Long>()

        fun findPath(dPads: Int, path: String): Long = memo.getOrPut("$dPads-$path") {
            if (dPads == 0) return@getOrPut path.length.toLong()

            var len = 0L
            var prev = 'A'

            for (d in path) {
                len += findPath(dPads - 1, dPadPaths["$prev$d"]!!)
                prev = d
            }

            len
        }

        fun solveFor(dPads: Int): Long {
            var ans = 0L

            for (code in input) {
                var len = 0L
                var prevNum = 'A'

                for (num in code) {
                    var prev = 'A'

                    for (d in numPadPaths["$prevNum$num"]!!) {
                        len += findPath(dPads, dPadPaths["$prev$d"]!!)

                        prev = d
                    }

                    prevNum = num
                }

                ans += len * code.dropLast(1).toLong()
            }

            return ans
        }

        result1 = solveFor(1).toInt()
        result2 = solveFor(24)
    }

    override fun solvePart1(input: List<String>): Int {
        if (result1 == -1) solveCombined(input)
        return result1
    }

    override fun solvePart2(input: List<String>): Long {
        if (result2 == -1L) solveCombined(input)
        return result2
    }

    companion object {
        private const val KEY_PRIORITY = "<v^>A"
        private const val DPAD_KEYS = "^>v<A"
        private const val NUMPAD_KEYS = "0123456789A"

        private val DPAD_COORDS = mapOf(
            '^' to intArrayOf(0, 1),
            '>' to intArrayOf(1, 2),
            'v' to intArrayOf(1, 1),
            '<' to intArrayOf(1, 0),
            'A' to intArrayOf(0, 2)
        )

        private val DPAD_DIRS = mapOf(
            '^' to intArrayOf(-1, 0),
            '>' to intArrayOf(0, 1),
            'v' to intArrayOf(1, 0),
            '<' to intArrayOf(0, -1),
        )

        private val NUMPAD_COORDS = mapOf(
            '0' to intArrayOf(3, 1),
            '1' to intArrayOf(2, 0),
            '2' to intArrayOf(2, 1),
            '3' to intArrayOf(2, 2),
            '4' to intArrayOf(1, 0),
            '5' to intArrayOf(1, 1),
            '6' to intArrayOf(1, 2),
            '7' to intArrayOf(0, 0),
            '8' to intArrayOf(0, 1),
            '9' to intArrayOf(0, 2),
            'A' to intArrayOf(3, 2)
        )
    }
}
