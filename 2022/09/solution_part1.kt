import java.io.File

fun readLines(source: String): List<String> =
  File("src/main/resources/${source.lowercase()}.txt").readText().split("\n\n\n").first().lines()

class Point(var x: Int, var y: Int) {
  fun touches(point: Point): Boolean {
    if(x == point.x && y == point.y) return true // they overlap
    if(adjecents().find { it.first == point.x && it.second == point.y } != null) return true

    return false
  }

  private fun adjecents(): List<Pair<Int, Int>> {
    return listOf(
      Pair(x-1, y),
      Pair(x+1, y),
      Pair(x, y-1),
      Pair(x, y+1),
      Pair(x-1, y+1),
      Pair(x+1, y+1),
      Pair(x-1, y-1),
      Pair(x+1, y-1),
      )
  }

  override fun toString(): String = "[$x-$y]"
}

class Rope() {
  val headMovement = mutableListOf(Point(0, 0))
  val tailMovement = mutableListOf(Point(0, 0))

  fun move(direction: String, times: Int) {
    repeat(times) {
      addTouchPoint(headMovement, direction)

      if(!headMovement.last().touches(tailMovement.last())) {
        val lastPosition = headMovement[headMovement.lastIndex - 1]
        tailMovement.add(Point(lastPosition.x, lastPosition.y))
      }
    }
  }

  private fun addTouchPoint(list: MutableList<Point>, direction: String) {
    val lastPoint = list.last()

    when(direction) {
      "R" -> list.add(Point(lastPoint.x + 1, lastPoint.y))
      "L" -> list.add(Point(lastPoint.x - 1, lastPoint.y))
      "U" -> list.add(Point(lastPoint.x, lastPoint.y + 1))
      "D" -> list.add(Point(lastPoint.x, lastPoint.y - 1))
    }

  }
}

fun main() {
  val lines = readLines("input")
  val rope = Rope()

  // Part 1
  lines.forEach { rope.move(it.split(" ").first(), it.split(" ").last().toInt()) }
  println(rope.tailMovement.map { it.toString() }.distinct().count())

}
