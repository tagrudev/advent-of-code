import java.io.File

fun lines(): List<String> {
  return File("src/main/kotlin/example.txt").readText().trim().split("\n\n\n").first().lines()
}

fun main() {
  var lines = lines().map { it.toCharArray().toMutableList() }
  var openBrackets = "([{<".toCharArray()
  var openedBrackets = mutableListOf<Char>()

  lines.forEach a@{
    var line = mutableListOf<Char>()
    it.forEach { c ->
      if (c in openBrackets) line.add(c) && return@forEach

      when (c) {
        "}".single() -> if (line.last() != "{".single()) openedBrackets.add(c) && return@a else line.removeLast()
        "]".single() ->if (line.last() != "[".single()) openedBrackets.add(c) && return@a else line.removeLast()
        ">".single() -> if (line.last() != "<".single()) openedBrackets.add(c) && return@a else line.removeLast()
        ")".single() -> if (line.last() != "(".single()) openedBrackets.add(c) && return@a else line.removeLast()
      }
    }
  }

  var sum = 0
  openedBrackets.groupingBy { it }.eachCount().forEach {
    when (it.key) {
      ")".single() -> sum += it.value * 3
      "]".single() -> sum += it.value * 57
      "}".single() -> sum += it.value * 1197
      ">".single() -> sum += it.value * 25137
    }
  }
  println(sum)
}
