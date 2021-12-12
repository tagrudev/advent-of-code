import java.io.File

fun lines(): List<String> {
  return File("src/main/kotlin/input.txt").readText().trim().split("\n\n\n").first().lines()
}

val paths = lines().map { it.split("-") }

fun main() {
  println(paths)

  var allVisited = mutableListOf<List<String>>()
  recursive(allVisited = allVisited)
  println(allVisited.size)
}

tailrec fun recursive(current: String = "start", visited: List<String> = listOf(current), allVisited: MutableList<List<String>>) {
  if(current == "end") {
    allVisited.add(visited + current)
    return
  }

  var adjacentNodes = paths.filter { it.contains(current) }.map { it.first { it != current } }

  adjacentNodes.forEach {
    if (it.all(Char::isUpperCase)) {
      recursive(it, visited + it, allVisited)
    } else {
      if(!visited.contains(it)) {
        recursive(it, visited + it, allVisited)
      }
    }
  }
}
