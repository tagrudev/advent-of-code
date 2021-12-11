import java.io.File

fun lines(): List<String> {
  return File("src/main/kotlin/input.txt").readText().trim().split("\n\n\n").first().lines()
}

// 1 -> 2
// 4 -> 4
// 7 -> 3
// 8 -> 7

fun main() {
  var a = lines().map { it.split("|").last().trim() }.joinToString().replace(",", "").split(" ")

  println(a.groupingBy { it.length }.eachCount().toList().sumOf { gosho(it) })
}

fun gosho(number: Pair<Int, Int>) : Int {
  return if(number.first == 2 || number.first == 4 || number.first == 3 || number.first == 7) {
    number.second
  } else {
    0
  }
}
