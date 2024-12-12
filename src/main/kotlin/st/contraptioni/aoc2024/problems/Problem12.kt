package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem12 : Problem<Array<CharArray>, Int, Int>() {

    override fun parseInput(rawInput: String): Array<CharArray> {
        return rawInput.lines().map { it.toCharArray() }.toTypedArray()
    }

    override fun solvePart1(input: Array<CharArray>): Int {
        val visited = Array(input.size) { BooleanArray(input[0].size) }

        var region = '0'
        var area = 0
        var perimeter = 0

        fun dfs(y: Int, x: Int): Int {
            if (y !in input.indices || x !in input[0].indices || input[y][x] != region) return 1

            if (visited[y][x]) return 0
            visited[y][x] = true

            area++
            val dp = dfs(y - 1, x) + dfs(y, x - 1) + dfs(y + 1, x) + dfs(y, x + 1)
            perimeter += dp

            return 0
        }

        var ans = 0

        for (y in input.indices) {
            for (x in input[0].indices) {
                area = 0
                perimeter = 0
                region = input[y][x]
                dfs(y, x)
                ans += area * perimeter
            }
        }

        return ans
    }

    override fun solvePart2(input: Array<CharArray>): Int {
        val distinct = Array(input.size) { IntArray(input[0].size) }
        val areas = mutableListOf(0)

        var region = '0'
        var area = 0

        fun dfs(y: Int, x: Int) {
            if (y !in input.indices || x !in input[0].indices || input[y][x] != region || distinct[y][x] != 0) return
            distinct[y][x] = areas.size
            area++
            dfs(y - 1, x)
            dfs(y, x - 1)
            dfs(y + 1, x)
            dfs(y, x + 1)
        }

        for (y in input.indices) {
            for (x in input[0].indices) {
                region = input[y][x]
                area = 0
                dfs(y, x)
                if (area > 0) areas.add(area)
            }
        }

        val sides = IntArray(areas.size)

        for (y in 0..input.size) {
            for (x in 0..input[0].size) {
                val tl = distinct.getOrNull(y - 1)?.getOrNull(x - 1) ?: 0
                val tr = distinct.getOrNull(y - 1)?.getOrNull(x) ?: 0
                val br = distinct.getOrNull(y)?.getOrNull(x) ?: 0
                val bl = distinct.getOrNull(y)?.getOrNull(x - 1) ?: 0

                when {
                    tl == tr && bl == br || tl == bl && tr == br -> {
                        // not a vertex
                    }
                    tl == tr -> {
                        sides[bl]++
                        sides[br]++
                    }
                    bl == br -> {
                        sides[tl]++
                        sides[tr]++
                    }
                    tl == bl -> {
                        sides[tr]++
                        sides[br]++
                    }
                    tr == br -> {
                        sides[tl]++
                        sides[bl]++
                    }
                    else -> {
                        sides[tl]++
                        sides[tr]++
                        sides[br]++
                        sides[bl]++
                    }
                }
            }
        }

        return (1 until areas.size).sumOf { areas[it] * sides[it] }
    }
}
