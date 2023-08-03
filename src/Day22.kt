import kotlin.math.max

fun main() {
	data class Boss(var hit: Int, val damage: Int)

	data class Player(var hit: Int, var mana: Int, var armor: Int)

	open class Spell(val mana: Int, var timer: Int = 0) {
		open fun cast(player: Player, boss: Boss) {
			player.mana -= mana
		}

		open fun effect(player: Player, boss: Boss) {
			timer--
		}

		fun isActive() = timer > 0
	}

	class MagicMissile: Spell(53) {
		override fun cast(player: Player, boss: Boss) {
			super.cast(player, boss)
			boss.hit -= 4
		}
	}

	class Drain: Spell(73) {
		override fun cast(player: Player, boss: Boss) {
			super.cast(player, boss)
			boss.hit -= 2
			player.hit += 2
		}
	}

	class Shield: Spell(113) {
		override fun cast(player: Player, boss: Boss) {
			super.cast(player, boss)
			player.armor = 7
			timer = 6
		}

		override fun effect(player: Player, boss: Boss) {
			super.effect(player, boss)
			if (timer == 0) player.armor = 0
		}
	}

	class Poison: Spell(173) {
		override fun cast(player: Player, boss: Boss) {
			super.cast(player, boss)
			timer = 6
		}

		override fun effect(player: Player, boss: Boss) {
			boss.hit -= 3
			super.effect(player, boss)
		}
	}

	class Recharge: Spell(229) {
		override fun cast(player: Player, boss: Boss) {
			super.cast(player, boss)
			timer = 5
		}

		override fun effect(player: Player, boss: Boss) {
			player.mana += 101
			super.effect(player, boss)
		}
	}

	val allSpells = listOf(MagicMissile(), Drain(), Shield(), Poison(), Recharge())

	fun doEffects(player: Player, boss: Boss) {
		for (s in allSpells) if (s.isActive()) s.effect(player, boss)
	}

	fun parseInput(input: List<String>) = Boss(
		input[0].substringAfter("Hit Points: ").toInt(),
		input[1].substringAfter("Damage: ").toInt())

	class State(val playerHit: Int, val playerMana: Int, val playerArmor: Int, val bossHit: Int,
				val shieldTimer: Int, val poisonTimer: Int, val rechargeTimer: Int)

	fun saveState(player: Player, boss: Boss) = State(player.hit, player.mana, player.armor, boss.hit,
		allSpells[2].timer, allSpells[3].timer, allSpells[4].timer)

	fun loadState(state: State, player: Player, boss: Boss) {
		player.hit = state.playerHit
		player.mana = state.playerMana
		player.armor = state.playerArmor
		boss.hit = state.bossHit
		allSpells[2].timer = state.shieldTimer
		allSpells[3].timer = state.poisonTimer
		allSpells[4].timer = state.rechargeTimer
	}

	var minSpend = Int.MAX_VALUE

	fun recurs(player: Player, boss: Boss, spend: Int, hard: Boolean) {
		if (spend > minSpend) return
		// Player turn
		if (hard) {
			player.hit--
			if (player.hit <= 0) return
		}
		doEffects(player, boss)
		if (boss.hit <= 0) {
			if (spend < minSpend) minSpend = spend
			return
		}
		for (spell in allSpells) {
			if (player.mana < spell.mana) return
			if (spell.isActive()) continue
			val state = saveState(player, boss)
			spell.cast(player, boss)
			// Boss turn
			doEffects(player, boss)
			if (boss.hit <= 0) {
				if (spend + spell.mana < minSpend) minSpend = spend + spell.mana
				return
			}
			val damage = max(boss.damage - player.armor, 1)
			player.hit -= damage
			if (player.hit <= 0) return
			recurs(player, boss, spend + spell.mana, hard)
			loadState(state, player, boss)
		}
	}

	fun part1(input: List<String>): Int {
		val boss = parseInput(input)
		val player = Player(50, 500, 0)
		for (spell in allSpells) spell.timer = 0
		minSpend = Int.MAX_VALUE
		recurs(player, boss, 0, false)
		return minSpend
	}

	fun part2(input: List<String>): Int {
		val boss = parseInput(input)
		val player = Player(50, 500, 0)
		for (spell in allSpells) spell.timer = 0
		minSpend = Int.MAX_VALUE
		recurs(player, boss, 0, true)
		return minSpend
	}

	val input = readInput("Day22")
	part1(input).println()
	part2(input).println()
}
