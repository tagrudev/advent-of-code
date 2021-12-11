import java.io.File

fun lines(): List<String> {
  return File("src/main/kotlin/example.txt").readText().trim().split("\n\n\n").first().lines()
}

fun main() {
  println(recursive(lines().first().split(",").map { it.toInt() }.toMutableList(), 80).size)
}

fun recursive(jellys: MutableList<Int>, times: Int) : MutableList<Int> {
  if(times == 0) { return jellys }

  val iterator = jellys.listIterator()
  while (iterator.hasNext()) {
    val item = iterator.next()
    if (item == 0) {
      iterator.set(6)
      iterator.add(8)
    } else {
      iterator.set(item-1)
    }
  }

  return recursive(jellys, times - 1)
}
