import java.io.File

fun lines(): List<String> {
  return File("src/main/kotlin/input.txt").readText().trim().split("\n\n\n").first().lines()
}

fun getLowPoints() : MutableList<Point> {
  val lines = lines().map { it.trim().toCharArray().map { char -> char.digitToInt() } }
  var lowPoints = mutableListOf<Point>()

  lines.forEachIndexed y@{ y, line ->
    line.forEachIndexed x@{ x, number ->
      var up = if (y > 0) lines[y-1][x] else Int.MAX_VALUE
      var down = if (y < lines.size - 1) lines[y+1][x] else Int.MAX_VALUE
      var left = if (x > 0) lines[y][x-1] else Int.MAX_VALUE
      var right = if (x < lines.first().size - 1) lines[y][x+1] else Int.MAX_VALUE
      if (number >= up || number >= down || number >= left || number >= right) return@x
      lowPoints.add(Point(x, y, number))
    }
  }

  return lowPoints
}

fun main() {
  println(getLowPoints().sumOf { it.value + 1 })
}
