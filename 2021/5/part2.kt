import java.io.File
import kotlin.math.abs

fun lines(): List<String> {
  return File("src/main/kotlin/input.txt").readText().trim().split("\n\n\n").first().lines()
}

open class Point(val x: Int, val y: Int) {
  override fun toString() = "$x, $y"
  fun name() = "$x--$y"
}

class Point1(val x1: Int, val y1: Int) : Point(x1, y1)
class Point2(val x2: Int, val y2: Int) : Point(x2, y2)

fun main() {
  val lines = lines().map {
    var point1 = it.split(" -> ").first().split(",")
    var point2 = it.split(" -> ").last().split(",")
    Point1(point1.first().toInt(), point1.last().toInt()) to Point2(point2.first().toInt(), point2.last().toInt())
  }

  val coverPoints =
    lines.map {
      if (it.first.name() == it.second.name()) {
        listOf(it.first as Point) // Same point
      } else if (it.first.x1 == it.second.x2) {
        var sequence =
          if (it.first.y1 < it.second.y2) {
            generateSequence(Point(it.first.x1, it.first.y1)) { point -> Point(point.x, point.y + 1) }
          } else {
            generateSequence(Point(it.first.x1, it.first.y1)) { point -> Point(point.x, point.y - 1) }
          }

        sequence.take(abs(it.first.y1 - it.second.y2) + 1).toList()
      } else if (it.first.y1 == it.second.y2) {
        var sequence =
          if (it.first.x1 < it.second.x2) {
            generateSequence(Point(it.first.x1, it.first.y1)) { point -> Point(point.x + 1, point.y) }
          } else {
            generateSequence(Point(it.first.x1, it.first.y1)) { point -> Point(point.x - 1, point.y) }
          }

        sequence.take(abs(it.first.x1 - it.second.x2) + 1).toList()
      } else {
        var sequence =
          if (it.first.x1 < it.second.x2 && it.first.y1 < it.second.y2) {
            generateSequence(Point(it.first.x1, it.first.y1)) { point -> Point(point.x + 1, point.y + 1) }
          } else if (it.first.x1 < it.second.x2 && it.first.y1 > it.second.y2) {
            generateSequence(Point(it.first.x1, it.first.y1)) { point -> Point(point.x + 1, point.y - 1) }
          } else if (it.first.x1 > it.second.x2 && it.first.y1 < it.second.y2) {
            generateSequence(Point(it.first.x1, it.first.y1)) { point -> Point(point.x - 1, point.y + 1) }
          } else {
            generateSequence(Point(it.first.x1, it.first.y1)) { point -> Point(point.x - 1, point.y - 1) }
          }

        sequence.take(abs(it.first.x1 - it.second.x2) + 1).toList()
      }
    }

  println(coverPoints.flatten().map { it.name() }.groupingBy { it }.eachCount().filter { it.value > 1 }.size)
}


