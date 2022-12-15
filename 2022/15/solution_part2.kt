import java.lang.Math.abs

class Sensor(val sensor: Point, val beacon: Point) {
  val distance = sensor.manhattanDistanceTo(beacon)
  fun covers(x: Int, y: Int): Boolean = sensor.manhattanDistanceTo(Point(x, y)) <= distance
}

fun scannerFor(maxRange: Int, sensors: List<Sensor>): Point? {
  for (x in 0..maxRange) {
    var y = 0
    while (y <= maxRange) {
      val sensor = sensors.find { it.covers(x, y) } ?: return Point(x, y)
      y = sensor.sensor.y + sensor.distance - abs(x - sensor.sensor.x) + 1
    }
  }
  return null
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

  val max = 4_000_000
  var point = scannerFor(4_000_000, sensors)

  if (point != null) {
    val x = point.x.toLong()
    val y = point.y.toLong()
    println(x * max.toLong() + y)
  }
}

