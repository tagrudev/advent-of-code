import java.io.File
import kotlin.math.abs

fun lines(): List<String> {
  return File("src/main/kotlin/input.txt").readText().trim().split("\n\n\n").first().lines()
}

fun main() {
  var input = lines().first().split(',').map { it.toInt() }.sorted()
  var list = mutableMapOf<Int, Int>()

  for(i in 0..input.maxOrNull()!!) {
    list[i] = input.sumOf { abs(it - i) }
  }

  println(list.toList().minByOrNull { it.second }!!.second)
}
