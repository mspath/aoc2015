package day21

data class Player(val name: String, var hitPoints: Int, var damage: Int, var armor: Int)

data class Item(val name: String, val cost: Int, val damage: Int, val armor: Int)

val weapons = listOf(Item("Dagger", 8, 4, 0),
    Item("Shortsword", 10, 5, 0),
    Item("Warhammer", 25, 6, 0),
    Item("Longsword", 40, 7, 0),
    Item("Greataxe", 74, 8, 0))

val armor = listOf(Item("Leather", 13, 0, 1),
    Item("Chainmail", 31, 0, 2),
    Item("Splintmail", 53, 0, 3),
    Item("Bandedmail", 75, 0, 4),
    Item("Platemail", 102, 0, 5))

val rings = listOf(Item("Damage +1", 25, 1, 0),
    Item("Damage +2", 50, 2, 0),
    Item("Damage +3", 100, 3, 0),
    Item("Defense +1", 20, 0, 1),
    Item("Defense +2", 40, 0, 2),
    Item("Defense +3", 80, 0, 3))

fun main() {
    // as per instructions
    val player = Player("player", 100, 0, 0)
    val boss = Player("boss", 104, 8, 1)
    breakfast(player, boss)
}

fun Player.attack(other: Player) {
    val damage = kotlin.math.max(this.damage - other.armor, 1)
    other.hitPoints -= damage
}

// here we equip the player with items to win the battle for as little gold as possible
fun Player.prepare() {

    // rules:
    // (1) 1 weapon
    // (2) 0-1 armors
    // (3) 0-2 rings

    println("purchasing a greataxe for 74")
    this.damage += 8
    println("purchasing a platemail for 102")
    this.armor += 5
}

// this will run a battle starting with (first) player and return the winner
fun battle(player: Player, boss: Player): Player {
    var playerAttacking = true
    while (player.hitPoints > 0 && boss.hitPoints > 0) {
        playerAttacking = when (playerAttacking) {
            true -> {
                player.attack(boss)
                false
            }
            false -> {
                boss.attack(player)
                true
            }
        }
    }
    return if (player.hitPoints > 0) player else boss
}



fun breakfast(player: Player, boss: Player) {
    player.prepare()
    val winner = battle(player, boss)
    println(winner)
}