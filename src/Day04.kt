fun main() {
	fun part1(input: String): Int {
		for (i in 0 until Int.MAX_VALUE)
			if ((input + i).md5().startsWith("00000")) return i
		return -1
	}

	fun part2(input: String): Int {
		for (i in 0 until Int.MAX_VALUE)
			if ((input + i).md5().startsWith("000000")) return i
		return -1
	}

	val testInput = readInput("Day04_test")
	check(part1(testInput[0]) == 609043)
	check(part1(testInput[1]) == 1048970)
	check(part2(testInput[0]) == 6742839)
	check(part2(testInput[1]) == 5714438)

	val input = readFile("Day04")
	part1(input).println()
	part2(input).println()
}
