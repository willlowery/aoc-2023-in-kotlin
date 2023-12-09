fun main() {

    fun toNumbers(input: List<String>): List<List<Int>> {
        return input.map {
            it.split(" ").map { i -> i.toInt() }
        }
    }

    fun diffs(line: List<List<Int>>): List<List<MutableList<Int>>> {
        fun diff(line: List<Int>): MutableList<Int> {
            return (1..<line.size).map {
                line[it] - line[it - 1]
            }.toMutableList()
        }

        return line.map {
            val steps = mutableListOf(it.toMutableList())
            while (steps.last().sum() != 0) {
                steps.add(diff(steps.last()))
            }
            steps
        }
    }

    fun part1(lines: List<String>): Int {
        return diffs(toNumbers(lines)).sumOf {
            val reversed = it.reversed()
            reversed.fold(0) { acc, line ->
                acc + line.last()
            }.toInt()
        }
    }

    fun part2(lines: List<String>): Int {
        return diffs(toNumbers(lines)).sumOf {
            val reversed = it.reversed()
            reversed.fold(0) { acc, line ->
                line.first() - acc

            }.toInt()
        }
    }

    val input = readInput("Day09")

    part1(input).println() // 1930746032
    part2(input).println() // 1154
}