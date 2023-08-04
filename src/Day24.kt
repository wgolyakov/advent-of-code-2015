fun main() {
	var minCount = Int.MAX_VALUE
	var minQE = Long.MAX_VALUE

	fun recurs(weights: List<Int>, i1: Int, gr1: List<Int>, grWeight: Int) {
		if (gr1.size > minCount) return
		val sum1 = gr1.sum()
		if (sum1 > grWeight) return
		if (sum1 < grWeight) {
			for (i in i1 until weights.size) {
				recurs(weights, i + 1, gr1 + weights[i], grWeight)
			}
			return
		}
		if (gr1.size < minCount) {
			minCount = gr1.size
			minQE = gr1.fold(1L) { a, b -> a * b }
			return
		}
		val qe = gr1.fold(1L) { a, b -> a * b }
		if (qe < minQE) minQE = qe
	}

	fun part1(input: List<String>): Long {
		val weights = input.map { it.toInt() }.sorted().reversed()
		val grWeight = weights.sum() / 3
		minCount = Int.MAX_VALUE
		minQE = Long.MAX_VALUE
		recurs(weights, 0, emptyList(), grWeight)
		return minQE
	}

	fun part2(input: List<String>): Long {
		val weights = input.map { it.toInt() }.sorted().reversed()
		val grWeight = weights.sum() / 4
		minCount = Int.MAX_VALUE
		minQE = Long.MAX_VALUE
		recurs(weights, 0, emptyList(), grWeight)
		return minQE
	}

	val testInput = readInput("Day24_test")
	check(part1(testInput) == 99L)
	check(part2(testInput) == 44L)

	val input = readInput("Day24")
	part1(input).println()
	part2(input).println()
}
