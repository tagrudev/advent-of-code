contents = File.read("tree")
# contents = File.read("example")
paths = Kernel.eval(contents)

# Part 1
# non_trees = 0
# trees = 0

# paths.each_with_index do |path, index|
#   new_path = path * 6000

#   if index == 0
#     if new_path[0] == '.'
#       non_trees += 1
#     else
#       trees += 1
#     end
#   else
#     if new_path[index * 3] == '.'
#       non_trees += 1
#     else
#       trees += 1
#     end
#   end
# end

# Part 2
trees_arr = []

[[1, 1], [3, 1], [5, 1], [7, 1], [1, 2]].each do |variation|
  trees = 0
  non_trees = 0
  new_index = 0

  paths.each_with_index do |path, index|
    new_path = path * 26000

    if index != 0
      if variation.last != 1
        if (index % 2) == 0
          new_index += 1

          if new_path[new_index] == '.'
            non_trees += 1
          else
            trees += 1
          end
        end
      else
        if new_path[index * variation.first] == '.'
          non_trees += 1
        else
          trees += 1
        end
      end
    end
  end

  trees_arr << trees
end

puts trees_arr
puts trees_arr.reduce(:*)





