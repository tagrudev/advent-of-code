import java.io.File

fun lines(): List<String> {
  return File("src/main/kotlin/input.txt").readText().trim().split("\n\n\n").first().lines()
}

var lines = lines().map { it.toCharArray().toMutableList() }
var openBrackets = "([{<".toCharArray()
var mapOpenCloseBrackets = mapOf(
  "[".single() to "]".single(),
  "{".single() to "}".single(),
  "<".single() to ">".single(),
  "(".single() to ")".single()
)

fun main() {
  val sum = mutableListOf<Long>()

  getIncompleteReversedLines().forEach { reversedLine ->
    var total = 0.toLong()
    reversedLine.forEach { char ->
      var currentValue = 0
      when (char) {
        ")".single() -> currentValue = 1
        "]".single() -> currentValue = 2
        "}".single() -> currentValue = 3
        ">".single() -> currentValue = 4
      }

      total = total * 5 + currentValue
    }
    sum.add(total)
  }

  println(sum.sorted()[sum.size/2])
}

fun getIncompleteReversedLines() : MutableList<List<Char>> {
  var incompleteReversedLines = mutableListOf<List<Char>>()
  lines.forEach index@{
    var tempLine = mutableListOf<Char>()

    it.forEach { char ->
      if (char in openBrackets) {
        tempLine.add(char)
      } else {
        when (char) {
          "}".single() -> if (tempLine.last() != "{".single()) { return@index } else { tempLine.removeLast() }
          "]".single() -> if (tempLine.last() != "[".single()) { return@index } else { tempLine.removeLast() }
          ">".single() -> if (tempLine.last() != "<".single()) { return@index } else { tempLine.removeLast() }
          ")".single() -> if (tempLine.last() != "(".single()) { return@index } else { tempLine.removeLast() }
        }
      }
    }

    incompleteReversedLines.add(tempLine.reversed().map { char -> mapOpenCloseBrackets[char]!! })
  }

  return incompleteReversedLines
}
