import java.io.File

fun lines(): List<String> {
  return File("src/main/kotlin/example.txt").readText().trim().split("\n\n\n").first().lines()
}

fun main() {
  println(lines().sumOf { line ->
    var line = line.split("|")
    var numberSet = line.first().split(" ").filterNot { it.isEmpty() }
    var lineOneExtraction = line.last().split(" ").filterNot { it.isEmpty() }
    var numbersMap = mutableMapOf<Int, String>()

    numbersMap[1] = numberSet.first { it.length == 2 }
    numbersMap[4] = numberSet.first { it.length == 4 }
    numbersMap[7] = numberSet.first { it.length == 3 }
    numbersMap[8] = numberSet.first { it.length == 7 }
    numbersMap[9] = numberSet.first { it.length == 6 && stringContains(it, numbersMap[4]!!) }
    numbersMap[0] = numberSet.first { it.length == 6 && it != numbersMap[9] && stringContains(it, numbersMap[1]!!) }
    numbersMap[6] = numberSet.first { it.length == 6 && it != numbersMap[9] && it != numbersMap[0] }
    numbersMap[3] = numberSet.first { it.length == 5 && stringContains(it, numbersMap[1]!!) }
    numbersMap[5] = numberSet.first { it.length == 5 && it != numbersMap[3] && stringContains(numbersMap[9]!!, it) }
    numbersMap[2] = numberSet.first { it.length == 5 && it != numbersMap[5] && it != numbersMap[3] }

    lineOneExtraction.map { number ->
      numbersMap.filter { it.value.toCharArray().sorted() == number.toCharArray().sorted() }.keys.first()
    }.joinToString("").toInt()
  })
}

fun stringContains(string1: String, string2: String) : Boolean {
  return string1.toCharArray().sorted().containsAll(string2.toCharArray().toList())
}
