package st.contraptioni.aoc2024

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class ResultTests {

    private fun testProblem(problemNo: Int, part1Result: Any, part2Result: Any) {
        val rawInput = File("input/$problemNo.txt").readText().trimEnd('\n')
        Problem.INDEX[problemNo - 1]().solve(rawInput) { result1, result2 ->
            assertEquals(part1Result, result1)
            assertEquals(part2Result, result2)
        }
    }

    @Test
    fun testProblem1() {
        testProblem(1, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem2() {
        testProblem(2, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem3() {
        testProblem(3, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem4() {
        testProblem(4, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem5() {
        testProblem(5, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem6() {
        testProblem(6, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem7() {
        testProblem(7, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem8() {
        testProblem(8, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem9() {
        testProblem(9, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem10() {
        testProblem(10, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem11() {
        testProblem(11, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem12() {
        testProblem(12, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem13() {
        testProblem(13, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem14() {
        testProblem(14, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem15() {
        testProblem(15, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem16() {
        testProblem(16, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem17() {
        testProblem(17, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem18() {
        testProblem(18, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem19() {
        testProblem(19, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem20() {
        testProblem(20, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem21() {
        testProblem(21, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem22() {
        testProblem(22, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem23() {
        testProblem(23, NotImplemented, NotImplemented)
    }

    @Test
    fun testProblem24() {
        testProblem(24, NotImplemented, NotImplemented)
    }
}
