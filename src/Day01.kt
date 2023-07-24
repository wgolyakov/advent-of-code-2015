fun main() {
    @Suppress("USELESS_CAST")
    fun part1(input: String) = input.sumOf { if (it == '(') 1 else -1 as Int }

    fun part2(input: String): Int {
        var floor = 0
        for ((i, d) in input.withIndex()) {
            floor += if (d == '(') 1 else -1
            if (floor == -1) return i + 1
        }
        return -1
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput[0]) == 0)
    check(part1(testInput[1]) == 0)
    check(part1(testInput[2]) == 3)
    check(part1(testInput[3]) == 3)
    check(part1(testInput[4]) == 3)
    check(part1(testInput[5]) == -1)
    check(part1(testInput[6]) == -1)
    check(part1(testInput[7]) == -3)
    check(part1(testInput[8]) == -3)
    check(part2(testInput[9]) == 1)
    check(part2(testInput[10]) == 5)

    val input = readFile("Day01")
    part1(input).println()
    part2(input).println()
}
