import kotlin.math.max

fun main() {
    fun readGameFromLine(line: String): Game {
        val identifierScores = line.split(":")
        val identifier = identifierScores[0]
        val scores = identifierScores[1].split(";")

        val scoreList = scores.map {
            it.split(",").map {
                val scoreArray = it.trim().split(" ")
                val score = scoreArray[0]
                val label = scoreArray[1]

                Score(score.toInt(), label)
            }
        }

        return Game(identifier.split(" ")[1].toInt(), scoreList)
    }

    fun part1(input: List<String>): Int {
        return input
                .map { line -> readGameFromLine(line) }
                .filter {
                    it.findMax("red") <= 12 && it.findMax("green") <= 13 && it.findMax("blue") <= 14
                }
                .sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        return input
                .map { readGameFromLine(it) }
                .sumOf {
                    it.findMax("red") * it.findMax("green") * it.findMax("blue")
                }
    }

    val input = readInput("Day02")
    part1(input).println() // 2061
    part2(input).println() // 72596
}

data class Game(
        val id: Int,
        val rounds: List<List<Score>>
) {
    fun findMax(label: String): Int {
        var result = 0;
        for (round in rounds) {
            for (score in round) {
                if (score.label == label) {
                    result = max(score.score, result);
                }
            }
        }
        return result;
    }
}

data class Score(val score: Int, val label: String)