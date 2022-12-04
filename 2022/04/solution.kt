import java.io.File

fun readLines(source: String): List<String> =
  File("src/main/resources/${source.lowercase()}.txt").readText().trim().split("\n\n\n").first().lines()

class Assignment(sectionOne: String, sectionTwo: String) {
  private val rangeOne = (sectionOne.split("-").first().toInt()..sectionOne.split("-").last().toInt()).toSet()
  private val rangeTwo = (sectionTwo.split("-").first().toInt()..sectionTwo.split("-").last().toInt()).toSet()
  private val intersectionSize = rangeOne.intersect(rangeTwo).size

  fun part1(): Int = if(intersectionSize == rangeOne.size || intersectionSize == rangeTwo.size) 1 else 0
  fun part2(): Int = if(intersectionSize > 0) 1 else 0
}

fun main() {
  val lines = readLines("input")

  println(lines.map { Assignment(it.split(",").first(), it.split(",").last()) }.sumOf { it.part1() })
  println(lines.map { Assignment(it.split(",").first(), it.split(",").last()) }.sumOf { it.part2() })
}
