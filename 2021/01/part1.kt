import java.io.File

fun main() {
  var inputs = mutableListOf<Int>()

  File("/Users/tagrudev/Projects/Personal/advent_of_code/2021/1/input.txt").forEachLine {
    inputs.add(it.toInt())
  }

  var increased = 0
  var decreased = 0
  var nochange = 0

  inputs.forEachIndexed { index, item->
    if(index > 0 && index < inputs.size) {
      if(item < inputs[index-1]) {
        decreased += 1
      } else if (item > inputs[index-1]) {
        increased += 1
      } else {
        nochange += 1
      }
    }
  }

  println("Bigger items: $increased, Smaller items: $decreased, No change for: $nochange")
}
