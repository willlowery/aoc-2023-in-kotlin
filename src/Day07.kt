fun main() {
    fun parseLine(line: String, rankFunction: (input: String) -> HandRank): HandWithBid {
        val parts = line.split(" ")

        return HandWithBid(parts[1].toInt(), parts[0], rankFunction(parts[0]))
    }

    fun part1(lines: List<String>): Int {
        val standardCardRules = StandardCardRules()
        return lines
            .map { parseLine(it, standardCardRules::rank) }
            .sortedWith(standardCardRules)
            .mapIndexed { index, item ->
                (item.bid * index.plus(1))
            }
            .sum()
    }

    fun part2(lines: List<String>): Int {
        val jackWildCardRules = JackWildCardRules()
        return lines
            .map { parseLine(it, jackWildCardRules::rank) }
            .sortedWith(jackWildCardRules)
            .mapIndexed { index, item ->
                (item.bid * index.plus(1))
            }
            .sum()
    }


    val input = readInput("Day07")

    part1(input).println() //253313241
    part2(input).println() //253362743

}

enum class HandRank(val rank: Int) {
    FIVE_OF_A_KIND(1),
    FOUR_OF_A_KIND(2),
    FULL_HOUSE(3),
    THREE_OF_A_KIND(4),
    TWO_PAIR(5),
    ONE_PAIR(6),
    HIGH_CARD(7)
}

data class HandWithBid(
    val bid: Int,
    val hand: String,
    val rankType: HandRank = HandRank.HIGH_CARD
) {
    fun compare(right: HandWithBid, cardRanks: Map<Char, Int>): Int {
        val initial = rankType.rank.compareTo(right.rankType.rank)
        if (initial == 0) {
            for ((index, c) in hand.withIndex()) {
                val comp = cardRanks[c]!!.compareTo(cardRanks[right.hand[index]]!!)
                if (comp != 0) {
                    return comp
                }
            }
            return 0
        }
        return initial
    }
}

fun rank(cardCounts: Map<Char, Int>): HandRank {
    if (cardCounts.size == 1) {
        return HandRank.FIVE_OF_A_KIND
    } else if (cardCounts.size == 2) {
        val first = cardCounts.values.first()
        return if (first == 1 || first == 4)
            HandRank.FOUR_OF_A_KIND
        else
            HandRank.FULL_HOUSE
    } else if (cardCounts.size == 3) {
        val first = cardCounts.values.maxOf { it }
        if (first == 3) {
            return HandRank.THREE_OF_A_KIND
        } else {
            return HandRank.TWO_PAIR
        }
    } else if (cardCounts.size == 4) {
        return HandRank.ONE_PAIR
    } else {
        return HandRank.HIGH_CARD
    }
}

class StandardCardRules : Comparator<HandWithBid> {
    private val cardRanks = mapOf(
        'A' to 1,
        'K' to 2,
        'Q' to 3,
        'J' to 4,
        'T' to 5,
        '9' to 6,
        '8' to 7,
        '7' to 8,
        '6' to 9,
        '5' to 10,
        '4' to 11,
        '3' to 12,
        '2' to 13
    )

    fun rank(hand: String): HandRank {
        val cardCounts = mutableMapOf<Char, Int>()
        hand.forEach {
            cardCounts[it] = cardCounts.getOrDefault(it, 0) + 1
        }
        return rank(cardCounts)
    }

    override fun compare(o1: HandWithBid?, o2: HandWithBid?): Int {
        return -o1!!.compare(o2!!, cardRanks)
    }
}

class JackWildCardRules : Comparator<HandWithBid> {
    private val cardRanks = mapOf(
        'A' to 1,
        'K' to 2,
        'Q' to 3,
        'T' to 4,
        '9' to 5,
        '8' to 6,
        '7' to 7,
        '6' to 8,
        '5' to 9,
        '4' to 10,
        '3' to 11,
        '2' to 12,
        'J' to 13
    )

    fun rank(hand: String): HandRank {
        val cardCounts = mutableMapOf<Char, Int>()
        hand.forEach {
            cardCounts[it] = cardCounts.getOrDefault(it, 0) + 1
        }

        val jCount = cardCounts.getOrDefault('J', 0)
        if (jCount != 5) {
            cardCounts.remove('J')
            val largestCount = cardCounts.entries.maxByOrNull { i -> i.value }!!
            cardCounts[largestCount.key] = cardCounts[largestCount.key]!!.plus(jCount)
        }

        return rank(cardCounts)
    }

    override fun compare(o1: HandWithBid?, o2: HandWithBid?): Int {
        return -o1!!.compare(o2!!, cardRanks)
    }
}


