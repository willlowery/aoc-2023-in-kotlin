fun main() {

    fun convert(lines: List<String>): Pair<List<Long>, List<MyCoolMap>> {
        val noEmptyLines = lines.filter { it.isNotEmpty() }
        val seeds = noEmptyLines.first().split(":")[1].trim().split(" ").map { it.trim().toLong() }

        val functions = mutableListOf<MyCoolMap>()

        noEmptyLines.takeLast(noEmptyLines.size - 1).forEach {
            if (it.contains(":")) {
                functions.add(MyCoolMap())
            } else {
                val numbers = it.split(" ").map { it.toLong() }

                functions.last().put(
                        numbers[1],
                        numbers[0],
                        numbers[2]
                )
            }
        }


        return Pair(seeds, functions)
    }

    fun part1(input: List<String>): Long {
        val item = convert(input)

        return item.first.minOfOrNull {
            item.second.fold(it)
            { acc, myCoolMap ->
                myCoolMap.get(acc)
            }
        } ?: 0
    }

    fun part2(input: List<String>) {
        val item = convert(input)
        var min = Long.MAX_VALUE
        item.first.fold(mutableListOf<MutableList<Long>>())
        { acc, l ->
            if (acc.isEmpty()) {
                acc.add(mutableListOf(l))
            } else if (acc.last().size < 2) {
                acc.last().add(l)
            } else {
                acc.add(mutableListOf(l))
            }
            acc
        }.forEach {
            println(Day05Range(it.first(), it.first(), it.last()))

        }
        println(min)
    }

    val input = readInput("Day05")
    part1(input).println() // 551761867
    part2(input) //57451710 - not right?
}


data class MyCoolMap(
        private val l: MutableList<Day05Range> = mutableListOf()
) {
    fun put(incomingStart: Long, endingStart: Long, length: Long) {
        l.add(Day05Range(incomingStart, endingStart, length))
    }

    fun get(item: Long): Long {
        val m = l.find { it.contains(item) }
        return m?.map(item) ?: item
    }
}

data class Day05Range(
        val incoming: Long,
        val ending: Long,
        val length: Long

) {
    val incomingEnd = incoming + length

    fun contains(value: Long): Boolean {
        return value in incoming..incomingEnd
    }

    fun map(value: Long): Long {
        return ending + value - incoming
    }
}
