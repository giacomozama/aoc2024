package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem
import java.util.*

class Problem9 : Problem<IntArray, Long, Long>() {

    override fun parseInput(rawInput: String): IntArray {
        return IntArray(rawInput.length) { rawInput[it].digitToInt() }
    }

    override fun solvePart1(input: IntArray): Long {
        val fileSizeStack = LinkedList<Int>()
        var remainingFileBlocks = 0

        for (i in input.indices step 2) {
            fileSizeStack.push(input[i])
            remainingFileBlocks += input[i]
        }

        var blockIndex = 0
        var fromEndId = (input.size - 1) / 2
        var ans = 0L

        outer@ for (i in input.indices) {
            if (i and 1 == 0) {
                for (k in 1..input[i]) {
                    if (remainingFileBlocks-- <= 0) break@outer
                    ans += i / 2 * blockIndex++
                }
            } else {
                for (k in 1..input[i]) {
                    if (remainingFileBlocks-- <= 0) break@outer
                    ans += fromEndId * blockIndex++
                    val movingFileSize = fileSizeStack.pop()
                    if (movingFileSize == 1) {
                        fromEndId--
                    } else {
                        fileSizeStack.push(movingFileSize - 1)
                    }
                }
            }
        }

        return ans
    }

    override fun solvePart2(input: IntArray): Long {
        val fileBlocks = LinkedList<IntArray>()
        val freeBlocks = Array(10) { PriorityQueue<Int>() }

        var blockIndex = 0
        for (i in input.indices) {
            if (i and 1 == 0) {
                fileBlocks.push(intArrayOf(i / 2, blockIndex, input[i]))
            } else {
                freeBlocks[input[i]].offer(blockIndex)
            }
            blockIndex += input[i]
        }

        var ans = 0L

        while (fileBlocks.isNotEmpty()) {
            val cur = fileBlocks.pop()
            val (id, start, size) = cur

            var earliestFreeBlockIndex = start
            var earliestFreeBlockSize = Int.MAX_VALUE

            for (s in size..9) {
                val ind = freeBlocks[s].peek()
                if (ind != null && ind < earliestFreeBlockIndex) {
                    earliestFreeBlockIndex = ind
                    earliestFreeBlockSize = s
                }
            }

            if (earliestFreeBlockIndex != start) {
                freeBlocks[earliestFreeBlockSize].poll()
                if (earliestFreeBlockSize > size) {
                    freeBlocks[earliestFreeBlockSize - size].offer(earliestFreeBlockIndex + size)
                }
            }

            for (i in 0 until size) {
                ans += id * (earliestFreeBlockIndex + i)
            }
        }

        return ans
    }
}
