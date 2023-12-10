fun main() {
    fun read(lines: List<String>): Pair<String, List<Node>> {
        val list = lines.filter { it.isNotEmpty() }
        val instructions = list.first()
        val network = list
            .takeLast(list.size - 1)
            .map {
                val idSplit = it.split("\\s*=\\s*".toRegex())
                val links = idSplit[1].replace("[()]".toRegex(), "").split("\\s*,\\s*".toRegex())
                Node(idSplit[0], links[0], links[1])
            }

        return Pair(instructions, network)
    }

    fun part1(data: Pair<String, List<Node>>): Int {
        val instruction = data.first
        val network = data.second.fold(mutableMapOf<String, Node>()) { acc, node ->
            acc[node.id] = node
            acc
        }
        var currentNode = network["AAA"]!!
        var count = 0
        while (currentNode.id != "ZZZ") {
            val direction = instruction[count.mod(instruction.length)]
            if (direction == 'L') {
                currentNode = network[currentNode.left]!!
            } else {
                currentNode = network[currentNode.right]!!
            }
            count++
        }

        return count
    }

    fun part2(data: Pair<String, List<Node>>): Long {
        val instruction = data.first
        val network = data.second.fold(mutableMapOf<String, Node>()) { acc, node ->
            acc[node.id] = node
            acc
        }

        val currentNode = data.second.filter { it.id.endsWith("A") }.toMutableList()

        val counts = currentNode.map {
            var current = it
            var count = 0
            while (!current.id.endsWith("Z")) {
                val direction = instruction[count.mod(instruction.length)]
                current = if (direction == 'L') {
                    network[current.left]!!
                } else {
                    network[current.right]!!
                }
                count++
            }

            count
        }

        return counts.fold(counts.first().toLong()) { acc, i ->
            lcm(acc, i.toLong())
        }
    }

    val input = readInput("Day08")
    part1(read(input)).println() // 16897
    part2(read(input)).println() // 16563603485021L
}

fun lcm(left: Long, right: Long): Long {
    var gcd = 1
    var i = 1
    while (i <= left && i <= right) {
        if (left % i == 0L && right % i == 0L)
            gcd = i
        ++i
    }

    return left * right / gcd
}

data class Node(val id: String, val left: String, val right: String)