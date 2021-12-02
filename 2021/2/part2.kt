import java.io.File

fun main() {
  var inputs = mutableListOf<String>()

  File("/Users/tagrudev/Projects/Personal/advent_of_code/2021/2/input.txt").forEachLine {
    inputs.add(it)
  }

  var horizontal = 0
  var depth = 0
  var aim = 0

  inputs.forEachIndexed { index, item ->
    var direction = item.split(" ").first()
    var number = item.split(" ").last().toInt()

    when (direction) {
      "forward" -> {
        horizontal += number
        depth += aim * number
      }
      "down" -> {
        aim += number
      }
      else -> {
        aim -= number
      }
    }
  }

  println("Horizontal: $horizontal, depth: $depth product ${depth*horizontal}")
}
