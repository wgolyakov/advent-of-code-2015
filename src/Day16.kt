fun main() {
	val things = mapOf(
		"children" to 3,
		"cats" to 7,
		"samoyeds" to 2,
		"pomeranians" to 3,
		"akitas" to 0,
		"vizslas" to 0,
		"goldfish" to 5,
		"trees" to 3,
		"cars" to 2,
		"perfumes" to 1
	)

	fun parse(input: List<String>): List<Map<String, Int>> {
		val result = mutableListOf<Map<String, Int>>()
		for (line in input) {
			val sue = Regex("Sue (\\d+): (\\w+): (\\d+), (\\w+): (\\d+), (\\w+): (\\d+)").matchEntire(line)!!
				.groupValues.takeLast(6).windowed(2, 2).associate { it[0] to it[1].toInt() }
			result.add(sue)
		}
		return result
	}

	fun part1(input: List<String>): Int {
		val sues = parse(input)
		return sues.withIndex().first { (_, sue) -> sue.all { things[it.key] == it.value } }.index + 1
	}

	fun part2(input: List<String>): Int {
		val sues = parse(input)
		return sues.withIndex().first { (_, sue) ->
			(sue - listOf("cats", "trees", "pomeranians", "goldfish")).all { things[it.key] == it.value } &&
			things["cats"]!! < (sue["cats"] ?: Int.MAX_VALUE) &&
			things["trees"]!! < (sue["trees"] ?: Int.MAX_VALUE) &&
			things["pomeranians"]!! > (sue["pomeranians"] ?: -1) &&
			things["goldfish"]!! > (sue["goldfish"] ?: -1)
		}.index + 1
	}

	val input = readInput("Day16")
	part1(input).println()
	part2(input).println()
}
