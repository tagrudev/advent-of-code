import java.io.File

fun lines(): List<String> {
  return File("src/main/kotlin/example.txt").readText().trim().split("\n\n\n").first().lines()
}

val matrix = lines().map { it.trim().toCharArray().map { char -> char.digitToInt() }.toMutableList() }.toMutableList()
var totalNumberOfFlashes = 0
val listOfPoints = mutableListOf<Point>()

class Point(var y: Int, var x: Int, var value: Int) {
  override fun toString(): String {
    return "y: $y, x: $x, value: $value"
  }

  fun increase() {
    if (value == 9) { value = 0 } else { value += 1 }
  }

  fun findAdjacentPoints() : MutableList<Point> {
    var topLeft = if (y > 0 && x > 0) listOfPoints.first { it.y == y - 1 && it.x == x - 1 } else null
    var top = if (y > 0) listOfPoints.first { it.y == y - 1 && it.x == x } else null
    var topRight = if (y > 0 && x < matrix.first().size - 1) listOfPoints.first { it.y == y - 1 && it.x == x + 1 } else null
    var left = if (x > 0) listOfPoints.first { it.y == y && it.x == x - 1 } else null
    var right = if (x < matrix.first().size - 1) listOfPoints.first { it.y == y && it.x == x + 1 } else null
    var bottomLeft = if (y < matrix.size - 1 && x > 0) listOfPoints.first { it.y == y + 1 && it.x == x - 1} else null
    var bottom = if (y < matrix.size - 1) listOfPoints.first { it.y == y + 1 && it.x == x } else null
    var bottomRight = if (y < matrix.size - 1 && x < matrix.first().size - 1) listOfPoints.first { it.y == y + 1 && it.x == x + 1 } else null

    return listOfNotNull(topLeft, top, topRight, left, right, bottomLeft, bottom, bottomRight).toMutableList()
  }
}

fun main() {
  matrix.forEachIndexed y@{ y, line -> line.forEachIndexed x@{ x, number -> listOfPoints.add(Point(y, x, number)) } }

  var countToZero = 0
  while(listOfPoints.sumOf { it.value } != 0) {
    countToZero += 1
    var stack = ArrayDeque<Point>()
    listOfPoints.forEach { point ->
      point.increase()
      if (point.value == 0) { totalNumberOfFlashes += 1; stack.add(point) }
    }

    while (stack.isNotEmpty()) {
      stack.first().findAdjacentPoints().forEach { point ->
        if (point.value != 0) {
          point.increase()
          if (point.value == 0) { totalNumberOfFlashes += 1;stack.add(point) }
        }
      }
      stack.removeFirst()
    }
  }

  println(countToZero)
}
