fun main() {
	fun part1(input: List<String>): Int {
		val codeCount = input.sumOf { it.length }
		var memoryCount = 0
		for (line in input) {
			var i = 1
			while (i < line.length - 1) {
				if (line[i] == '\\') {
					when (line[i + 1]) {
						'\\' -> i += 2
						'"' -> i += 2
						'x' -> i += 4
						else -> i++
					}
				} else {
					i++
				}
				memoryCount++
			}
		}
		return codeCount - memoryCount
	}

	fun part2(input: List<String>): Int {
		val codeCount = input.sumOf { it.length }
		@Suppress("USELESS_CAST")
		val encodedCount = input.sumOf { line -> 2 + line.sumOf { if (it == '\\' || it == '"') 2 else 1 as Int } }
		return encodedCount - codeCount
	}

	val testInput = readInput("Day08_test")
	check(part1(testInput) == 12)
	check(part2(testInput) == 19)

	val input = readInput("Day08")
	part1(input).println()
	part2(input).println()
}
