fun main() {
	fun lookAndSay(input: String, times: Int): Int {
		var result = StringBuilder(input)
		for (i in 0 until times) {
			val s = StringBuilder()
			var a = result[0]
			var n = 0
			for (c in result) {
				if (c == a) {
					n++
				} else {
					s.append(n).append(a)
					a = c
					n = 1
				}
			}
			s.append(n).append(a)
			result = s
		}
		return result.length
	}

	fun part1(input: String): Int {
		return lookAndSay(input, 40)
	}

	fun part2(input: String): Int {
		return lookAndSay(input, 50)
	}

	val testInput = readFile("Day10_test")
	check(part1(testInput) == 82350)
	check(part2(testInput) == 1166642)

	val input = readFile("Day10")
	part1(input).println()
	part2(input).println()
}
