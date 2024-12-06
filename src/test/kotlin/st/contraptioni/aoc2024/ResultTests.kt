package st.contraptioni.aoc2024

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class ResultTests {

    private fun <IN : Any, R1 : Any, R2 : Any> doTestProblem(
        id: Int,
        problem: Problem<IN, R1, R2>,
        part1Result: R1,
        part2Result: R2
    ) {
        val rawInput = File("input/$id.txt").readText().trimEnd('\n')
        val parsedInput = problem.parseInput(rawInput)
        assertEquals(part1Result, problem.solvePart1(parsedInput))
        assertEquals(part2Result, problem.solvePart2(parsedInput))
    }

    private fun testProblem(id: Int, part1Result: Any, part2Result: Any) {
        val problem = Problem.INDEX[id - 1]()
        doTestProblem(id, problem, part1Result, part2Result)
    }

    @Test
    fun testProblem1() {
        testProblem(1, 1258579, 23981443)
    }

    @Test
    fun testProblem2() {
        testProblem(2, 526, 566)
    }

    @Test
    fun testProblem3() {
        testProblem(3, 196826776, 106780429)
    }

    @Test
    fun testProblem4() {
        testProblem(4, 2427, 1900)
    }

    @Test
    fun testProblem5() {
        testProblem(5, 6949, 4145)
    }

    @Test
    fun testProblem6() {
        testProblem(6, 5531, 2165)
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

    @Test
    fun testProblem25() {
        testProblem(25, NotImplemented, NotImplemented)
    }
}
