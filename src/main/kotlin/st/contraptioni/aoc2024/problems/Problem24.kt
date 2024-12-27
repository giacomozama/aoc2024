package st.contraptioni.aoc2024.problems

import st.contraptioni.aoc2024.Problem

class Problem24 : Problem<Problem24.Input, Long, String>() {

    class Input(val wires: List<Pair<String, Int>>, val gates: List<Gate>)

    data class Gate(val src1: String, val type: Type, val src2: String, val output: String) {
        enum class Type(val op: (Int, Int) -> Int) {
            AND(Int::and), OR(Int::or), XOR(Int::xor)
        }
    }

    override fun parseInput(rawInput: String): Input {
        val lines = rawInput.lines()

        val wires = lines
            .take(90)
            .map { it.substringBefore(": ") to it.substringAfter(": ").toInt() }

        val gates = lines
            .drop(91)
            .map { it.split(" -> ", " ") }
            .map { (src1, type, src2, target) -> Gate(src1, Gate.Type.valueOf(type), src2, target) }

        return Input(wires, gates)
    }

    override fun solvePart1(input: Input): Long {
        val wires = hashMapOf<String, () -> Int>()

        for ((wire, initialValue) in input.wires) {
            wires[wire] = { initialValue }
        }

        for (gate in input.gates) {
            wires[gate.output] = { gate.type.op(wires[gate.src1]!!(), wires[gate.src2]!!()) }
        }

        var i = 0
        var ans = 0L

        while (true) {
            val cur = wires["z${i.toString().padStart(2, '0')}"] ?: break
            ans = ans or (cur().toLong() shl i++)
        }

        return ans
    }

    override fun solvePart2(input: Input): String {
        // the circuit is a 45 bit adder implemented using AND, OR and XOR gates
        //
        // we assume inputs are always correct.
        // we just need to find gates the output of which makes no sense,
        // which comes down to checking for the following:
        //
        // 1) all AND gate outputs should be inputs to an OR gate, except the one with inputs x00 and y00
        // 2) all XOR gates with x/y wires as inputs (except x00/y00) must output to either input of another XOR gate
        // 3) all other XOR gates must output to a z wire
        // 4) all OR gate outputs should be inputs to a XOR gate, except for z45

        val orGates = input.gates.filter { it.type == Gate.Type.OR }
        val andGates = input.gates.filter { it.type == Gate.Type.AND }
        val xorGates = input.gates.filter { it.type == Gate.Type.XOR }

        val orGateInputs = orGates.flatMap { listOf(it.src1, it.src2) }.toSet()
        val xorGateInputs = xorGates.flatMap { listOf(it.src1, it.src2) }.toSet()

        val badAndGates = andGates.filterNot { it.output in orGateInputs || it.src1 == "x00" || it.src1 == "y00" }

        val badXorGates = xorGates.filterNot {
            if (it.src1.startsWith("x") || it.src1.startsWith("y")) {
                it.src1.endsWith("00") || it.output in xorGateInputs
            } else {
                it.output.startsWith("z")
            }
        }

        val badOrGates = orGates.filterNot { it.output in xorGateInputs || it.output == "z45" }

        return (badAndGates + badXorGates + badOrGates).map { it.output }.sorted().joinToString(",")
    }
}
