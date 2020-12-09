contents = File.read("inputs.txt")

numbers = contents.split(/\n/).map(&:to_i)
step = 25
problematic = 0
answer = 41682220
possibilities = []

# Part 1
numbers.each_with_index do |number, index|
  next if index < step || numbers[index-step..index-1].combination(2).to_a.any? { |z| z.sum == number }

  problematic = number
  break
end

puts problematic

# Part 2
(3..numbers.size).each do |n|
  numbers.each_with_index do |number, index|
    sum = numbers[index..index+n]

    possibilities << sum if sum.sum == outlier
  end
end

puts possibilities.first.min + possibilities.first.max
