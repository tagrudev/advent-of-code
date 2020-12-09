# Part 1
correct = 0
wrong = 0

inputs.each do |input|
  min_max, unformatted_letter, password = *input.split(' ')

  min, max = min_max.split('-')
  letter = unformatted_letter[0]
  length = password.scan(/#{letter}/).length

  unless length.between?(min.to_i, max.to_i)
    wrong += 1
  else
    correct += 1
  end
end

puts "Correct: #{correct}"
puts "Wrong: #{wrong}"

# Part 2
correct = 0
wrong = 0

inputs.each do |input|
  first_last, unformatted_letter, password = *input.split(' ')

  first, last = first_last.split('-')
  letter = unformatted_letter[0]

  if (password[first.to_i-1] == letter && password[last.to_i-1] != letter) || (password[first.to_i-1] != letter && password[last.to_i-1] == letter)
    correct += 1
  else
    wrong += 1
  end
end

puts "Correct: #{correct}"
puts "Wrong: #{wrong}"
