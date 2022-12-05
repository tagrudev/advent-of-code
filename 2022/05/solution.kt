import java.io.File

fun readLines(source: String): List<String> =
  File("src/main/resources/${source.lowercase()}.txt").readText().split("\n\n\n").first().lines()

fun main() {
  val lines = readLines("input2")
  val instructions = readLines("input")

  val all = mutableListOf<MutableList<String>>()
  lines.last().chunked(4).forEach { _ -> all.add(mutableListOf()) }

  lines.dropLast(1).forEach {
    val chunks = it.toList().chunked(4)
    chunks.forEachIndexed { index, chars ->
      val item = chars.toMutableList().filter { it.toString() != "[" && it.toString() != "]" && it.toString().isNotBlank() }
      if(item.isNotEmpty()) all[index].add(item.first().toString())
    }
  }
//  // first part with array deque
//  instructions.forEach { instruction ->
//    val step = instruction.split(" ").chunked(2).map { it.last().toInt() }
//    val count = step[0]
//    val from = step[1]
//    val to = step[2]
//
//    repeat(count) {
//      all[to-1].push(all[from-1].pop())
//    }
//  }

  instructions.forEach { instruction ->
    val step = instruction.split(" ").chunked(2).map { it.last().toInt() }
    val count = step[0]
    val from = step[1]
    val to = step[2]
    val temp = all[from-1].take(count)
    all[to-1].addAll(0, temp)
    repeat(count) { all[from-1].removeAt(0) }
  }

  println(all.joinToString("") { it.first() })
}
