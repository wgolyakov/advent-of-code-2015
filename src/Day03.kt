fun main() {
	class Santa(var x: Int = 0, var y: Int = 0, val visited: MutableSet<Pair<Int, Int>> = mutableSetOf(x to y)) {
		constructor(santa: Santa): this(santa.x, santa.y, santa.visited)

		fun move(move: Char) {
			when (move) {
				'>' -> x++
				'<' -> x--
				'^' -> y++
				'v' -> y--
				else -> error("Wrong move: $move")
			}
			visited.add(x to y)
		}
	}

	fun part1(input: String): Int {
		val santa = Santa()
		for (move in input) {
			santa.move(move)
		}
		return santa.visited.size
	}

	fun part2(input: String): Int {
		val santa = Santa()
		val roboSanta = Santa(santa)
		for (moves in input.windowed(2, 2)) {
			santa.move(moves[0])
			roboSanta.move(moves[1])
		}
		return santa.visited.size
	}

	val testInput = readInput("Day03_test")
	check(part1(testInput[0]) == 2)
	check(part1(testInput[2]) == 4)
	check(part1(testInput[3]) == 2)
	check(part2(testInput[1]) == 3)
	check(part2(testInput[2]) == 3)
	check(part2(testInput[3]) == 11)

	val input = readFile("Day03")
	part1(input).println()
	part2(input).println()
}
