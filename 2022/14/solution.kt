import kotlin.math.max
import kotlin.math.min

data class Coordinate(val x: Int, val y: Int) {
  val bottom get() = copy(y = y + 1)
  val bottomLeft get() = copy(x = x - 1, y = y + 1)
  val bottomRight get() = copy(x = x + 1, y = y + 1)
}

fun main() {
  val lines = readLines("example").map { line ->
    line.split(" -> ").map { Coordinate(it.split(",")[0].toInt(), it.split(",")[1].toInt()) }
  }

  val maxX = lines.maxOf { it.maxOf { coordinate -> coordinate.x } }
  val maxY = lines.maxOf { it.maxOf { coordinate -> coordinate.y } }
  val grid = Array(maxY + 1) { CharArray(maxX + 1) { '.' } }

  lines.forEach {
    it.windowed(2) { (start, end) ->
      for (i in min(start.x, end.x) .. max(start.x, end.x)) {
        for (j in min(start.y, end.y) .. max(start.y, end.y)) {
          grid[j][i] = '#'
        }
      }
    }
  }

  fun move(coordinate: Coordinate) {
    val element = grid[coordinate.y + 1][coordinate.x]
    if (element == '.') move(coordinate.bottom)
    else if (grid[coordinate.y + 1][coordinate.x - 1] == '.') move(coordinate.bottomLeft)
    else if (grid[coordinate.y + 1][coordinate.x + 1] == '.') move(coordinate.bottomRight)
    else grid[coordinate.y][coordinate.x] = 'o'
  }

  val start = Coordinate(500, 0)

  while (true) {
    if (grid[start.y][start.x] != '.') break

    try { move(start) }
    catch (e: Exception) { break }
  }

  println(grid.flatMap { it.toList() }.count { it == 'o' })

// print the grid for fun
//  grid.forEach {
//    it.filterNot { it == null }.forEach {
//      print(it)
//    }
//    println()
//  }
}
