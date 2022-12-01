fun solve() {
  val elves = sampleInput.split(Regex("\n\n")).map { it.split("\n").sumOf { it.toInt() } }

  println("solution 1: ${elves.maxBy { it }}")
  println("solution 2: ${elves.sortedByDescending { it }.take(3).sum()}")
}
