import kotlin.math.min

fun main() {
	class Reindeer(val name: String, val speed: Int, val fly: Int, val rest: Int) {
		var points = 0

		fun distance(seconds: Int): Int {
			val n = seconds / (fly + rest)
			val rs = seconds % (fly + rest)
			return (fly * speed) * n + min(rs, fly) * speed
		}
	}

	fun parse(input: List<String>): List<Reindeer> {
		val result = mutableListOf<Reindeer>()
		for (line in input) {
			val (name, speed, fly, rest) =
				Regex("(\\w+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds.")
				.matchEntire(line)!!.groupValues.takeLast(4)
			result.add(Reindeer(name, speed.toInt(), fly.toInt(), rest.toInt()))
		}
		return result
	}

	fun part1(input: List<String>, seconds: Int): Int {
		val reindeerList = parse(input)
		return reindeerList.maxOf { it.distance(seconds) }
	}

	fun part2(input: List<String>, seconds: Int): Int {
		val reindeerList = parse(input)
		for (t in 1..seconds) {
			val max = reindeerList.maxOf { it.distance(t) }
			reindeerList.filter { it.distance(t) == max }.forEach { it.points++ }
		}
		return reindeerList.maxOf { it.points }
	}

	val testInput = readInput("Day14_test")
	check(part1(testInput, 1000) == 1120)
	check(part2(testInput, 1000) == 689)

	val input = readInput("Day14")
	part1(input, 2503).println()
	part2(input, 2503).println()
}
