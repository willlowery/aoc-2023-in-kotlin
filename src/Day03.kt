import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    fun part1(world: World): Int {
        return world.digits
                .filter { digit -> world.symbols.any { digit.near(it.key) } }
                .sumOf { it.value }
    }

    fun part2(world: World): Int {
        return world.symbols
                .filter { it.value == '*' }
                .map { potentialGear -> Pair(potentialGear.key, world.digits.filter { it.near(potentialGear.key) }) }
                .filter { it.second.size == 2 }
                .sumOf { it.second.map { it.value }.reduce { acc, i -> acc * i } }
    }

    val lines = readInput("Day03")
    val world = WorldBuilder().build(lines)

    part1(world).println() // 525119
    part2(world).println() // 76504829
}

class WorldBuilder() {
    private val symbols = mutableMapOf<Point, Char>()
    private val digits = mutableSetOf<Digits>()

    private var points = mutableListOf<Point>()
    private var word = ""
    fun build(lines: List<String>): World {
        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, character ->
                if (character.isDigit()) {
                    points.add(Point(x, y))
                    word += character
                } else if (character == '.') {
                    addWord()
                } else {
                    symbols[Point(x, y)] = character
                    addWord()
                }
            }
            addWord()
        }

        return World(digits, symbols)
    }

    private fun addWord() {
        if (word.isNotEmpty()) {
            digits.add(Digits(points, word.toInt()))
            this.points = mutableListOf()
        }
        word = ""
    }
}

data class World(val digits: Set<Digits>, val symbols: Map<Point, Char>)
data class Point(val x: Int, val y: Int) {
    fun dist(other: Point): Double {
        return sqrt((x - other.x).toDouble().pow(2) +
                (y - other.y).toDouble().pow(2))
    }
}

data class Digits(val points: List<Point>, val value: Int) {
    fun near(point: Point): Boolean {
        return points.any { it.dist(point) < 1.5 }
    }
}