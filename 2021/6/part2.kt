import java.io.File

fun lines(): List<String> {
  return File("src/main/kotlin/input.txt").readText().trim().split("\n\n\n").first().lines()
}

fun main() {
  var input = lines().first().split(',').map { it.toInt() }.groupingBy { it }.eachCount()
    .mapValues { it.value.toLong() }

  for (i in 1..256) {
    val temp = mutableMapOf<Int, Long>()
    temp[8] = input[0] ?: 0
    temp[7] = input[8] ?: 0
    temp[6] = (input[7] ?: 0) + (input[0] ?: 0)
    temp[5] = input[6] ?: 0
    temp[4] = input[5] ?: 0
    temp[3] = input[4] ?: 0
    temp[2] = input[3] ?: 0
    temp[1] = input[2] ?: 0
    temp[0] = input[1] ?: 0
    input = temp
  }

  println(input.values.sumOf { it })
}
