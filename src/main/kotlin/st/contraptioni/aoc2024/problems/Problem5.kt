package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem5 : Problem<Problem5.Input, Int, Int>() {

    class Input(
        val ordering: Set<List<Int>>,
        val updates: List<List<Int>>
    )

    override fun parseInput(rawInput: String): Input {
        val (rawOrdering, rawUpdates) = rawInput.split("\n\n")
        val ordering = rawOrdering.lines().map { l -> l.split("|").map { it.toInt() } }.toSet()
        val updates = rawUpdates.lines().map { l -> l.split(",").map { it.toInt() } }
        return Input(ordering, updates)
    }

    override fun solvePart1(input: Input): Int {
        var ans = 0

        updates@ for (update in input.updates) {
            for (i in 1 until update.size) {
                for (j in 0 until i) {
                    if (listOf(update[i], update[j]) in input.ordering) continue@updates
                }
            }

            ans += update[update.size / 2]
        }

        return ans
    }

    override fun solvePart2(input: Input): Int {
        var ans = 0

        for (update in input.updates) {
            var isSorted = true

            check@ for (i in 1 until update.size) {
                for (j in 0 until i) {
                    if (listOf(update[i], update[j]) in input.ordering) {
                        isSorted = false
                        break@check
                    }
                }
            }

            if (isSorted) continue

            val sorted = update.toIntArray()

            sorting@ while (!isSorted) {
                for (i in 1 until update.size) {
                    for (j in 0 until i) {
                        if (listOf(sorted[i], sorted[j]) in input.ordering) {
                            val t = sorted[i]
                            sorted[i] = sorted[j]
                            sorted[j] = t
                            continue@sorting
                        }
                    }
                }
                isSorted = true
            }

            ans += sorted[sorted.size / 2]
        }

        return ans
    }
}
