import java.io.File

fun lines(): List<String> {
  return File("src/main/kotlin/input.txt").readText().trim().split("\n\n\n").first().lines()
}

class Point(var y: Int, var x: Int) {
  override fun toString(): String = "($x, $y)"
  fun getFromFold(fold: String): Int = if(fold.split("=").first() == "y") y else x
  fun updateFromFold(direction: String, number: Int) { if (direction == "y") { y = number } else { x = number } }
}

val points = lines().takeWhile { it.isNotBlank() }.map { Point(it.split(",").last().toInt(), it.split(",").first().toInt()) }
val folds = lines().takeLastWhile { it.isNotBlank() }.map { it.replace("fold along ", "") }

fun main() {
  for (fold in folds) { foldPaper(fold) }

  repeat(points.maxOfOrNull { it.y }!!+1) { y ->
    println()
    repeat(points.maxOfOrNull { it.x }!!+1) { x ->
      if(points.any { it.x == x && it.y == y }) { print("█") } else { print(" ") }
    }
  }
}

fun foldPaper(fold: String) : Int {
  val max = points.maxOfOrNull { it.getFromFold(fold) }!!
  val foldNumber = fold.split("=").last().toInt()
  val foldDirection = fold.split("=").first()

  points.filter { it.getFromFold(fold) > foldNumber }.groupBy { it.getFromFold(fold) }.forEach {
    for((index, number) in (foldNumber+1..max).toList().withIndex()) {
      if (it.key == number) {
        it.value.forEach { point -> point.updateFromFold(foldDirection, 2 * foldNumber - (foldNumber+index+1)) }
      }
    }
  }

  return points.groupingBy { it.toString() }.eachCount().size
}
