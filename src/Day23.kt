fun main() {
	fun offset(instruction: String, signIndex: Int): Int {
		val sign = if (instruction[signIndex] == '+') 1 else -1
		val offset = instruction.substring(signIndex + 1).toInt()
		return offset * sign - 1
	}

	fun run(instructions: List<String>, aInit: Long): Long {
		var i = 0
		var a = aInit
		var b = 0L
		while (i in instructions.indices) {
			val ins = instructions[i]
			when (ins.take(3)) {
				"hlf" -> if (ins[4] == 'a') a /= 2 else b /= 2
				"tpl" -> if (ins[4] == 'a') a *= 3 else b *= 3
				"inc" ->if (ins[4] == 'a') a++ else b++
				"jmp" -> i += offset(ins, 4)
				"jie" -> if (ins[4] == 'a') {
					if (a % 2 == 0L) i += offset(ins, 7)
				} else {
					if (b % 2 == 0L) i += offset(ins, 7)
				}
				"jio" -> if (ins[4] == 'a') {
					if (a == 1L) i += offset(ins, 7)
				} else {
					if (b == 1L) i += offset(ins, 7)
				}
			}
			i++
		}
		return b
	}

	fun part1(input: List<String>): Long {
		return run(input, 0)
	}

	fun part2(input: List<String>): Long {
		return run(input, 1)
	}

	val testInput = readInput("Day23_test")
	check(part1(testInput) == 0L)
	check(part2(testInput) == 0L)

	val input = readInput("Day23")
	part1(input).println()
	part2(input).println()
}
