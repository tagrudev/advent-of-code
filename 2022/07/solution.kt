import java.io.File

fun readLines(source: String): List<String> =
  File("src/main/resources/${source.lowercase()}.txt").readText().split("\n\n\n").first().lines()

const val maxSize = 70_000_000
const val requiredSize = 30_000_000

class Directory(private val name: String, val parent: Directory?) {
  val subDirectories = mutableListOf<Directory>()
  val files = mutableListOf<Int>()

  fun size(): Int = subDirectories.sumOf { it.size() } + files.sum()
  fun findDir(name: String): Directory? = subDirectories.firstOrNull { it.name == name }
}

fun main() {
  val lines = readLines("input")
  val directories = mutableListOf<Directory>()
  var currentDirectory: Directory? = null

  lines.forEach { command ->
    when {
      command.startsWith("dir") || command.startsWith("$ ls") -> {}
      command == "$ cd .." -> {
        currentDirectory =  if (currentDirectory != null) currentDirectory!!.parent else Directory("/", null)
      }
      command.startsWith("$ cd") -> {
        val dirName = command.split(" ").last()
        currentDirectory?.findDir(dirName).also {
          currentDirectory = if (it == null) {
            val dir = Directory(dirName, currentDirectory).also { directories.add(it) }
            currentDirectory?.subDirectories?.add(dir)
            dir
          } else {
            it
          }
        }
      }

      else -> currentDirectory?.files?.add(command.split(" ").first().toInt())
    }
  }

  println("-------- Part 1")
  println(directories.map { it.size() }.filter { it < 100_000 }.sum())

  println("-------- Part 2")
  val rootSize = directories.first().size()
  val freeSpace = maxSize - rootSize
  val requiredSpace = requiredSize - freeSpace
  val allDirsSorted = directories.map { it.size() }.sorted()
  println("Current Free space: $freeSpace")
  println("Required Free space: $requiredSpace")
  println("Last Free space: ${allDirsSorted[allDirsSorted.takeWhile { it < requiredSpace }.size]}")
}
