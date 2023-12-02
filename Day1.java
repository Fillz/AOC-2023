import java.util.ArrayList;

public class Day1 {
  public static void main(String[] args) {
    new Day1();
  }

  public Day1() {
    ArrayList<String> input = ReadInput.read("res/input1.txt");
    partOne(input);
    partTwo(input);
  }

  private void partOne(ArrayList<String> input) {
    int res = 0;
    for (String line : input) {
      char first = '-';
      for (int i = 0; i < line.length(); i++) {
        char c = line.charAt(i);
        if (Character.isDigit(c)) {
          first = c;
          break;
        }
      }

      char second = '-';
      for (int i = line.length() - 1; i >= 0; i--) {
        char c = line.charAt(i);
        if (Character.isDigit(c)) {
          second = c;
          break;
        }
      }
      res += Integer.parseInt(String.valueOf(first) + String.valueOf(second));
    }
    System.out.println(res);
  }

  private void partTwo(ArrayList<String> input) {
    int res = 0;
    for (String line : input) {
      char first = '-';
      for (int i = 0; i < line.length(); i++) {
        char c = line.charAt(i);
        if (Character.isDigit(c)) {
          first = c;
          break;
        }
        char n = isNumber(line, i);
        if (n != '-') {
          first = n;
          break;
        }
      }

      char second = '-';
      for (int i = line.length() - 1; i >= 0; i--) {
        char c = line.charAt(i);
        if (Character.isDigit(c)) {
          second = c;
          break;
        }
        char n = isNumber(line, i);
        if (n != '-') {
          second = n;
          break;
        }
      }
      res += Integer.parseInt(String.valueOf(first) + String.valueOf(second));
    }
    System.out.println(res);
  }

  private char isNumber(String s, int index) {
    NumberPair[] numberArray = { new NumberPair('1', "one"), new NumberPair('2', "two"), new NumberPair('3', "three"),
        new NumberPair('4', "four"), new NumberPair('5', "five"), new NumberPair('6', "six"),
        new NumberPair('7', "seven"),
        new NumberPair('8', "eight"), new NumberPair('9', "nine") };

    for (NumberPair np : numberArray) {
      String n = np.name;
      if (index + n.length() <= s.length() && s.substring(index, index + n.length()).equals(n))
        return np.value;
    }
    return '-';
  }

  private class NumberPair {
    public char value;
    public String name;

    public NumberPair(char v, String n) {
      this.value = v;
      this.name = n;
    }
  }

}
