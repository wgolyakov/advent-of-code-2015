fun main() {
	fun part1(input: List<String>): Int {
		var total = 0
		for (line in input) {
			val (l, w, h) = line.split('x').map { it.toInt() }
			val s = 2 * l * w + 2 * w * h + 2 * h * l
			val minS = listOf(l * w, w * h, h * l).min()
			total += s + minS
		}
		return total
	}

	fun part2(input: List<String>): Int {
		var total = 0
		for (line in input) {
			val (l, w, h) = line.split('x').map { it.toInt() }
			val minP = listOf(l * 2 + w * 2, w * 2 + h * 2, h * 2 + l * 2).min()
			val bow = l * w * h
			total += minP + bow
		}
		return total
	}

	val testInput = readInput("Day02_test")
	check(part1(testInput) == 58 + 43)
	check(part2(testInput) == 34 + 14)

	val input = readInput("Day02")
	part1(input).println()
	part2(input).println()
}
