val lines = readLines("input")

val letters = ('a'..'z').toMutableList().mapIndexed { index, value ->  Pair(value, index) }

val matrix = lines.map { it.trim().toList() }
val listOfPoints = mutableListOf<Point>()

class Point(var y: Int, var x: Int, var value: Int, var name: String, var visited: Boolean = false) {
  var distance = 0

  private val top get() = if (y > 0) listOfPoints.first { it.y == y - 1 && it.x == x } else null
  private val left get() = if (x > 0) listOfPoints.first { it.y == y && it.x == x - 1 } else null
  private val right get() = if (x < matrix.first().size - 1) listOfPoints.first { it.y == y && it.x == x + 1 } else null
  private val bottom get() = if (y < matrix.size - 1) listOfPoints.first { it.y == y + 1 && it.x == x } else null

  fun adjacentSidePoints(): List<Point> {
    return listOfNotNull(left, right, top, bottom)
  }

  fun isOuterPoint(): Boolean {
    return top == null || bottom == null || left == null || right == null
  }

  override fun toString(): String {
    return "y: $y, x: $x, value: $value, name: $name"
  }
}

fun main() {
  matrix.forEachIndexed y@{ y, line ->
    line.forEachIndexed x@{ x, letter ->
      val elevation =
        if(letters.firstOrNull { it.first == letter } != null) {
          letters.firstOrNull { it.first == letter }?.second
        } else if (letter == 'S') {
          letters.first().second
        } else {
          letters.last().second
        }

      listOfPoints.add(
        Point(y, x, elevation!!.toInt(), letter.toString())
      )
    }
  }

  val paths = mutableListOf<Int>()

  listOfPoints.filter { it.isOuterPoint() && it.value == 0 }.map {
    val deque = ArrayDeque<Point>()
    val start = it
    val end = listOfPoints.first { it.name == "E"}
    start.visited = true

    deque.add(start)

    while(deque.isNotEmpty()) {
      val point = deque.minByOrNull { it.distance }!!

      point.adjacentSidePoints().forEach { child ->
        if(child.value in 0.. point.value + 1 && !child.visited) {
          child.visited = true
          child.distance = point.distance + 1
          deque.add(child)
        }
      }

      deque.remove(point)
    }

    paths.add(end.distance)
    listOfPoints.map {
      it.distance = 0
      it.visited = false
    }
  }

  println(paths.filter { it != 0 }.min())
}
