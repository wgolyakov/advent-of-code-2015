fun main() {
	fun parse(input: List<String>): List<List<Int>> {
		val result = mutableListOf<List<Int>>()
		for (line in input) {
			val ingredient = line.split(':')[1].split(',')
				.map { it.trim().split(' ')[1].toInt() }
			result.add(ingredient)
		}
		return result
	}

	fun recurs(ingredients: List<List<Int>>, iNum: Int, teaspoons: IntArray, maxScore: Int, useCalories: Boolean): Int {
		if (iNum == ingredients.size) {
			if (teaspoons.sum() != 100) return maxScore
			val scores = IntArray(ingredients[0].size)
			for ((i, ingredient) in ingredients.withIndex()) {
				for (p in ingredient.indices) {
					scores[p] += ingredient[p] * teaspoons[i]
				}
			}
			for (p in scores.indices) {
				if (scores[p] < 0) scores[p] = 0
			}
			val score = scores.copyOfRange(0, scores.size - 1).reduce { a, b -> a * b }
			if (useCalories) {
				val calories = scores.last()
				return if (calories == 500 && score > maxScore) score else maxScore
			}
			return if (score > maxScore) score else maxScore
		}
		var max = maxScore
		val free = 100 - teaspoons.copyOfRange(0, iNum).sum()
		for (t in 0..free) {
			teaspoons[iNum] = t
			max = recurs(ingredients, iNum + 1, teaspoons, max, useCalories)
		}
		return max
	}

	fun part1(input: List<String>): Int {
		val ingredients = parse(input)
		val teaspoons = IntArray(ingredients.size)
		return recurs(ingredients, 0, teaspoons, 0, false)
	}

	fun part2(input: List<String>): Int {
		val ingredients = parse(input)
		val teaspoons = IntArray(ingredients.size)
		return recurs(ingredients, 0, teaspoons, 0, true)
	}

	val testInput = readInput("Day15_test")
	check(part1(testInput) == 62842880)
	check(part2(testInput) == 57600000)

	val input = readInput("Day15")
	part1(input).println()
	part2(input).println()
}
