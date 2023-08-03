import java.util.Collections.synchronizedList
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

fun main() {
	fun presents(house: Int): Int {
		var presents = 0
		for (elf in 1..house) {
			if (house % elf == 0) presents += elf * 10
		}
		return presents
	}

	fun part1Slow(input: List<String>): Int {
		val minPresents = input[0].toInt()
		for (house in 1 until Int.MAX_VALUE) {
			if (presents(house) >= minPresents) return house
		}
		return -1
	}

	fun executeScheduler(): ThreadPoolExecutor {
		val nThreads = Runtime.getRuntime().availableProcessors()
		val taskQueue = ArrayBlockingQueue<Runnable>(nThreads * 2)
		val scheduler = ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, taskQueue)
		scheduler.prestartAllCoreThreads()
		return scheduler
	}

	fun part1(input: List<String>): Int {
		val minPresents = input[0].toInt()
		val scheduler = executeScheduler()
		val result = synchronizedList(mutableListOf<Int>())
		for (house in 1 until Int.MAX_VALUE) {
			scheduler.queue.put {
				if (presents(house) >= minPresents) result.add(house)
			}
			if (result.isNotEmpty()) break
		}
		scheduler.shutdown()
		return result.min()
	}

	fun presents2(house: Int): Int {
		var presents = 0
		for (elf in 1..house) {
			if (elf * 50 < house) continue
			if (house % elf == 0) presents += elf * 11
		}
		return presents
	}

	fun part2(input: List<String>): Int {
		val minPresents = input[0].toInt()
		val scheduler = executeScheduler()
		val result = synchronizedList(mutableListOf<Int>())
		for (house in 1 until Int.MAX_VALUE) {
			scheduler.queue.put {
				if (presents2(house) >= minPresents) result.add(house)
			}
			if (result.isNotEmpty()) break
		}
		scheduler.shutdown()
		return result.min()
	}

	val testInput = readInput("Day20_test")
	check(part1(testInput) == 4)
	check(part2(testInput) == 4)

	val input = readInput("Day20")
	part1(input).println()
	part2(input).println()
}
