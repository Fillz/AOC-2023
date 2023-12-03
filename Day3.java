import java.util.ArrayList;
import java.util.HashMap;

public class Day3 {
  public static void main(String[] args) {
    new Day3();
  }

  public Day3() {
    ArrayList<String> input = ReadInput.read("res/input3.txt");
    partOne(input);
    partTwo(input);
  }

  private void partOne(ArrayList<String> input) {
    int res = 0;
    Schematic schematic = new Schematic(input);
    for (int y = 0; y < schematic.getHeight(); y++) {
      for (int x = 0; x < schematic.getWidth(); x++) {
        char c = schematic.get(x, y);
        if (Character.isDigit(c)) {
          int numberEndXValue = -1;
          for (int i = x + 1; i <= schematic.getWidth(); i++) {
            char newChar = schematic.get(i, y);
            if (!Character.isDigit(newChar)) {
              numberEndXValue = i - 1;
              break;
            }
          }

          if (schematic.isPartNumber(x, numberEndXValue, y))
            res += schematic.getNumber(x, numberEndXValue, y);

          x = numberEndXValue + 1;
        }
      }
    }
    System.out.println(res);
  }

  private void partTwo(ArrayList<String> input) {
    Schematic schematic = new Schematic(input);
    NumberMap nm = new NumberMap();

    for (int y = 0; y < schematic.getHeight(); y++) {
      for (int x = 0; x < schematic.getWidth(); x++) {
        char c = schematic.get(x, y);
        if (Character.isDigit(c)) {
          int numberEndXValue = -1;
          for (int i = x + 1; i <= schematic.getWidth(); i++) {
            char newChar = schematic.get(i, y);
            if (!Character.isDigit(newChar)) {
              numberEndXValue = i - 1;
              break;
            }
          }

          int number = schematic.getNumber(x, numberEndXValue, y);
          for (int i = x; i <= numberEndXValue; i++) {
            nm.put(i, y, number);
          }

          x = numberEndXValue + 1;
        }
      }
    }

    int res = 0;
    for (int y = 0; y < schematic.getHeight(); y++) {
      for (int x = 0; x < schematic.getWidth(); x++) {
        char c = schematic.get(x, y);

        if (c != '*')
          continue;

        int gearValue = 1;
        int adjacentNumbers = 0;

        int topLeft = nm.get(x - 1, y - 1);
        int topMid = nm.get(x, y - 1);
        int topRight = nm.get(x + 1, y - 1);
        if (topLeft != -1 && topMid != -1 && topRight != -1) {
          adjacentNumbers++;
          gearValue *= topLeft;
        } else if (topLeft != -1 && topMid == -1 && topRight != -1) {
          adjacentNumbers += 2;
          gearValue *= topLeft * topRight;
        } else if (topLeft != -1) {
          adjacentNumbers++;
          gearValue *= topLeft;
        } else if (topRight != -1) {
          adjacentNumbers++;
          gearValue *= topRight;
        }

        int botLeft = nm.get(x - 1, y + 1);
        int botMid = nm.get(x, y + 1);
        int botRight = nm.get(x + 1, y + 1);
        if (botLeft != -1 && botMid != -1 && botRight != -1) {
          adjacentNumbers++;
          gearValue *= botLeft;
        } else if (botLeft != -1 && botMid == -1 && botRight != -1) {
          adjacentNumbers += 2;
          gearValue *= botLeft * botRight;
        } else if (botLeft != -1) {
          adjacentNumbers++;
          gearValue *= botLeft;
        } else if (botRight != -1) {
          adjacentNumbers++;
          gearValue *= botRight;
        }

        int left = nm.get(x - 1, y);
        int right = nm.get(x + 1, y);
        if (left != -1) {
          adjacentNumbers++;
          gearValue *= left;
        }
        if (right != -1) {
          adjacentNumbers++;
          gearValue *= right;
        }

        if (adjacentNumbers == 2)
          res += gearValue;
      }
    }
    System.out.println(res);
  }

  private class Schematic {
    private ArrayList<ArrayList<Character>> map;

    public Schematic(ArrayList<String> input) {
      map = new ArrayList<ArrayList<Character>>();
      for (String line : input) {
        map.add(new ArrayList<Character>());
        for (char c : line.toCharArray()) {
          map.get(map.size() - 1).add(c);
        }
      }
    }

    public char get(int x, int y) {
      if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight())
        return '.';
      return map.get(y).get(x);
    }

    public int getNumber(int xStart, int xEnd, int y) {
      StringBuilder sb = new StringBuilder();
      for (int i = xStart; i <= xEnd; i++) {
        sb.append(get(i, y));
      }
      return Integer.parseInt(sb.toString());
    }

    public boolean isPartNumber(int xStart, int xEnd, int yIn) {
      for (int y = yIn - 1; y <= yIn + 1; y++) {
        for (int x = xStart - 1; x <= xEnd + 1; x++) {
          char c = get(x, y);
          if (!Character.isDigit(c) && c != '.')
            return true;
        }
      }
      return false;
    }

    public int getWidth() {
      return map.get(0).size();
    }

    public int getHeight() {
      return map.size();
    }
  }

  private class NumberMap {
    private HashMap<String, Integer> map;

    public NumberMap() {
      map = new HashMap<String, Integer>();
    }

    public int get(int x, int y) {
      Integer res = map.get("" + x + "," + y);
      return res != null ? res : -1;
    }

    public void put(int x, int y, int number) {
      map.put("" + x + "," + y, number);
    }
  }
}
