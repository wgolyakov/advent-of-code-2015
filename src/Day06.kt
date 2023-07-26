fun main() {
	fun parse(s: String): List<Int> {
		val (p1, p2) = s.split(" through ")
		val (x1, y1) = p1.split(',').map { it.toInt() }
		val (x2, y2) = p2.split(',').map { it.toInt() }
		return listOf(x1, y1, x2, y2)
	}

	fun part1(input: List<String>): Int {
		val grid = Array(1000) { BooleanArray(1000) }
		for (line in input) {
			if (line.startsWith("turn on ")) {
				val (x1, y1, x2, y2) = parse(line.substring("turn on ".length))
				for (x in x1..x2)
					for (y in y1..y2)
						grid[x][y] = true
			} else if (line.startsWith("turn off ")) {
				val (x1, y1, x2, y2) = parse(line.substring("turn off ".length))
				for (x in x1..x2)
					for (y in y1..y2)
						grid[x][y] = false
			} else if (line.startsWith("toggle ")) {
				val (x1, y1, x2, y2) = parse(line.substring("toggle ".length))
				for (x in x1..x2)
					for (y in y1..y2)
						grid[x][y] = !grid[x][y]
			} else error("Wrong instruction: $line")
		}
		return grid.sumOf { col -> col.count { it } }
	}

	fun part2(input: List<String>): Int {
		val grid = Array(1000) { IntArray(1000) }
		for (line in input) {
			if (line.startsWith("turn on ")) {
				val (x1, y1, x2, y2) = parse(line.substring("turn on ".length))
				for (x in x1..x2)
					for (y in y1..y2)
						grid[x][y]++
			} else if (line.startsWith("turn off ")) {
				val (x1, y1, x2, y2) = parse(line.substring("turn off ".length))
				for (x in x1..x2)
					for (y in y1..y2)
						if (grid[x][y] > 0) grid[x][y]--
			} else if (line.startsWith("toggle ")) {
				val (x1, y1, x2, y2) = parse(line.substring("toggle ".length))
				for (x in x1..x2)
					for (y in y1..y2)
						grid[x][y] += 2
			} else error("Wrong instruction: $line")
		}
		return grid.sumOf { it.sum() }
	}

	val input = readInput("Day06")
	part1(input).println()
	part2(input).println()
}
