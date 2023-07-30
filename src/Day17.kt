fun main() {
	fun recurs(containers: List<Int>, num: Int, combination: Set<Int>, combinations: MutableSet<Set<Int>>, liters: Int) {
		val v = combination.sumOf { containers[it] }
		if (v == liters) {
			combinations.add(combination)
			return
		} else if (v > liters) {
			return
		}
		if (num >= containers.size) return
		for (container in num until containers.size) {
			recurs(containers, container + 1, combination + container, combinations, liters)
		}
	}

	fun part1(input: List<String>, liters: Int): Int {
		val containers = input.map { it.toInt() }
		val combinations = mutableSetOf<Set<Int>>()
		recurs(containers, 0, emptySet(), combinations, liters)
		return combinations.size
	}

	fun part2(input: List<String>, liters: Int): Int {
		val containers = input.map { it.toInt() }
		val combinations = mutableSetOf<Set<Int>>()
		recurs(containers, 0, emptySet(), combinations, liters)
		val minNumber = combinations.minOf { it.size }
		return combinations.count { it.size == minNumber }
	}

	val testInput = readInput("Day17_test")
	check(part1(testInput, 25) == 4)
	check(part2(testInput, 25) == 3)

	val input = readInput("Day17")
	part1(input, 150).println()
	part2(input, 150).println()
}
