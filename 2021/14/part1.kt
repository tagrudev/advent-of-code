import java.io.File

fun lines(): List<String> {
  return File("src/main/kotlin/input.txt").readText().trim().split("\n\n\n").first().lines()
}

var rules = mutableListOf<Pair<String, String>>()
var pairs = lines().takeLastWhile { it.isNotBlank() }

fun main() {
  pairs.forEach { rules.add(it.split(" -> ").last() to it.split(" -> ").first()) }
  var temp = returnPolymer(lines().first(), 0).groupingBy { it }.eachCount()

  println(temp.maxByOrNull { it.value }!!.value - temp.minByOrNull { it.value }!!.value)
}

tailrec fun returnPolymer(polymer: String, count: Int) : String{
  if (count == 10) {
    return polymer
  }

  var polymerToPairs = mutableListOf<String>()
  polymer.toList().forEachIndexed { index, value ->
    if (index < polymer.length - 1) { polymerToPairs.add(value.toString() + polymer.toList()[index+1]) }
  }

  var polymerPairsWithAdjacent = mutableListOf<String>()

  polymerToPairs.forEachIndexed { index, pair ->
    var a = rules.first { it.second == pair }

    if (index == 0) {
      polymerPairsWithAdjacent.add("${pair.first()}${a.first}${pair.last()}")
    } else {
      polymerPairsWithAdjacent.add("${a.first}${pair.last()}")
    }
  }

  return returnPolymer(polymerPairsWithAdjacent.joinToString(""), count + 1)
}
