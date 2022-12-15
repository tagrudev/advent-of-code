class Sensor(val sensor: Point, val beacon: Point) {
  private val distance = sensor.manhattanDistanceTo(beacon)
  fun covers(x: Int, y: Int): Boolean = sensor.manhattanDistanceTo(Point(x, y)) <= distance
}

fun main() {
  var sensors = mutableListOf<Sensor>()
  val lines = readLines("input")

  lines.forEach { line ->
    val sensor = line.split(":").first().split("x=").last().split(",")
    val beacon = line.split(":").last().split("x=").last().split(",")

    sensors.add(
      Sensor(
        Point(sensor.first().toInt(), sensor.last().substringAfter("y=").toInt()),
        Point(beacon.first().toInt(), beacon.last().substringAfter("y=").toInt())
      )
    )
  }

  val line = 2_000_000
  var covered = 0
  for (i in -line..line*3) { if (sensors.any{ it.covers(i, line) }) covered ++ }

  // part 1
  println(covered - 1)
}
