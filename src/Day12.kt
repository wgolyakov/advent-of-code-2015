class Day12 {
	abstract class Node(open val x: Any) {
		open fun isRed() = false
		open fun removeRed() {}
		override fun toString() = x.toString()
	}

	class NodeInt(override val x: Int) : Node(x)

	class NodeString(override val x: String) : Node(x) {
		override fun toString() = "\"$x\""
	}

	class NodeArray(override val x: MutableList<Node>) : Node(x) {
		override fun removeRed() {
			x.removeIf {
				if (it.isRed()) {
					true
				} else {
					it.removeRed()
					false
				}
			}
		}
		override fun toString() = x.joinToString(",", "[", "]")
	}

	class NodeObject(override val x: MutableMap<String, Node>) : Node(x) {
		override fun isRed(): Boolean {
			return x.values.find { (it as? NodeString)?.x == "red" } != null
		}

		override fun removeRed() {
			x.entries.removeIf {
				if (it.value.isRed()) {
					true
				} else {
					it.value.removeRed()
					false
				}
			}
		}

		override fun toString() = x.map { "\"${it.key}\":${it.value}" }
			.joinToString(",", "{", "}")
	}

	private fun parseInt(s: String, n: Int): NodeInt {
		var i = -1
		for (j in n until s.length) {
			if (!s[j].isDigit() && s[j] != '-') {
				i = j
				break
			}
		}
		val x = if (i == -1) s.substring(n).toInt() else s.substring(n, i).toInt()
		return NodeInt(x)
	}

	private fun parseString(s: String, n: Int): NodeString {
		val i = s.indexOf('"', n + 1)
		return NodeString(s.substring(n + 1, i))
	}

	private fun parseArray(s: String, n: Int): NodeArray {
		val x = mutableListOf<Node>()
		var i = n + 1
		while (s[i] != ']') {
			val a = parse(s, i)
			x.add(a)
			i += a.toString().length
			while (s[i] == ',') i++
		}
		return NodeArray(x)
	}

	private fun parseObject(s: String, n: Int): NodeObject {
		val x = mutableMapOf<String, Node>()
		var i = n + 1
		while (s[i] != '}') {
			val k = parseString(s, i)
			i += k.toString().length
			while (s[i] == ':') i++
			val v = parse(s, i)
			x[k.x] = v
			i += v.toString().length
			while (s[i] == ',') i++
		}
		return NodeObject(x)
	}

	fun parse(s: String, n: Int): Node {
		return when (s[n]) {
			'"' -> parseString(s, n)
			'[' -> parseArray(s, n)
			'{' -> parseObject(s, n)
			else -> parseInt(s, n)
		}
	}
}

fun main() {
	fun part1(input: String): Int {
		var result = 0
		var num = ""
		for (c in input) {
			if (c.isDigit() || c == '-') {
				num += c
			} else if (num.isNotEmpty()) {
				result += num.toInt()
				num = ""
			}
		}
		return result
	}

	fun part2(input: String): Int {
		val node = Day12().parse(input, 0)
		if (node.isRed()) {
			return 0
		}
		node.removeRed()
		return part1(node.toString())
	}

	val testInput = readInput("Day12_test")
	check(part1(testInput[0]) == 6)
	check(part1(testInput[1]) == 6)
	check(part1(testInput[2]) == 3)
	check(part1(testInput[3]) == 3)
	check(part1(testInput[4]) == 0)
	check(part1(testInput[5]) == 0)
	check(part1(testInput[6]) == 0)
	check(part1(testInput[7]) == 0)
	check(part2(testInput[0]) == 6)
	check(part2(testInput[8]) == 4)
	check(part2(testInput[9]) == 0)
	check(part2(testInput[10]) == 6)

	val input = readFile("Day12")
	part1(input).println()
	part2(input).println()
}
