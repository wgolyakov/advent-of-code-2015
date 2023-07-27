fun main() {
	fun parse(input: List<String>): MutableMap<String, MutableMap<String, Int>> {
		val graph = mutableMapOf<String, MutableMap<String, Int>>()
		for (line in input) {
			val (a, b, len) = Regex("(\\w+) to (\\w+) = (\\d+)").matchEntire(line)!!.groupValues.takeLast(3)
			val aDist = graph.getOrPut(a) { mutableMapOf() }
			val bDist = graph.getOrPut(b) { mutableMapOf() }
			aDist[b] = len.toInt()
			bDist[a] = len.toInt()
		}
		return graph
	}

	fun recurs(graph: MutableMap<String, MutableMap<String, Int>>, locations: List<String>,
			   path: List<String>, distance: Int, bestDistance: Int, isMin: Boolean): Int {
		if (locations.isEmpty()) {
			return if (isMin) {
				if (distance < bestDistance) distance else bestDistance
			} else {
				if (distance > bestDistance) distance else bestDistance
			}
		}
		var best = bestDistance
		for (location in locations) {
			val d = if (path.isEmpty()) 0 else graph[path.last()]!![location]!!
			best = recurs(graph, locations - location, path + location, distance + d, best, isMin)
		}
		return best
	}

	fun part1(input: List<String>): Int {
		val graph = parse(input)
		return recurs(graph, graph.keys.toList(), emptyList(), 0, Int.MAX_VALUE, true)
	}

	fun part2(input: List<String>): Int {
		val graph = parse(input)
		return recurs(graph, graph.keys.toList(), emptyList(), 0, 0, false)
	}

	val testInput = readInput("Day09_test")
	check(part1(testInput) == 605)
	check(part2(testInput) == 982)

	val input = readInput("Day09")
	part1(input).println()
	part2(input).println()
}
