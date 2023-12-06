import java.util.*
import kotlin.math.pow

fun main() {
    fun createCard(line: String): Card {
        val idSplitOut = line.split("\\s*:\\s*".toRegex())
        val id = idSplitOut[0].split("\\s+".toRegex())[1].toInt()

        val numbersSplitOut = idSplitOut[1]
                .split("|")

        val magicNumbers = numbersSplitOut[0]
                .split(" ")
                .filter { it.isNotEmpty() }
                .map { it.toInt() }

        val numbers = numbersSplitOut[1]
                .split(" ")
                .filter { it.isNotEmpty() }
                .map { it.toInt() }

        return Card(id, magicNumbers, numbers)
    }

    fun part1(lines: List<String>): Int {
        return lines.map {
            createCard(it)
        }.sumOf { item -> item.score }
    }

    fun part2(lines: List<String>): Int {
        val cards = lines.map { createCard(it) }

        val processQueue = LinkedList<Card>()
        processQueue.addAll(cards)
        var count = 0

        while (processQueue.isNotEmpty()) {
            val toProcess = processQueue.removeFirst()

            for (i in 0..<toProcess.matching) {
                processQueue.add(cards[toProcess.id + i])
            }

            count++
        }

        return count
    }

    val lines = readInput("Day04")
    part1(lines).println() // 24160
    part2(lines).println() // 5659035
}

data class Card(val id: Int, val winningNumbers: List<Int>, val numbers: List<Int>) {
    val matching: Int = numbers.intersect(winningNumbers.toSet()).size
    val score: Int = if (matching == 0) 0 else 2.toDouble().pow((matching - 1)).toInt()
}