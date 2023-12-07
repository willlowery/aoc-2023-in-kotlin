fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            val digits = it.filter { char -> char.isDigit() }
            val firstAndLast = digits.first() + "" + digits.last()

            firstAndLast.toInt()
        }.reduce { acc, int ->
            acc + int
        }
    }

    fun part2(input: List<String>): Int {
        val replacements = mapOf(
            "one" to "1e",
            "two" to "2o",
            "three" to "3e",
            "four" to "4r",
            "five" to "5e",
            "six" to "6x",
            "seven" to "7n",
            "eight" to "8t",
            "nine" to "9e"
        )

        val lines = input.map { line ->
            line.map {
                it.toString()
            }.reduce { acc, char ->
                var result = acc + char
                for ((key, value) in replacements) {
                    result = result.replace(key, value)
                }
                result
            }
        }

        return part1(lines)
    }


    val input = readInput("Day01")

    part1(input).println() //54708
    part2(input).println() //54087
}
