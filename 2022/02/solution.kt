import java.io.File

fun readLines(source: String): List<String> =
  File("src/main/resources/${source.lowercase()}.txt").readText().trim().split("\n\n\n").first().lines()

class Rochambeau(private val input: String) {
  fun fight(): Int =
    when (input) {
      "A X" -> 4
      "A Y" -> 8
      "A Z" -> 3
      "B X" -> 1
      "B Y" -> 5
      "B Z" -> 9
      "C X" -> 7
      "C Y" -> 2
      "C Z" -> 6
      else -> 0
    }

  fun fightAgain(): Int =
    when(input) {
      "A X" -> 3
      "A Y" -> 4
      "A Z" -> 8
      "B X" -> 1
      "B Y" -> 5
      "B Z" -> 9
      "C X" -> 2
      "C Y" -> 6
      "C Z" -> 7
      else -> 0
    }
}

fun main() {
  val lines = readLines("input")
  val outcomes = mutableListOf<Rochambeau>()
  lines.forEach { outcomes.add(Rochambeau(it))}

  println(outcomes.sumOf { it.fight() })
  println(outcomes.sumOf { it.fightAgain() })
}
