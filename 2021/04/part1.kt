import java.io.File

class Board(var initialInput: MutableList<MutableList<Int>>) {
  fun possibleOptions(): MutableList<MutableList<Int>> {
    var temp = initialInput.toMutableList()

    repeat(5) { item ->
      temp.add(initialInput.map { it[item] }.toMutableList())
    }

    return temp
  }
}

class PossibleNumbers(var input: String) {
  fun numbers(): List<Int> {
    return input.split(",").map { it.trim().toInt() }
  }
}

fun main() {
  var possibleNumbers =
    PossibleNumbers(
      input = File("/Users/tagrudev/Projects/Personal/advent_of_code/2021/4/numbers2.txt").inputStream().readBytes()
        .toString(Charsets.UTF_8)
    )
  var possibleBoards = ArrayList<Board>()
  var inputs = mutableListOf<String>()
  File("/Users/tagrudev/Projects/Personal/advent_of_code/2021/4/boards2.txt").forEachLine {
    inputs.add(it)
  }

  inputs.filter { it.isNotEmpty() }.chunked(5).forEach {
    possibleBoards.add(Board(it.map { it.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toMutableList() }
      .toMutableList()))
  }

  run lit@{
    possibleNumbers.numbers().forEach { number ->

      possibleBoards.map { it.possibleOptions() }.toMutableList().forEach { board ->
        board.forEach { option ->
          option.remove(number)

          if (option.isEmpty()) {
            println(board.take(5).map { it.sum() }.sum() * number)
          }
        }
      }
    }
  }
}

