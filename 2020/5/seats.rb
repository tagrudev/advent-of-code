contents = File.read("inputs.txt")
seats = Kernel.eval(contents)

class PlaneSeat
  def initialize(encoded_number)
    @encoded_number = encoded_number
    @row_range = (0..127).to_a
    @column_range = (0..7).to_a
    @encoded_row = @encoded_number[0..6]
    @encoded_column = @encoded_number[7..9]
  end

  def decoded_number
    @encoded_row.chars.each do |char|
      if char == 'F'
        @row_range = @row_range.each_slice(@row_range.size / 2).to_a.first
      else
        @row_range = @row_range.each_slice(@row_range.size / 2).to_a.last
      end
    end

    row = @row_range.first

    @encoded_column.chars.each do |char|
      if char == 'L'
        @column_range = @column_range.each_slice(@column_range.size / 2).to_a.first
      else
        @column_range = @column_range.each_slice(@column_range.size / 2).to_a.last
      end
    end

    column = @column_range.first

    row * 8 + column
  end
end


puts seats.map { |seat| PlaneSeat.new(seat).decoded_number }.sort

