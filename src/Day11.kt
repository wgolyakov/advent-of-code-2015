fun main() {
	fun next(pass: String): String {
		val result = StringBuilder()
		var r = 1
		for (c in pass.reversed()) {
			val a = c + r
			if (a > 'z') {
				r = 1
				result.append('a' + (a - 'z' - 1))
			} else {
				r = 0
				result.append(a)
			}
		}
		return result.reverse().toString()
	}

	fun good(pass: String): Boolean {
		pass.windowed(3).find { it[0] + 1 == it[1] && it[1] + 1 == it[2] } ?: return false
		if ('i' in pass || 'o' in pass || 'l' in pass) return false
		val pair = pass.windowed(2).find { it[0] == it[1] } ?: return false
		pass.substringAfter(pair).windowed(2).find { it[0] == it[1] } ?: return false
		return true
	}

	fun part1(input: String): String {
		var p = input
		do {
			p = next(p)
		} while (!good(p))
		return p
	}

	fun part2(input: String): String {
		return part1(part1(input))
	}

	val testInput = readInput("Day11_test")
	check(part1(testInput[0]) == "abcdffaa")
	check(part1(testInput[1]) == "ghjaabcc")

	val input = readFile("Day11")
	part1(input).println()
	part2(input).println()
}
