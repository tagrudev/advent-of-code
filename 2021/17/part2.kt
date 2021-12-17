import utils.challenge

data class Point(var x: Int, var y: Int) {
  fun within(gtx: Int, ltx: Int, bty: Int, lty: Int) : Boolean = this.x in gtx..ltx && this.y in bty..lty
}

fun main() {
  challenge { lines ->
    val ranges = lines.first().replace("target area: ", "").split(",").map { it.trim() }
    var bruteForcePoints = mutableListOf<Point>()
    for (i in -200 until 200) { for (j in -200 until 200) { bruteForcePoints.add(Point(j, i)) } }

    val gtx = ranges.first().replace("x=", "").split("..").first().toInt()
    val ltx = ranges.first().replace("x=", "").split("..").last().toInt()
    val gty = ranges.last().replace("y=", "").split("..").first().toInt()
    val lty = ranges.last().replace("y=", "").split("..").last().toInt()

    var count = 0
    bruteForcePoints.forEach { bruteForcePoint ->
      if (generateTrajectoryStepsFor(bruteForcePoint).take(250).any { it.within(gtx, ltx, gty, lty) }) {
        count += 1
      }
    }

    println(count)
  }
}

fun generateTrajectoryStepsFor(point: Point) : Sequence<Point> {
  var startX = point.x
  var startY = point.y

  var trajectorySteps = generateSequence(point) {
    startY -= 1
    if(startX > 0) {
      startX -= 1
    } else if (startX < 0) {
      startX += 1
    } else {
      startX
    }

    var x = it.x + startX
    var y = it.y + startY

    Point(x, y)
  }

  return trajectorySteps
}
