package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem
import kotlin.collections.HashSet


class Problem23 : Problem<List<Array<String>>, Int, String>() {

    override fun parseInput(rawInput: String): List<Array<String>> {
        return rawInput.lines().map { arrayOf(it.take(2), it.takeLast(2)) }
    }

    private fun nodeIdOf(node: String) = (node[0] - 'a') * 26 + (node[1] - 'a')

    override fun solvePart1(input: List<Array<String>>): Int {
        val isConnected = Array(676) { BooleanArray(676) }

        for ((a, b) in input) {
            val idA = nodeIdOf(a)
            val idB = nodeIdOf(b)
            isConnected[idA][idB] = true
            isConnected[idB][idA] = true
        }

        var ans = 0
        val startsWithT = nodeIdOf("ta") until nodeIdOf("ua")

        for (a in 0..673) {
            for (b in a + 1..674) {
                for (c in b + 1..675) {
                    val isGroupOf3 = isConnected[a][b] && isConnected[b][c] && isConnected[c][a]
                    if (isGroupOf3 && (a in startsWithT || b in startsWithT || c in startsWithT)) {
                        ans++
                    }
                }
            }
        }

        return ans
    }

    override fun solvePart2(input: List<Array<String>>): String {
        val graph = hashMapOf<Int, HashSet<Int>>()

        for ((a, b) in input) {
            val aId = nodeIdOf(a)
            val bId = nodeIdOf(b)
            graph.getOrPut(aId) { hashSetOf() }.add(bId)
            graph.getOrPut(bId) { hashSetOf() }.add(aId)
        }

        var largestClique = setOf<Int>()

        fun bronKerbosch(clique: Set<Int>, candidates: Set<Int>, visited: Set<Int>) {
            if (candidates.isEmpty() && visited.isEmpty() && clique.size > largestClique.size) {
                largestClique = clique
                return
            }

            val pivotNbs = candidates.firstOrNull()?.let { graph[it]!! }
            var curCandidates = candidates
            var curVisited = visited

            while (curCandidates.isNotEmpty()) {
                val candidate = curCandidates.first()

                if (candidate !in pivotNbs!!) {
                    val nbs = graph[candidate]!!
                    bronKerbosch(clique + candidate, curCandidates.intersect(nbs), curVisited.intersect(nbs))
                }

                curCandidates = curCandidates - candidate
                curVisited = curVisited - candidate
            }
        }

        bronKerbosch(setOf(), graph.keys, setOf())

        return largestClique.sorted().joinToString(",") { "${'a' + it / 26}${'a' + it % 26}" }
    }
}
