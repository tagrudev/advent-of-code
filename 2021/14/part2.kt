import java.io.File

fun lines(): List<String> {
  return File("src/main/kotlin/input.txt").readText().trim().split("\n\n\n").first().lines()
}

val rules = lines().takeLastWhile { it.isNotBlank() }.map { it.split(" -> ").first() to it.split(" -> ").last() }

class Polymer(var name: String, var size: Long = 0, val resultsTo: String) {
  override fun toString(): String = "($name, $size, $resultsTo)"
}

fun main() {
  var temp = rules.map { Polymer(it.first, size = 0, resultsTo = it.second) }

  lines().first().windowed(2).forEach { windowed ->
    temp.first { it.name == windowed }.size += 1
  }

  var mapped = findPolymers(temp.filter { it.size >= 1 }).groupBy({ it.name.first() }, { it.size })
    .map { it.key to it.value.sum() }

  var min = mapped.minByOrNull { it.second }!!
  var max = mapped.maxByOrNull { it.second }!!

  if (min.first == lines().first().last()) {
    println(max.second - min.second - 1)
  } else if (max.first == lines().first().last()) {
    println(max.second - min.second + 1)
  } else {
    println(max.second - min.second)
  }
}

fun findPolymers(polymers: List<Polymer>, count: Int = 0): List<Polymer> {
  if (count == 40) {
    return polymers
  }

  var temp = rules.map { Polymer(it.first, 0, it.second) }
  polymers.filter { it.size >= 1 }.forEach { polymer ->
    temp.first { it.name == polymer.name.first() + polymer.resultsTo }.size += polymer.size
    temp.first { it.name == polymer.resultsTo + polymer.name.last() }.size += polymer.size
  }

  return findPolymers(temp, count + 1)
}
