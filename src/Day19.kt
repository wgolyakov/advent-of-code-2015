fun main() {
	fun part1(input: List<String>): Int {
		val replacements = input.take(input.size - 2).map { it.split(" => ") }
		val molecule = input.last()
		val distinct = mutableSetOf<String>()
		for (r in replacements) {
			var i = -1
			do {
				i = molecule.indexOf(r[0], i + 1)
				if (i != -1) {
					val m = molecule.substring(0, i) + r[1] + molecule.substring(i + r[0].length)
					distinct.add(m)
				}
			} while (i != -1)
		}
		return distinct.size
	}

	class Step(var molecule: String, var repNum: Int, var ind: Int)

	fun part2(input: List<String>): Int {
		val replacements = input.take(input.size - 2).map { it.split(" => ") }.toMutableList()
		val medicineMolecule = input.last()
		while (true) {
			val path = mutableListOf(Step(medicineMolecule, 0, -1))
			var step = 0
			for (count in 0..10000) {
				val s = path[step]
				if (s.molecule == "e") {
					return step
				}
				val r = replacements[s.repNum]
				s.ind = if (r[0] == "e" && r[1] != s.molecule)
					-1
				else
					s.molecule.indexOf(r[1], s.ind + 1)
				if (s.ind == -1) {
					s.repNum++
					if (s.repNum >= replacements.size) {
						step--
						if (step <= 0) return -1 else continue
					}
				} else {
					val m = s.molecule.substring(0, s.ind) + r[0] + s.molecule.substring(s.ind + r[1].length)
					step++
					val sNext = Step(m, 0, -1)
					if (path.size <= step) path.add(sNext) else path[step] = sNext
				}
			}
			replacements.shuffle()
		}
	}

	val testInput = readInput("Day19_test")
	check(part1(testInput) == 7)
	check(part2(testInput) == 6)

	val input = readInput("Day19")
	part1(input).println()
	part2(input).println()
}
