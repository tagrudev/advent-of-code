output = []

# part 1
numbers.each_with_index do |first, index|
  numbers[index+1..numbers.size].each do |second|
    if first + second == 2020
      output << [first, second]
    end
  end
end

# part 2
numbers.each_with_index do |first, index|
  numbers[index+1..numbers.size].each do |second|
    numbers[index+2..numbers.size].each do |third|
      if (first + second + third) == 2020
        output << [first, second, third]
      end
    end
  end
end
