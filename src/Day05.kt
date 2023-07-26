fun main() {
	fun part1(input: List<String>): Int {
		var niceCount = 0
		val vowels = "aeiou".toSet()
		val badStrings = setOf("ab", "cd", "pq", "xy")
		for (line in input) {
			val vowelsCount = line.count { it in vowels }
			if (vowelsCount < 3) continue
			val twiceLetter = line.windowed(2).find { it[0] == it[1] }
			if (twiceLetter == null) continue
			val containsBadString = badStrings.any { it in line }
			if (containsBadString) continue
			niceCount++
		}
		return niceCount
	}

	fun part2(input: List<String>): Int {
		var niceCount = 0
		for (line in input) {
			var nice = false
			for ((i, s) in line.windowed(2).withIndex()) {
				if (s in line.substring(0, i) || (i < line.length - 2 && s in line.substring(i + 2, line.length))) {
					nice = true
					break
				}
			}
			if (!nice) continue
			nice = line.windowed(3).find { it[0] == it[2] } != null
			if (!nice) continue
			niceCount++
		}
		return niceCount
	}

	val testInput = readInput("Day05_test")
	check(part1(testInput) == 2)
	val testInput2 = readInput("Day05_test_2")
	check(part2(testInput2) == 2)

	val input = readInput("Day05")
	part1(input).println()
	part2(input).println()
}
