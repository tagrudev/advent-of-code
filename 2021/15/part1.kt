import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

fun lines(): List<String> = File("src/main/kotlin/input.txt").readLines()
val matrix = lines().map { it.trim().toCharArray().map { char -> char.digitToInt() } }
val listOfPoints = mutableListOf<Point>()

class Point(var y: Int, var x: Int, var value: Int, var distance: Int = Int.MAX_VALUE) {
  private val top get() = if (y > 0) listOfPoints.first { it.y == y - 1 && it.x == x } else null
  private val left get() = if (x > 0) listOfPoints.first { it.y == y && it.x == x - 1 } else null
  private val right get() = if (x < matrix.first().size - 1) listOfPoints.first { it.y == y && it.x == x + 1 } else null
  private val bottom get() = if (y < matrix.size - 1) listOfPoints.first { it.y == y + 1 && it.x == x } else null

  fun adjacentSidePoints(): List<Point> {
    return listOfNotNull(left, right, top, bottom)
  }

  override fun toString(): String {
    return "y: $y, x: $x, value: $value"
  }
}

@OptIn(ExperimentalTime::class)
fun main() = measureTime {
  matrix.forEachIndexed y@{ y, line -> line.forEachIndexed x@{ x, number -> listOfPoints.add(Point(y, x, number)) } }

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
