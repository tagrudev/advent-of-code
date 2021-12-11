import java.io.File

fun main() {
  var inputs = mutableListOf<Int>()

  File("/Users/tagrudev/Projects/Personal/advent_of_code/2021/1/input.txt").forEachLine {
    inputs.add(it.toInt())
  }

  var increased = 0
  var decreased = 0
  var nochange = 0

  inputs.forEachIndexed { index, item ->
    if(index < inputs.size - 3) {
      var a = item + inputs[index+1] + inputs[index+2]
      var b = inputs[index+1] + inputs[index+2] + inputs[index+3]

      if( a > b) {
        decreased += 1
      } else if (a < b) {
        increased += 1
      } else {
        nochange += 1
      }
    }
  }

  println("Bigger items: $increased, Smaller items: $decreased, No change for: $nochange")
}
