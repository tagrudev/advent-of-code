contents = File.read("inputs.txt")

class Command
  attr_accessor :index, :name, :value

  def initialize(index, name, value)
    @index = index
    @name  = name
    @value = value
  end
end

class Recursive
  attr_accessor :value, :executed_commands, :current_index

  def initialize(commands)
    @value = 0
    @executed_commands = []
    @commands = commands
    @current_index = 0
  end

  def execute(start_from)
    @current_index = start_from

    @commands.each_with_index do |command, index|
      next if @current_index != index

      break if @executed_commands.include? command

      log(command)

      if command.name == 'nop'
        @executed_commands = @executed_commands << command

        @current_index += 1
        next
      end

      if command.name == 'acc'
        @value += command.value
        @executed_commands = @executed_commands << command
        @current_index += 1
        next
      end

      if command.name == 'jmp'
        @executed_commands = @executed_commands << command
        @current_index = index + command.value

        execute(@current_index)
      end
    end
  end

  def log(command)
    puts "Command with index: #{command.index}, name: #{command.name}, value: #{command.value}"
  end
end

commands = []
contents.split(/\n/).each_with_index do |line, index|
  commands << Command.new(index, line.split(' ').first, line.split(' ').last.to_i)
end

# Part 1
# r = Recursive.new new_commands
# r.execute(0)
# puts r.value

commands.select { |c| c.name == 'nop' || c.name == 'jmp' }.each do |strange|
  new_commands = Marshal.load(Marshal.dump(commands))
  new_object = new_commands.find { |z| z.index == strange.index }
  new_object.name == 'nop' ?  new_object.name = 'jmp' : new_object.name = 'nop'

  r = Recursive.new new_commands
  r.execute(0)
  puts r.value

  break if r.executed_commands.last == new_commands.last
end
