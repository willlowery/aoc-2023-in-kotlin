fun main() {
    fun race(lines: List<String>): List<Race> {
        val time = lines.first().split(":")[1].trim().split("\\s+".toRegex()).map { it.toLong() }
        val distance = lines.last().split(":")[1].trim().split("\\s+".toRegex()).map { it.toLong() }

        val races = mutableListOf<Race>()
        time.forEachIndexed { index, i ->
            races.add(Race(i, distance[index]))
        }

        return races
    }


    fun part1(lines: List<String>): Int {
        val races = race(lines)
        return races.map {
            val race = it
            (0..race.time).map { i ->
                val remaining = race.time - i
                i * remaining
            }.filter {
                it > race.distance
            }.size
        }.fold(1) { acc, i ->
            acc * i
        }
    }

    fun part2(lines: List<String>): Int {
        return part1(lines.map { it.replace(" ", "") })
    }

    val lines = readInput("Day06")

    part1(lines).println() //  1413720
    part2(lines).println() // 30565288
}

data class Race(val time: Long, val distance: Long)