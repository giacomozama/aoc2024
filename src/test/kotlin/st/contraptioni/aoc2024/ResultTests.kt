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
        testProblem(7, 3119088655389, 264184041398847)
    }

    @Test
    fun testProblem8() {
        testProblem(8, 303, 1045)
    }

    @Test
    fun testProblem9() {
        testProblem(9, 6301895872542, 6323761685944)
    }

    @Test
    fun testProblem10() {
        testProblem(10, 841, 1875)
    }

    @Test
    fun testProblem11() {
        testProblem(11, 217443L, 257246536026785L)
    }

    @Test
    fun testProblem12() {
        testProblem(12, 1319878, 784982)
    }

    @Test
    fun testProblem13() {
        testProblem(13, 31589L, 98080815200063L)
    }

    @Test
    fun testProblem14() {
        testProblem(14, 218619120, 7055)
    }

    @Test
    fun testProblem15() {
        testProblem(15, 1515788, 1516544)
    }

    @Test
    fun testProblem16() {
        testProblem(16, 107512, 561)
    }

    @Test
    fun testProblem17() {
        testProblem(17, "3,1,4,3,1,7,1,6,3", 37221270076916L)
    }

    @Test
    fun testProblem18() {
        testProblem(18, 226, "60,46")
    }

    @Test
    fun testProblem19() {
        testProblem(19, 293, 623924810770264L)
    }

    @Test
    fun testProblem20() {
        testProblem(20, 1360, 1005476)
    }

    @Test
    fun testProblem21() {
        testProblem(21, 217662, 263617786809000L)
    }

    @Test
    fun testProblem22() {
        testProblem(22, 20411980517L, 2362)
    }

    @Test
    fun testProblem23() {
        testProblem(23, 1485, "cc,dz,ea,hj,if,it,kf,qo,sk,ug,ut,uv,wh")
    }

    @Test
    fun testProblem24() {
        testProblem(24, 53258032898766L, "gbs,hwq,thm,wrm,wss,z08,z22,z29")
    }

    @Test
    fun testProblem25() {
        testProblem(25, 3057, 0)
    }
}
