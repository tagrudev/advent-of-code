import java.io.File

fun readLines(source: String): List<String> =
  File("src/main/resources/${source.lowercase()}.txt").readText().split("\n\n\n").first().lines()

class CPURegister() {
  var cycles = 0
  var x = 1
  var allValues = mutableListOf(0)

  fun input(cmd: String) {
    if (cmd.startsWith("addx")) addx(cmd.substringAfter("addx ").toInt()) else noop()
  }

  private fun addx(number: Int) {
    incrementCycle()
    incrementCycle()
    x += number
  }

  private fun noop() {
    incrementCycle()
  }

  var line1 = mutableListOf("")
  var line2 = mutableListOf("")
  var line3 = mutableListOf("")
  var line4 = mutableListOf("")
  var line5 = mutableListOf("")
  var line6 = mutableListOf("")

  private fun drawSprite(line: MutableList<String>, minus: Int = 0) {
    if((x-1..x+1).contains(cycles - minus)) line.add("⚪️") else line.add("⚫️")
  }

  private fun draw() {
    if ((0..39).contains(cycles)) drawSprite(line1)
    else if ((40..79).contains(cycles)) drawSprite(line2, 40)
    if ((80..119).contains(cycles)) drawSprite(line3, 80)
    if ((120..159).contains(cycles)) drawSprite(line4, 120)
    if ((160..199).contains(cycles)) drawSprite(line5, 160)
    if ((200..239).contains(cycles)) drawSprite(line6, 200)
  }

  private fun incrementCycle() {
    draw()
    cycles += 1
  }
}
fun main() {
  val lines = readLines("input")
  val cpuRegister = CPURegister()

  lines.forEach { line ->
    cpuRegister.input(line)
  }

  println("Cycles: ${cpuRegister.cycles} and X is: ${cpuRegister.x}")
  println("All sum: ${cpuRegister.allValues.sum()}")
  println(cpuRegister.line1.joinToString(""))
  println(cpuRegister.line2.joinToString(""))
  println(cpuRegister.line3.joinToString(""))
  println(cpuRegister.line4.joinToString(""))
  println(cpuRegister.line5.joinToString(""))
  println(cpuRegister.line6.joinToString(""))
}

