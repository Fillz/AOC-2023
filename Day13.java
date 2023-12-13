import java.util.ArrayList;

public class Day13 {
  public static void main(String[] args) {
    new Day13();
  }

  public Day13() {
    ArrayList<String> input = ReadInput.read("res/input13.txt");

    ArrayList<ArrayList<String>> inputList = new ArrayList<ArrayList<String>>();
    inputList.add(new ArrayList<String>());
    for (String s : input) {
      if (s.equals("")) {
        inputList.add(new ArrayList<String>());
        continue;
      }
      inputList.get(inputList.size() - 1).add(s);
    }

    ArrayList<char[][]> maps = new ArrayList<char[][]>();
    for (int i = 0; i < inputList.size(); i++) {
      ArrayList<String> list = inputList.get(i);
      char[][] map = new char[list.get(0).length()][list.size()];
      for (int y = 0; y < list.size(); y++) {
        for (int x = 0; x < list.get(0).length(); x++) {
          map[x][y] = list.get(y).charAt(x);
        }
      }
      maps.add(map);
    }

    partOne(maps);
    partTwo(maps);
  }

  private void partOne(ArrayList<char[][]> maps) {
    int res = 0;

    for (char[][] map : maps) {
      res += getValue(map);
    }
    System.out.println(res);
  }

  private void partTwo(ArrayList<char[][]> maps) {
    int res = 0;
    for (char[][] map : maps) {
      res += findSmudgeFixValue(map);
    }
    System.out.println(res);
  }

  private int findSmudgeFixValue(char[][] map) {
    int initialValue = getValue(map);
    for (int y = 0; y < map[0].length; y++) {
      for (int x = 0; x < map.length; x++) {
        char[][] newMap = copyCharArray(map);
        newMap[x][y] = newMap[x][y] == '.' ? '#' : '.';
        int tmpRes = getValue(newMap, initialValue);
        if (tmpRes == -1)
          continue;
        if (tmpRes != initialValue)
          return tmpRes;
      }
    }
    return initialValue;
  }

  private int getValue(char[][] map, Integer... ignoreValue) {
    for (int row = 0; row < map[0].length - 1; row++) {
      boolean rowIsReflective = true;
      for (int i = 0; i < map[0].length; i++) {
        if (row - i < 0 || row + 1 + i >= map[0].length)
          break;
        if (!getRow(row - i, map).equals(getRow(row + 1 + i, map))) {
          rowIsReflective = false;
          break;
        }
      }
      if (rowIsReflective && (ignoreValue.length == 0 || ignoreValue.length != 0 && (row + 1) * 100 != ignoreValue[0]))
        return (row + 1) * 100;
    }

    for (int col = 0; col < map.length - 1; col++) {
      boolean columnIsReflective = true;
      for (int i = 0; i < map.length; i++) {
        if (col - i < 0 || col + 1 + i >= map.length)
          break;
        if (!getColumn(col - i, map).equals(getColumn(col + 1 + i, map))) {
          columnIsReflective = false;
          break;
        }
      }
      if (columnIsReflective && (ignoreValue.length == 0 || ignoreValue.length != 0 && col + 1 != ignoreValue[0]))
        return col + 1;
    }
    return -1;
  }

  private String getRow(int rowIndex, char[][] map) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < map.length; i++)
      sb.append(map[i][rowIndex]);
    return sb.toString();
  }

  private String getColumn(int columnIndex, char[][] map) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < map[0].length; i++)
      sb.append(map[columnIndex][i]);
    return sb.toString();
  }

  private char[][] copyCharArray(char[][] map) {
    char[][] newMap = new char[map.length][map[0].length];
    for (int y = 0; y < map[0].length; y++) {
      for (int x = 0; x < map.length; x++) {
        newMap[x][y] = map[x][y];
      }
    }
    return newMap;
  }

}