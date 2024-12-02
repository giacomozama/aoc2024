package st.contraptioni.aoc2024

import st.contraptioni.aoc2024.problems.*

abstract class Problem<INPUT, out RESULT_1 : Any, out RESULT_2 : Any> {

    abstract fun parseInput(rawInput: String): INPUT

    abstract fun solvePart1(input: INPUT): RESULT_1

    abstract fun solvePart2(input: INPUT): RESULT_2

    companion object {

        val INDEX = arrayOf(
            ::Problem1,
            ::Problem2,
            ::Problem3,
            ::Problem4,
            ::Problem5,
            ::Problem6,
            ::Problem7,
            ::Problem8,
            ::Problem9,
            ::Problem10,
            ::Problem11,
            ::Problem12,
            ::Problem13,
            ::Problem14,
            ::Problem15,
            ::Problem16,
            ::Problem17,
            ::Problem18,
            ::Problem19,
            ::Problem20,
            ::Problem21,
            ::Problem22,
            ::Problem23,
            ::Problem24,
            ::Problem25
        )
    }
}
