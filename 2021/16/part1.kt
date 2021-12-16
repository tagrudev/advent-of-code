import utils.challenge

fun Char.toBinary(): String {
  return when (this.toString()) {
    "0" -> "0000"
    "1" -> "0001"
    "2" -> "0010"
    "3" -> "0011"
    "4" -> "0100"
    "5" -> "0101"
    "6" -> "0110"
    "7" -> "0111"
    "8" -> "1000"
    "9" -> "1001"
    "A" -> "1010"
    "B" -> "1011"
    "C" -> "1100"
    "D" -> "1101"
    "E" -> "1110"
    "F" -> "1111"
    else -> throw Throwable("Char to Binary failed for: $this")
  }
}

sealed class Packet(open val version: Long, open val typeId: Int, open val length: Long) {}
data class LiteralPacket(
  override var version: Long,
  override var typeId: Int = 4,
  var data: Long,
  override val length: Long
) : Packet(version, typeId, length)

data class OperatorPacket(
  override var version: Long,
  override var typeId: Int,
  var lengthId: Int,
  override val length: Long,
  var subpackages: List<Packet>
) : Packet(version, typeId, length) {
}

fun main() {
  challenge { lines ->
    var code = lines.first().map { it.toBinary() }.joinToString("")
    var packets = mutableListOf<Packet>()
    parsePacket(code, packets)

    println(part1(packets.first()))
  }
}

fun part1(packet: Packet) : Long {
  return if (packet is LiteralPacket) {
    packet.version
  } else {
    (packet as OperatorPacket).subpackages.sumOf { part1(it) } + packet.version
  }
}

fun parsePacket(code: String, initialPackets: MutableList<Packet>) {
  if (code.isEmpty() || code.all { it.toString() == "0" }) { return }

  var dropSize = 0
  var version = code.drop(dropSize).take(3).toLong(2); dropSize += 3
  var typeId = code.drop(dropSize).take(3).toInt(2); dropSize += 3

  if (typeId == 4) {
    initialPackets.add(parseLiteral(code.drop(dropSize), version, typeId))
  } else {
    var lengthId = code.drop(dropSize).take(1).toInt()
    if (lengthId == 0) {
      initialPackets.add(parseOperatorZero(code.drop(dropSize), version, typeId))
    } else {
      initialPackets.add(parseOperatorOne(code.drop(dropSize), version, typeId))
    }
  }
}

fun parseLiteral(code: String, version: Long, typeId: Int): LiteralPacket {
  if (typeId != 4) return throw Throwable("Type id for Literal should be 4")
  var length = 0
  var data = mutableListOf<String>()

  for (chunk in code.chunked(5)) {
    data.add(chunk.drop(1))
    length += 5
    if (chunk.first().toString() == "0") {
      break
    }
  }

  return LiteralPacket(version, typeId, data.joinToString("").toLong(2), length.toLong() + 6)
}

fun parseOperatorZero(code: String, version: Long, typeId: Int): OperatorPacket {
  var dropSize = 0
  var lengthId = code.drop(dropSize).take(1).toInt(); dropSize += 1
  var subPacketsLength = code.drop(dropSize).take(15).toInt(2); dropSize += 15
  var newCode = code.drop(dropSize).take(subPacketsLength)
  var subPackets = mutableListOf<Packet>()

  if (lengthId != 0) return throw Throwable("The length type is not 0 for Opeartor Zero")
  if (typeId == 4) return throw Throwable("Type id for Operator should not be 4")

  while (subPackets.sumOf { it.length } < subPacketsLength) {
    parsePacket(newCode.drop(subPackets.sumOf { it.length }.toInt()), subPackets)
  }

  return OperatorPacket(version, typeId, lengthId, subPackets.sumOf { it.length } + 22, subPackets)
}

fun parseOperatorOne(code: String, version: Long, typeId: Int) : OperatorPacket {
  var dropSize = 0
  var lengthId = code.drop(dropSize).take(1).toInt(); dropSize += 1
  var times = code.drop(dropSize).take(11).toInt(2); dropSize += 11
  var newCode = code.drop(dropSize)
  var subPackets = mutableListOf<Packet>()

  if (lengthId != 1) return throw Throwable("The length type is not 1 for the Operator One")
  if (typeId == 4) return throw Throwable("Type id for Operator should not be 4")

  repeat(times) { parsePacket(newCode.drop(subPackets.sumOf { it.length }.toInt()), subPackets) }

  return OperatorPacket(version, typeId, lengthId, subPackets.sumOf { it.length } + 18, subPackets)
}
