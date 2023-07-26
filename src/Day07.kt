fun main() {
	fun emulate(input: List<String>, wires: MutableMap<String, UShort>, overrideB: Boolean) {
		fun value(a: String): UShort? {
			if (a[0].isDigit()) return a.toUShort()
			return wires[a]
		}

		val queue = ArrayDeque(input)
		while (queue.isNotEmpty()) {
			val command = queue.removeFirst()
			if ("NOT" in command) {
				val (a, b) = Regex("NOT (\\w+) -> (\\w+)").matchEntire(command)!!.groupValues.takeLast(2)
				val aValue = value(a)
				if (aValue == null) {
					queue.addLast(command)
				} else {
					if (!overrideB || b != "b") wires[b] = aValue.inv()
				}
			} else if ("AND" in command) {
				val (a, b, c) = Regex("(\\w+) AND (\\w+) -> (\\w+)").matchEntire(command)!!.groupValues.takeLast(3)
				val aValue = value(a)
				val bValue = value(b)
				if (aValue == null || bValue == null) {
					queue.addLast(command)
				} else {
					if (!overrideB || c != "b") wires[c] = aValue and bValue
				}
			} else if ("OR" in command) {
				val (a, b, c) = Regex("(\\w+) OR (\\w+) -> (\\w+)").matchEntire(command)!!.groupValues.takeLast(3)
				val aValue = value(a)
				val bValue = value(b)
				if (aValue == null || bValue == null) {
					queue.addLast(command)
				} else {
					if (!overrideB || c != "b") wires[c] = aValue or bValue
				}
			} else if ("LSHIFT" in command) {
				val (a, b, c) = Regex("(\\w+) LSHIFT (\\w+) -> (\\w+)").matchEntire(command)!!.groupValues.takeLast(3)
				val aValue = value(a)
				val bValue = value(b)
				if (aValue == null || bValue == null) {
					queue.addLast(command)
				} else {
					if (!overrideB || c != "b") wires[c] = (aValue.toUInt() shl bValue.toInt()).toUShort()
				}
			} else if ("RSHIFT" in command) {
				val (a, b, c) = Regex("(\\w+) RSHIFT (\\w+) -> (\\w+)").matchEntire(command)!!.groupValues.takeLast(3)
				val aValue = value(a)
				val bValue = value(b)
				if (aValue == null || bValue == null) {
					queue.addLast(command)
				} else {
					if (!overrideB || c != "b") wires[c] = (aValue.toUInt() shr bValue.toInt()).toUShort()
				}
			} else {
				val (a, b) = Regex("(\\w+) -> (\\w+)").matchEntire(command)!!.groupValues.takeLast(2)
				val aValue = value(a)
				if (aValue == null) {
					queue.addLast(command)
				} else {
					if (!overrideB || b != "b") wires[b] = aValue
				}
			}
		}
	}

	fun part1(input: List<String>): Int {
		val wires = mutableMapOf<String, UShort>()
		emulate(input, wires, false)
		return wires["a"]?.toInt() ?: 0
	}

	fun part2(input: List<String>): Int {
		val wires = mutableMapOf<String, UShort>()
		emulate(input, wires, false)
		val a = wires["a"] ?: 0.toUShort()
		wires.clear()
		wires["b"] = a
		emulate(input, wires, true)
		return wires["a"]?.toInt() ?: 0
	}

	val testInput = readInput("Day07_test")
	check(part1(testInput) == 0)
	check(part2(testInput) == 0)

	val input = readInput("Day07")
	part1(input).println()
	part2(input).println()
}
