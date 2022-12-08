import java.io.File

fun readLines(source: String): List<String> =
  File("src/main/resources/${source.lowercase()}.txt").readText().split("\n\n\n").first().lines()

val matrix = readLines("example").map { it.trim().toCharArray().map { char -> char.digitToInt() } }
val listOfPoints = mutableListOf<Point>()

class Point(val y: Int, val x: Int, val value: Int) {
  private val top get() = if (y > 0) listOfPoints.first { it.y == y - 1 && it.x == x } else null
  private val left get() = if (x > 0) listOfPoints.first { it.y == y && it.x == x - 1 } else null
  private val right get() = if (x < matrix.first().size - 1) listOfPoints.first { it.y == y && it.x == x + 1 } else null
  private val bottom get() = if (y < matrix.size - 1) listOfPoints.first { it.y == y + 1 && it.x == x } else null

  private var score = 0

  fun visible(from: String, initial: Int? = null): Boolean {
    val side = getSide(from) ?: return true

    if (initial != null && side.value < initial) return side.visible(from, initial)
    if (initial == null && side.value < value) return side.visible(from, value)

    return false
  }

  fun totalScore(): Int {
    return scoreFor("top") * scoreFor("bottom") * scoreFor("left") * scoreFor("right")
  }

  private fun getSide(name: String): Point? {
    return when(name) {
      "top" -> top
      "bottom" -> bottom
      "right" -> right
      else -> left
    }
  }

  private fun scoreFor(side: String): Int {
    score = 0
    calculateSideScore(side, this)
    return score
  }

  private fun calculateSideScore(from: String, point: Point, initial: Int? = null) {
    val side = getSide(from) ?: return

    if (initial != null) {
      if (side.value < initial) {
        point.score += 1
        side.calculateSideScore(from, point, initial)
      } else point.score += 1
    } else {
      if (side.value < value) {
        point.score += 1
        side.calculateSideScore(from, point, value)
      } else point.score += 1
    }
  }
}

fun main() {
  matrix.forEachIndexed y@{ y, line -> line.forEachIndexed x@{ x, value -> listOfPoints.add(Point(y, x, value)) } }
  // part 1
  println(listOfPoints.count { it.visible("top") || it.visible("bottom") || it.visible("right") || it.visible("left") })

  // part 2
  println(listOfPoints.maxOf { it.totalScore() })
}
