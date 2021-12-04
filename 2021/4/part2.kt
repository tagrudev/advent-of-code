import java.io.File

class Board(var initialInput: MutableList<MutableList<Int>>) {
  fun possibleOptions(): MutableList<MutableList<Int>> {
    var something = initialInput.toMutableList()

    repeat(5) { item ->
      something.add(initialInput.map { it[item] }.toMutableList())
    }

    return something
  }
}

class PossibleNumbers(var input: String) {
  fun numbers(): List<Int> {
    return input.split(",").map { it.trim().toInt() }
  }
}

fun main() {
  var possibleNumbers = PossibleNumbers(
    input = File("/Users/tagrudev/Projects/Personal/advent_of_code/2021/4/numbers.txt").inputStream().readBytes()
      .toString(Charsets.UTF_8)
  )
  var possibleBoards = ArrayList<Board>()
  var inputs = mutableListOf<String>()
  File("/Users/tagrudev/Projects/Personal/advent_of_code/2021/4/boards.txt").forEachLine {
    inputs.add(it)
  }

  inputs.filter { it.isNotEmpty() }.chunked(5).forEach { items ->
    possibleBoards.add(Board(items.map { it.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toMutableList() }
      .toMutableList()))
  }

  var possibleBoardsInputs = possibleBoards.map { it.possibleOptions() }.toMutableList()
  var alreadyWinnerBoards = mutableListOf<Int>()

  run lit@ {
    possibleNumbers.numbers().forEach { number ->
      possibleBoardsInputs.forEachIndexed { index, board ->
        board.forEach { option ->
          if (alreadyWinnerBoards.contains(index)) return@forEach

          option.remove(number)

          if (option.isEmpty()) {
            alreadyWinnerBoards.add(index)
            println("Board: $board")
            println("Number: $number")
            println(board.take(5).sumOf { it.sum() } * number)
          }
        }
      }
    }
  }
}

