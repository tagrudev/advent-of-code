import kotlinx.serialization.json.*

private fun String.asJsonArray(): JsonArray = Json.decodeFromString(JsonArray.serializer(), this)

private fun cmp(left: JsonElement, right: JsonElement): Int? {
  return if (left is JsonPrimitive && right is JsonPrimitive) {
    (left.int - right.int).takeUnless { it == 0 }
  } else if (left is JsonArray && right is JsonArray) {
    repeat(maxOf(left.size, right.size)) { index ->
      val leftValue = left.getOrNull(index)
      val rightValue = right.getOrNull(index)
      if (leftValue == null) return Int.MIN_VALUE
      else if (rightValue == null) return Int.MAX_VALUE
      else {
        val childInOrder = cmp(leftValue, rightValue)
        if (childInOrder != null) return childInOrder
      }
    }
    null
  } else if (left is JsonArray && right is JsonPrimitive) cmp(left, JsonArray(listOf(right)))
  else if (left is JsonPrimitive && right is JsonArray) cmp(JsonArray(listOf(left)), right)
  else error("Sori motori!")
}

fun main() {
  val lines = readLines("input")

  val part1 = lines.chunked(3).mapIndexed { index, cmds ->
    val (left, right) = cmds.take(2).map { it.asJsonArray() }
    if (cmp(left, right)!! <= 0) index + 1 else 0
  }.sum()

  println(part1)

  val distressPackages = listOf("[[2]]", "[[6]]").map { it.asJsonArray() }
  val inputPackages = lines.filter(String::isNotBlank).map { it.asJsonArray() }
  val sortedPackages = (distressPackages + inputPackages).sortedWith { a, b -> cmp(a, b)!! }
  val part2 = distressPackages.map { sortedPackages.indexOf(it) + 1 }.reduce(Int::times)

  println(part2)
}
