import java.io.File

fun main() {
  var inputs = mutableListOf<String>()

  File("/Users/tagrudev/Projects/Personal/advent_of_code/2021/3/input.txt").forEachLine {
    inputs.add(it)
  }

  var gammaRate = ""
  var epsilonRate = ""

  repeat(12) { index ->
    var max = inputs.map { it[index] }.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
    var min = inputs.map { it[index] }.groupingBy { it }.eachCount().minByOrNull { it.value }?.key
    gammaRate = gammaRate.plus(max)
    epsilonRate = epsilonRate.plus(min)
  }

  println("Product ${gammaRate.toInt(2) * epsilonRate.toLong(2)}")
}
