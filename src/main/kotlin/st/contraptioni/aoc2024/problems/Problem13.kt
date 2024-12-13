package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem13 : Problem<List<IntArray>, Long, Long>() {

    override fun parseInput(rawInput: String): List<IntArray> {
        val regex = """
            Button A: X\+(\d+), Y\+(\d+)
            Button B: X\+(\d+), Y\+(\d+)
            Prize: X=(\d+), Y=(\d+)
        """.trimIndent().toRegex()

        return regex.findAll(rawInput).map { res -> IntArray(6) { res.groupValues[it + 1].toInt() } }.toList()
    }

    private fun solveFor(machine: IntArray, prizeOffset: Long): Long {
        val (aX, aY, bX, bY) = machine
        val prizeX = machine[4] + prizeOffset
        val prizeY = machine[5] + prizeOffset

        // we need to solve the linear system
        //
        // aY * A + bY * B = prizeY
        // aX * A + bX * B = prizeX
        //
        // for A and B, only accepting integer solutions

        val d = aY * bX - aX * bY

        val dA = prizeY * bX - prizeX * bY
        if (dA % d != 0L) return 0L

        val dB = aY * prizeX - aX * prizeY
        if (dB % d != 0L) return 0L

        return dA / d * 3 + dB / d
    }

    override fun solvePart1(input: List<IntArray>): Long {
        return input.sumOf { solveFor(it, 0) }
    }

    override fun solvePart2(input: List<IntArray>): Long {
        return input.sumOf { solveFor(it, 10_000_000_000_000) }
    }
}
