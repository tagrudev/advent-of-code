import java.io.File

fun lines(): List<String> {
  return File("src/main/kotlin/input.txt").readText().trim().split("\n\n\n").first().lines()
}

val matrix = lines().map { it.trim().toCharArray().map { char -> char.digitToInt() } }

fun main() {
  var lowPoints = mutableListOf<Point>()
  var summary = mutableListOf<Int>()

  matrix.forEachIndexed y@{ y, line ->
    line.forEachIndexed x@{ x, number ->
      var up = if (y > 0) matrix[y-1][x] else Int.MAX_VALUE
      var down = if (y < matrix.size - 1) matrix[y+1][x] else Int.MAX_VALUE
      var left = if (x > 0) matrix[y][x-1] else Int.MAX_VALUE
      var right = if (x < matrix.first().size - 1) matrix[y][x+1] else Int.MAX_VALUE
      if (number >= up || number >= down || number >= left || number >= right) return@x

      lowPoints.add(Point(x, y, number))
    }
  }

  lowPoints.forEach {
    var tempChildren = mutableListOf<Point>()
    findChildren(it, tempChildren)
    summary.add(tempChildren.distinctBy { it.toString() }.size + 1)
  }

  println(summary.sorted().takeLast(3).reduce { acc, i -> acc * i })
}

class Point(private val x: Int, private val y: Int, val value: Int) {
  override fun toString(): String {
    return "[$x, $y] -> value: $value"
  }

  fun closestSmallPoints(): MutableList<Point> {
    var temp = mutableListOf<Point>()

    var up = if (y > 0) matrix[y-1][x] else null
    var down = if (y < matrix.size - 1) matrix[y+1][x] else null
    var left = if (x > 0) matrix[y][x-1] else null
    var right = if (x < matrix.first().size - 1) matrix[y][x+1] else null

    if (up != null && up > value && up != 9) { temp.add(Point(x, y-1, up)) }
    if (down != null && down > value && down != 9) { temp.add(Point(x, y+1, down)) }
    if (left != null && left > value && left != 9) { temp.add(Point(x-1, y, left)) }
    if (right != null && right > value && right != 9) { temp.add(Point(x+1, y, right)) }

    return temp
  }
}

tailrec fun findChildren(point: Point, pointzz: MutableList<Point>) {
  point.closestSmallPoints().forEach {
    pointzz.add(it)

    if (it.closestSmallPoints().isNotEmpty()) {
      findChildren(it, pointzz)
    }
  }
}
