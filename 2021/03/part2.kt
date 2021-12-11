import java.io.File

fun main() {
  var inputs = mutableListOf<String>()

  File("/Users/tagrudev/Projects/Personal/advent_of_code/2021/3/input.txt").forEachLine {
    inputs.add(it)
  }

  var oxygen = recursiveFind(inputs, index = 0, filterChar = "1")
  var carbonDioxide = recursiveFind(inputs, index = 0, filterChar = "0")

  println(oxygen * carbonDioxide)
}

fun recursiveFind(inputs: MutableList<String>, index: Int, filterChar: String): Int {
  var grouped = inputs.map { it[index] }.groupingBy { it }.eachCount()

  // Hacky
  if (grouped.size == 1) {
    return inputs.first { it[index + 1] == filterChar.single() }.toInt(2)
  }

  var filteredInputs = if (grouped.values.first() == grouped.values.last()) {
    inputs.filter { it[index] == filterChar.single() }.toMutableList()
  } else {
    var char = if (filterChar == "1") {
      grouped.maxByOrNull { it.value }?.key!!
    } else {
      grouped.minByOrNull { it.value }?.key!!
    }

    inputs.filter { it[index] == char }.toMutableList()
  }

  return if (filteredInputs.size == 1) {
    filteredInputs.first().toInt(2)
  } else {
    recursiveFind(filteredInputs, index + 1, filterChar)
  }
}

