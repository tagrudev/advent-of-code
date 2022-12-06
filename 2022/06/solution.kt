import java.io.File

fun readLines(source: String): List<String> =
  File("src/main/resources/${source.lowercase()}.txt").readText().split("\n\n\n").first().lines()

fun main() {
  val lines = readLines("input")
  val messages = mutableListOf<Char>()

  run breaking@{
    lines.first().toCharArray().forEach { letter ->
      if (messages.size > 4 && messages.takeLast(14).distinctBy { it }.size == 14) {
        println("The size is ${messages.size}")
        return@breaking
      }
      messages.add(letter)
    }
  }
}
