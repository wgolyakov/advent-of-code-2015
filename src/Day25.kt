fun main() {
	fun part1(input: String): Int {
		val row = input.substringAfter("row ").substringBefore(',').toInt()
		val column = input.substringAfter("column ").substringBefore('.').toInt()
		var n = 20151125
		var x = 1
		var y = 1
		while (x != column || y != row) {
			x++
			y--
			if (y == 0) {
				y = x
				x = 1
			}
			n = (n.toLong() * 252533 % 33554393).toInt()
		}
		return n
	}

	val testInput = readFile("Day25_test")
	check(part1(testInput) == 33511524)

	val input = readFile("Day25")
	part1(input).println()
}
