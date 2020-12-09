contents = File.read("input.txt")
passports = []

class Passport
  def initialize(attributes)
    @byr = attributes.fetch "byr", nil
    @ecl = attributes.fetch "ecl", nil
    @eyr = attributes.fetch "eyr", nil
    @hcl = attributes.fetch "hcl", nil
    @hgt = attributes.fetch "hgt", nil
    @iyr = attributes.fetch "iyr", nil
    @pid = attributes.fetch "pid", nil
  end

  def valid?
    return false if instance_variables.map { |attr| instance_variable_get(attr) }.any? &:nil?
    return false unless @byr.to_i.between?(1920, 2002)
    return false unless %(amb blu brn gry grn hzl oth).include?(@ecl)
    return false unless @eyr.to_i.between?(2020, 2030)
    return false unless @hcl.start_with?('#') && @hcl[1..@hcl.length] != 6

    height, measure = @hgt.chars.first(@hgt.length - 2).join, @hgt.chars.last(2).join
    return false unless %(in cm).include?(measure)

    if measure == 'in'
      return false unless height.to_i.between?(59, 79)
    else
      return false unless height.to_i.between?(150, 193)
    end

    return false unless @iyr.to_i.between?(2010, 2020)
    return false unless @pid.scan(/\D/).empty? && @pid.length == 9

    true
  end
end

contents.split(/\n{2,}/).each do |data|
  attributes = Hash[*data.gsub("\n", ' ').split(' ').map { |z| z.split(':') }.flatten]

  passports << Passport.new(attributes)
end

puts passports.map(&:valid?).count(true)


