import java.io.File
import kotlin.math.abs

fun lines(): List<String> {
  return File("src/main/kotlin/input.txt").readText().trim().split("\n\n\n").first().lines()
}

fun main() {
  var input = lines().first().split(',').map { it.toInt() }
  var list = mutableMapOf<Int, Int>()

  for(i in 0..input.maxOrNull()!!) {
    list[i] = input.sumOf { (1..abs(it - i)).sum() }
  }

  println(list.toList().minByOrNull { it.second }!!.second)
}
