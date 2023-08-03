import kotlin.math.max

fun main() {
	class Item(val name: String, val cost: Int, val damage: Int, val armor: Int)
	class Character(val hit: Int, val damage: Int, val armor: Int) {
		constructor(items: List<Item>): this(100, items.sumOf { it.damage }, items.sumOf { it.armor })
	}

	val weapons = listOf(
		Item("Dagger", 8, 4, 0),
		Item("Shortsword", 10, 5, 0),
		Item("Warhammer", 25, 6, 0),
		Item("Longsword", 40, 7, 0),
		Item("Greataxe", 74, 8, 0),
	)
	val armors = listOf(
		Item("Leather", 13, 0, 1),
		Item("Chainmail", 31, 0, 2),
		Item("Splintmail", 53, 0, 3),
		Item("Bandedmail", 75, 0, 4),
		Item("Platemail", 102, 0, 5),
	)
	val rings = listOf(
		Item("Damage +1", 25, 1, 0),
		Item("Damage +2", 50, 2, 0),
		Item("Damage +3", 100, 3, 0),
		Item("Defense +1", 20, 0, 1),
		Item("Defense +2", 40, 0, 2),
		Item("Defense +3", 80, 0, 3)
	)
	val shop = weapons + armors + rings

	fun itemSets(gold: Int): List<List<Item>> {
		// Weapon: 1
		// Armor: 0 - 1
		// Ring: 0 - 2
		if (gold == 0) return emptyList()
		val weaponsToBuy = weapons.filter { it.cost <= gold }
		if (weaponsToBuy.isEmpty()) return emptyList()
		val sets = mutableListOf<List<Item>>()
		for (weapon in weaponsToBuy) {
			sets.add(listOf(weapon))
			val g1 = gold - weapon.cost
			val armorToBuy = armors.filter { it.cost <= g1 }
			for (armor in armorToBuy) {
				sets.add(listOf(weapon, armor))
				val g2 = g1 - armor.cost
				val rings1ToBuy = rings.filter { it.cost <= g2 }
				for (ring1 in rings1ToBuy) {
					sets.add(listOf(weapon, armor, ring1))
					val g3 = g2 - ring1.cost
					val rings2ToBuy = rings.filter { it.cost <= g3 && it !== ring1 }
					for (ring2 in rings2ToBuy) {
						sets.add(listOf(weapon, armor, ring1, ring2))
					}
				}
			}
			val rings1ToBuy = rings.filter { it.cost <= g1 }
			for (ring1 in rings1ToBuy) {
				sets.add(listOf(weapon, ring1))
				val g2 = g1 - ring1.cost
				val rings2ToBuy = rings.filter { it.cost <= g2 && it !== ring1 }
				for (ring2 in rings2ToBuy) {
					sets.add(listOf(weapon, ring1, ring2))
				}
			}
		}
		return sets
	}

	fun divRoundUp(num: Int, divisor: Int): Int {
		return (num + divisor - 1) / divisor
	}

	fun isWin(player: Character, boss: Character): Boolean {
		val damageToBoss = max(player.damage - boss.armor, 1)
		val damageToPlayer = max(boss.damage - player.armor, 1)
		val bossDeathTurn = divRoundUp(boss.hit, damageToBoss)
		val playerDeathTurn = divRoundUp(player.hit, damageToPlayer)
		return bossDeathTurn <= playerDeathTurn
	}

	fun parseInput(input: List<String>) = Character(
		input[0].substringAfter("Hit Points: ").toInt(),
		input[1].substringAfter("Damage: ").toInt(),
		input[2].substringAfter("Armor: ").toInt())

	fun part1(input: List<String>): Int {
		val boss = parseInput(input)
		val maxGold = shop.sumOf { it.cost }
		for (gold in 0..maxGold) {
			for (items in itemSets(gold)) {
				val player = Character(items)
				if (isWin(player, boss)) return gold
			}
		}
		return -1
	}

	fun part2(input: List<String>): Int {
		val boss = parseInput(input)
		val maxGold = shop.sumOf { it.cost }
		val sets = itemSets(maxGold).sortedBy { it.sumOf { item -> item.cost } }.reversed()
		for (items in sets) {
			val player = Character(items)
			if (!isWin(player, boss)) return items.sumOf { it.cost }
		}
		return -1
	}

	val input = readInput("Day21")
	part1(input).println()
	part2(input).println()
}
