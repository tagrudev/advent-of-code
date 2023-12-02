input = File.read('input1.txt').split(/\n/)

class Game
  attr_reader :id

  class GameSet
    attr_reader :red, :green, :blue

    def initialize(collection)
      @collection = collection
      @red = 0
      @green = 0
      @blue = 0

      @collection.split(", ").map do |item|
        color = item.split(" ")
        case color.last
        when "red" then @red = color.first.to_i
        when "blue" then @blue = color.first.to_i
        when "green" then @green = color.first.to_i
        else ""
        end
      end
    end

    def possible?
      @red < 13 && @green < 14 && @blue < 15
    end
  end

  def initialize(line)
    @line = line
    @id = @line.split(":").first.split.last.to_i
  end

  def sets
    @line.split(": ").last.split("; ").map { GameSet.new _1 }
  end
end

games = input.map { Game.new(_1) }
# solution 1
puts games.select { _1.sets.all?(&:possible?) }.sum(&:id)
#solution 2
puts games.map { _1.sets.map(&:green).max * _1.sets.map(&:blue).max * _1.sets.map(&:red).max }.sum
