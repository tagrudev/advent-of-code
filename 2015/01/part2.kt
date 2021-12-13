import java.io.File

fun lines(): List<String> {
  return File("src/main/kotlin/input.txt").readText().trim().split("\n\n\n").first().lines()
}

fun main() {
  var counter = 0

  run block@ {
    lines().first().toCharArray().forEachIndexed { index, bracket ->
      if (bracket.toString() == "(") { counter += 1 } else { counter -= 1 }

      if (counter == -1) {
        println(index + 1)
        return@block
      }
    }
  }
}
