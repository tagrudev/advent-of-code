import java.io.File

fun readLines(source: String): List<String> =
  File("src/main/resources/${source.lowercase()}.txt").readText().split("\n\n\n").first().lines()

val monkeys1 = listOf(
  Monkey(0, 23, mutableListOf(79, 98), "* 19", Pair(2, 3)),
  Monkey(1, 19, mutableListOf(54, 65, 75, 74), "+ 6", Pair(2, 0)),
  Monkey(2, 13, mutableListOf(79, 60, 97), "* old", Pair(1, 3)),
  Monkey(3, 17, mutableListOf(74), "+ 3", Pair(0, 1)),
)

val monkeys = listOf(
  Monkey(0, 2, mutableListOf(84, 66, 62, 69, 88, 91, 91), "* 11", Pair(4, 7)),
  Monkey(1, 7, mutableListOf(98, 50, 76, 99), "* old", Pair(3, 6)),
  Monkey(2, 13, mutableListOf(72, 56, 94), "+ 1", Pair(4, 0)),
  Monkey(3, 3, mutableListOf(55, 88, 90, 77, 60, 67), "+ 2", Pair(6, 5)),
  Monkey(4, 19, mutableListOf(69, 72, 63, 60, 72, 52, 63, 78), "* 13", Pair(1, 7)),
  Monkey(5, 17, mutableListOf(89, 73), "+ 5", Pair(2, 0)),
  Monkey(6, 11, mutableListOf(78, 68, 98, 88, 66), "+ 6", Pair(2, 5)),
  Monkey(7, 5, mutableListOf(70), "+ 7", Pair(1, 3)),
)

class Monkey(
  val name: Int,
  val divisibleNumber: Long,
  var startingItems: MutableList<Long>,
  val operation: String,
  val nextTo: Pair<Int, Int>,
  var inspectedItems: Long = 0L) {

  fun operation(item: Long): Long =
    when(operation.substringBefore(" ")) {
      "+" -> item + operation.substringAfter(" ").toLong()
      "*" -> {
        val after = operation.substringAfter(" ")
        if (after == "old") item * item else item * after.toLong()
      }
      else -> throw error("You haven't parsed the operation correctly")
    } % (monkeys.map(Monkey::divisibleNumber).reduce(Long::times))

  fun play() {
    startingItems.forEach { item ->
      val worry = operation(item)
      val afterTest = Pair(if (worry % divisibleNumber == 0L) nextTo.first else nextTo.second, worry)

      monkeys.first { it.name == afterTest.first }.startingItems.add(afterTest.second)
      inspectedItems += 1
    }

    startingItems = mutableListOf()
  }
}

fun main() {
  repeat(10000) { round ->
    monkeys.forEach { monkey -> monkey.play() }
    println("Round ${round + 1}")
    println(monkeys.map { it.inspectedItems })
    println("Inspected Items: ${monkeys.map { it.inspectedItems }.sortedDescending().take(2).reduce { acc, i -> acc * i }}")
  }
}

