import java.io.File

fun readLines(source: String): List<String> =
  File("src/main/resources/${source.lowercase()}.txt").readText().trim().split("\n\n\n").first().lines()

val smallLetters = ('a'..'z').toMutableList().map { it.toString() }
val capitalLetters = ('A'..'Z').toMutableList().map { it.toString() }
val targets = (smallLetters + capitalLetters).mapIndexed { index: Int, s: String -> s to index + 1 }.toMap()

fun getScore(input: String): Int {
  val inputHalf = input.chunked(input.toCharArray().size / 2)
  val partOne = inputHalf.first().toCharArray().toSet()
  val partTwo = inputHalf.last().toCharArray().toSet()
  val commonLetter = partOne.intersect(partTwo.toSet()).first().toString()

  return targets[commonLetter]!!.toInt()
}

fun getScore(input: List<String>): Int {
  val partOne = input[0].toSet().intersect(input[1].toSet()).joinToString("")
  val partTwo = input[1].toSet().intersect(input[2].toSet()).joinToString("")
  return targets[partOne.toSet().intersect(partTwo.toSet()).first().toString()]!!.toInt()
}

fun main() {
  val lines = readLines("input")
  println(lines.sumOf { getScore(it) })
  println(lines.chunked(3).map { getScore(it) }.sumOf { it })
}
