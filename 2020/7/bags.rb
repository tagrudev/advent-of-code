contents = File.read("inputs.txt")

class Bag
  attr_accessor :size, :color

  def initialize(size, color)
    @size  = size
    @color = color
  end
end

class Rule
  def initialize(sentence)
    @sentence = sentence.split('contain')
  end

  def bag_color
    Bag.new 1, @sentence.first.gsub(' bags', '').strip
  end

  def containing_bag_colors
    if @sentence.last == ' no other bags.'
      0
    else
      @sentence.last.split(',').map do |z|
        Bag.new z[1], z[3..z.length].chomp(' bags.').chomp(' bags').chomp(' bag').chomp(' bag.')
      end
    end
  end
end

class Recursive
  attr_accessor :bags_of_gold

  def initialize(pairs)
    @pairs = pairs
    @bags_of_gold = 0
  end

  def execute(bag)
    @pairs.each do |pair|
      next if pair.last == 0
      next unless bag.color == pair.first.color

      pair.last.each do |t|
        t.size.to_i.times do |index|
          @bags_of_gold += 1

          execute(t)
        end
      end
    end
  end
end

pairs = []

contents.split(/\n/).each do |z|
  l = Rule.new(z)

  pairs << [l.bag_color,  l.containing_bag_colors]
end

q = Bag.new 1, 'shiny gold'

r = Recursive.new pairs
r.execute(q)

puts r.bags_of_gold


# class Something
#   def initialize(pairs)
#     @pairs = pairs
#     @bags_of_gold = []
#     @summary = 0
#   end

#   def bagss
#     @bags_of_gold
#   end

#   def recursive_bitch(info)
#     @pairs.each do |pair|
#       next if pair.last.nil?

#       if pair.last.include?(info)
#         @bags_of_gold << pair.first

#         recursive_bitch(pair.first)
#       end
#     end
#   end

#   def another_recursive_bitch(info)
#     @pairs.each do |pair|
#       next unless pair.first == info.color
#       next if pair.last == nil

#       pair.last.each do |z|
#         @bags_of_gold << z

#         another_recursive_bitch(z)
#       end
#     end
#   end
# end




