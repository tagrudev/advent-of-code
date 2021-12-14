import java.io.File

fun lines(): List<String> {
  return File("src/main/kotlin/input.txt").readText().trim().split("\n\n\n").first().lines()
}

class Box(val length: Int, val width: Int, val height: Int) {
  override fun toString(): String = "(${length}x${width}x${height})"
  fun ribbon() : Int {
    return length*height*width + listOf(length, width, height).sorted().take(2).reduce { acc, i ->  (acc + i)*2 }
  }
}

fun main() {
  val boxes = mutableListOf<Box>()

  lines().forEach {
    var line = it.split("x")
    boxes.add(Box(line.first().toInt(), line[1].toInt(), line.last().toInt()))
  }

  println(boxes.sumOf { it.ribbon() })
}
