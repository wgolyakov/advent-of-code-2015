fun main() {
	fun parse(input: List<String>): MutableMap<String, MutableMap<String, Int>> {
		val table = mutableMapOf<String, MutableMap<String, Int>>()
		for (line in input) {
			val (a, sign, h, b) = Regex("(\\w+) would (\\w+) (\\d+) happiness units by sitting next to (\\w+).")
				.matchEntire(line)!!.groupValues.takeLast(4)
			val happiness = h.toInt() * (if (sign == "gain") 1 else -1)
			val aHap = table.getOrPut(a) { mutableMapOf() }
			val bHap = table.getOrPut(b) { mutableMapOf() }
			aHap[b] = (aHap[b] ?: 0) + happiness
			bHap[a] = (bHap[a] ?: 0) + happiness
		}
		return table
	}

	fun recurs(table: MutableMap<String, MutableMap<String, Int>>, guests: List<String>,
			   path: List<String>, maxHappiness: Int): Int {
		if (guests.isEmpty()) {
			var happiness = table[path.last()]!![path.first()]!!
			for (i in 0 until path.size - 1) {
				happiness += table[path[i]]!![path[i + 1]]!!
			}
			return if (happiness > maxHappiness) happiness else maxHappiness
		}
		var max = maxHappiness
		for (guest in guests) {
			max = recurs(table, guests - guest, path + guest, max)
		}
		return max
	}

	fun part1(input: List<String>): Int {
		val table = parse(input)
		val first = table.keys.first()
		return recurs(table, table.keys.toList() - first, listOf(first), 0)
	}

	fun part2(input: List<String>): Int {
		val table = parse(input)
		for (e in table) {
			e.value["i"] = 0
		}
		table["i"] = table.keys.associateWith { 0 }.toMutableMap()
		val first = table.keys.first()
		return recurs(table, table.keys.toList() - first, listOf(first), 0)
	}

	val testInput = readInput("Day13_test")
	check(part1(testInput) == 330)

	val input = readInput("Day13")
	part1(input).println()
	part2(input).println()
}
