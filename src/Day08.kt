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

    fun part2(data: Pair<String, List<Node>>): Int {
        val instruction = data.first
        val network = data.second.fold(mutableMapOf<String, Node>()) { acc, node ->
            acc[node.id] = node
            acc
        }

        var currentNode = data.second.filter { it.id.endsWith("A") }.first()

        (0..instruction.length * 5).fold(mutableListOf(currentNode)) { acc, i ->
            val last = acc.last()
            if (instruction[i.mod(instruction.length)] == 'L') {
                acc.add(network[last.left]!!)
            } else {
                acc.add(network[last.right]!!)
            }
            acc
        }.forEach{ it.println()}


        return 0
    }

    val input = readInput("Day08")
    part1(read(input)).println()
//    part2(read(input)).println()
}

data class Node(val id: String, val left: String, val right: String)