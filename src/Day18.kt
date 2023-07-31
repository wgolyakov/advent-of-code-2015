fun main() {
	fun part1(input: List<String>, steps: Int): Int {
		val grid = input.map { StringBuilder(it) }
		var prev = grid.map { it.toString() }
		for (s in 0 until steps) {
			for (y in prev.indices) {
				for (x in prev[y].indices) {
					var neighbors = 0
					for (dx in -1..1) {
						for (dy in -1..1) {
							if (dx == 0 && dy == 0) continue
							val nx = x + dx
							val ny = y + dy
							if (nx >= 0 && ny >= 0 && ny < prev.size && nx < prev[y].length &&
								prev[ny][nx] == '#') neighbors++
						}
					}
					grid[y][x] = if (prev[y][x] == '#') {
						if (neighbors == 2 || neighbors == 3) '#' else '.'
					} else {
						if (neighbors == 3) '#' else '.'
					}
				}
			}
			prev = grid.map { it.toString() }
		}
		return grid.sumOf { row -> row.count { it == '#' } }
	}

	fun part2(input: List<String>, steps: Int): Int {
		val grid = input.map { StringBuilder(it) }
		grid[0][0] = '#'
		grid[0][grid[0].length - 1] = '#'
		grid[grid.size - 1][0] = '#'
		grid[grid.size - 1][grid[0].length - 1] = '#'
		var prev = grid.map { it.toString() }
		for (s in 0 until steps) {
			for (y in prev.indices) {
				for (x in prev[y].indices) {
					var neighbors = 0
					for (dx in -1..1) {
						for (dy in -1..1) {
							if (dx == 0 && dy == 0) continue
							val nx = x + dx
							val ny = y + dy
							if (nx >= 0 && ny >= 0 && ny < prev.size && nx < prev[y].length &&
								prev[ny][nx] == '#') neighbors++
						}
					}
					grid[y][x] = if (prev[y][x] == '#') {
						if (neighbors == 2 || neighbors == 3) '#' else '.'
					} else {
						if (neighbors == 3) '#' else '.'
					}
				}
			}
			grid[0][0] = '#'
			grid[0][grid[0].length - 1] = '#'
			grid[grid.size - 1][0] = '#'
			grid[grid.size - 1][grid[0].length - 1] = '#'
			prev = grid.map { it.toString() }
		}
		return grid.sumOf { row -> row.count { it == '#' } }
	}

	val testInput = readInput("Day18_test")
	check(part1(testInput, 4) == 4)
	check(part2(testInput, 5) == 17)

	val input = readInput("Day18")
	part1(input, 100).println()
	part2(input, 100).println()
}
