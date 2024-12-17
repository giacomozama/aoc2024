package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem17 : Problem<IntArray, String, Long>() {

    override fun parseInput(rawInput: String): IntArray {
        val regex = """
            Register A: (\d+)
            Register B: (\d+)
            Register C: (\d+)
        """.trimIndent().toRegex()

        val registers = regex.find(rawInput)!!.groupValues.drop(1).map { it.toInt() }
        val program = rawInput.substringAfter("Program: ").split(',').map { it.toInt() }

        return (registers + program).toIntArray()
    }

    override fun solvePart1(input: IntArray): String {
        var (regA, regB, regC) = input
        var pc = 3
        val output = StringBuilder()

        fun comboOp() = when (val op = input[pc + 1]) {
            0, 1, 2, 3 -> op
            4 -> regA
            5 -> regB
            6 -> regC
            else -> error("Invalid op")
        }

        while (pc < input.size) {
            when (input[pc]) {
                0 -> {
                    regA = regA shr comboOp()
                }
                1 -> {
                    regB = regB xor input[pc + 1]
                }
                2 -> {
                    regB = comboOp() % 8
                }
                3 -> {
                    if (regA != 0) {
                        pc = input[pc + 1] + 1
                    }
                }
                4 -> {
                    regB = regB xor regC
                }
                5 -> {
                    output.append(comboOp() % 8).append(',')
                }
                6 -> {
                    regB = regA shr comboOp()
                }
                7 -> {
                    regC = regA shr comboOp()
                }
            }
            pc += 2
        }

        return output.substring(0, output.lastIndex)
    }

    override fun solvePart2(input: IntArray): Long {
        // we assume that all input programs are equivalent to
        //
        // do {
        //    out((A % 8 xor (A shr (A % 8 xor p)) xor p xor q) % 8)
        //    A = A shr 3
        // } while (A != 0)
        //
        // where p and q are the operands of the two bxl instructions we expect to be
        // in the input, in the order in which they appear.
        //
        // register A is shifted 3 bits to the right at every iteration, while registers B and C are
        // only used to store intermediate calculation results: output only depends on register A.
        //
        // this means we can determine the most significant bits of the initial value of A
        // by looking at the last digits in the expected output.

        var p = -1L
        var q = -1L

        for (i in 3 until input.size step 2) {
            if (input[i] == 1) {
                if (p == -1L) {
                    p = input[i + 1].toLong()
                } else {
                    q = input[i + 1].toLong()
                    break
                }
            }
        }

        val expected = LongArray(input.size - 3) { input[input.lastIndex - it].toLong() }

        fun dfs(regA: Long, e: Int): Long? {
            if (e == expected.size) return regA
            for (i in 0L..7L) {
                val r = regA shl 3 or i
                val t = (r % 8 xor (r shr (r % 8 xor p).toInt()) xor p xor q) % 8
                if (t == expected[e]) {
                    dfs(r, e + 1)?.let { return it }
                }
            }
            return null
        }

        return dfs(0, 0)!!
    }
}
