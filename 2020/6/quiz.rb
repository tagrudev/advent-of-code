contents = File.read("inputs.txt")

class Answer
  def initialize(input)
    @input = input.split("\n")
  end

  def correct
    @input.chars.uniq.size
  end

  def something
    if @input.size == 1
      @input.first.chars.uniq
    else
      @input.join.chars
    end
  end

  def group_correct
    if @input.size == 1
      @input.first.chars.uniq.size
    else
      @input.map { |z| z.chars }.reduce(:&).size
    end
  end
end

uniq_correct_answers = []

contents.split(/\n{2,}/).each do |data|
  uniq_correct_answers << Answer.new(data).group_correct
end

puts uniq_correct_answers.sum


