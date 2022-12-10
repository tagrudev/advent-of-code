import java.io.File
import kotlin.math.sign

fun readLines(source: String): List<String> =
  File("src/main/resources/${source.lowercase()}.txt").readText().split("\n\n\n").first().lines()

enum class Direction { TOP, BOTTOM, RIGHT, LEFT }

data class Point(var x: Int, var y: Int) {
  companion object {
    fun moveTail(head: Point, tail: Point): Point =
      if (!head.nearbyPoints().contains(tail)) {
        Point(tail.x + (head.x - tail.x).sign, tail.y + (head.y - tail.y).sign)
      } else tail
  }

  fun move(direction: Direction) = when (direction) {
    Direction.RIGHT -> Point(x + 1, y)
    Direction.LEFT -> Point(x - 1, y)
    Direction.TOP -> Point(x, y - 1)
    Direction.BOTTOM -> Point(x, y + 1)
  }

  fun nearbyPoints(): List<Point> {
    return listOf(
      Point(x-1, y),
      Point(x+1, y),
      Point(x, y-1),
      Point(x, y+1),
      Point(x-1, y-1),
      Point(x-1, y+1),
      Point(x+1, y-1),
      Point(x+1, y+1)
    )
  }

  override fun toString(): String = "$x+$y"
}

fun main() {
  val lines = readLines("input")
  val knots = MutableList(10) { Point(0, 0) }
  val visited = mutableSetOf(Point(0, 0))

  lines.forEach { line ->
    val direction: Direction = when (line.split(" ").first()) {
      "U" -> Direction.TOP
      "L" -> Direction.LEFT
      "R" -> Direction.RIGHT
      "D" -> Direction.BOTTOM
      else -> error("Sorry")
    }

    repeat(line.substringAfter(" ").toInt()) {
      knots[0] = knots[0].move(direction)

      knots.drop(1).indices.forEach { index ->
        knots[index + 1] = Point.moveTail(knots[index], knots[index+1])
      }
      visited += knots.last()
    }
  }

  println(visited.map { it.toString() }.distinct().size)
}

