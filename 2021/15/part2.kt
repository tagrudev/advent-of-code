import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

fun lines(): List<String> = File("src/main/kotlin/input.txt").readLines()
val matrix = lines().map {
  it.trim().toCharArray().map {
      char -> char.digitToInt()
  }
}
val listOfPoints = mutableListOf<Point>()

class Point(var y: Int, var x: Int, var value: Int, var distance: Int = Int.MAX_VALUE) {
  private val top get() = if (y > 0) listOfPoints.first { it.y == y - 1 && it.x == x } else null
  private val left get() = if (x > 0) listOfPoints.first { it.y == y && it.x == x - 1 } else null
  private val right get() = if (x < matrix.first().size * 5 - 1) listOfPoints.first { it.y == y && it.x == x + 1 } else null
  private val bottom get() = if (y < matrix.size * 5 - 1) listOfPoints.first { it.y == y + 1 && it.x == x } else null

  fun adjacentSidePoints(): List<Point> {
    return listOfNotNull(left, right, top, bottom)
  }

  override fun toString(): String {
    return "y: $y, x: $x, value: $value"
  }
}

@OptIn(ExperimentalTime::class)
fun main() = measureTime {
  repeat(5) { lineIndex ->
    matrix.forEachIndexed y@{ y, line ->
      repeat(5) { columnIndex ->
        line.forEachIndexed x@{ x, number ->
          var newNumber =  if (columnIndex > 0) {
            if (number + columnIndex > 9) (number + columnIndex).toString().map { it.toString().toInt() }.sum() else number + columnIndex
          } else {
            number
          }

          if (lineIndex > 0) {
            newNumber = if (newNumber + lineIndex > 9) (newNumber + lineIndex).toString().map { it.toString().toInt() }.sum() else newNumber + lineIndex
          }

          listOfPoints.add(Point(matrix.size * lineIndex + y, line.size * columnIndex + x, newNumber))
        }
      }
    }
  }


  // https://brilliant.org/wiki/dijkstras-short-path-finder/
  // Dijkstra’s algorithm finds a shortest path tree from a single source node,
  // by building a set of nodes that have minimum distance from the source.
  val queue = ArrayDeque<Point>()
  val start = listOfPoints.first()
  val end = listOfPoints.last()
  start.distance = 0 // Set the distance of the start to be 0
  queue.add(listOfPoints.first()) // Add it to the queue

  while (!queue.isEmpty()) {
    val point = queue.minByOrNull { it.distance }!!
    point.adjacentSidePoints().forEach {
      val distance = point.distance + it.value
      if (distance < it.distance) {
        it.distance = distance
        queue.add(it)
      }
    }
    queue.remove(point)
  }

  println(end.distance)
}.let { println(it) }
