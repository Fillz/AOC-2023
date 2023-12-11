import java.util.ArrayList;

public class Day11 {
  public static void main(String[] args) {
    new Day11();
  }

  public Day11() {
    ArrayList<String> input = ReadInput.read("res/input11.txt");
    partOne(input);
    partTwo(input);
  }

  private void partOne(ArrayList<String> input) {
    calculateSumOfDistances(input, 1);
  }

  private void partTwo(ArrayList<String> input) {
    calculateSumOfDistances(input, 999999);
  }

  private void calculateSumOfDistances(ArrayList<String> input, long emptyMultiplier) {
    ArrayList<ArrayList<Character>> map = new ArrayList<ArrayList<Character>>();
    for (String line : input) {
      map.add(new ArrayList<Character>());
      for (char c : line.toCharArray()) {
        map.get(map.size() - 1).add(c);
      }
    }

    ArrayList<Integer> emptyRows = new ArrayList<Integer>();
    ArrayList<Integer> emptyCols = new ArrayList<Integer>();

    for (int y = 0; y < map.size(); y++) {
      boolean rowIsEmpty = true;
      for (int x = 0; x < map.get(0).size(); x++) {
        if (map.get(y).get(x) != '.') {
          rowIsEmpty = false;
          break;
        }
      }
      if (!rowIsEmpty)
        continue;
      emptyRows.add(y);
    }

    for (int x = 0; x < map.get(0).size(); x++) {
      boolean colIsEmpty = true;
      for (int y = 0; y < map.size(); y++) {
        if (map.get(y).get(x) != '.') {
          colIsEmpty = false;
          break;
        }
      }
      if (!colIsEmpty)
        continue;
      emptyCols.add(x);
    }

    ArrayList<Coord> galaxies = new ArrayList<Coord>();
    for (int y = 0; y < map.size(); y++) {
      for (int x = 0; x < map.get(0).size(); x++) {
        if (map.get(y).get(x) == '#')
          galaxies.add(new Coord(x, y));
      }
    }

    long res = 0;
    for (int i = 0; i < galaxies.size(); i++) {
      for (int j = i + 1; j < galaxies.size(); j++) {
        long emptyRowsAmount = 0;
        long emptyColsAmount = 0;
        for (int k = 0; k < emptyRows.size(); k++) {
          if (galaxies.get(i).y < galaxies.get(j).y) {
            if (galaxies.get(i).y < emptyRows.get(k) && emptyRows.get(k) < galaxies.get(j).y)
              emptyRowsAmount++;
          } else {
            if (galaxies.get(j).y < emptyRows.get(k) && emptyRows.get(k) < galaxies.get(i).y)
              emptyRowsAmount++;
          }
        }
        for (int k = 0; k < emptyCols.size(); k++) {
          if (galaxies.get(i).x < galaxies.get(j).x) {
            if (galaxies.get(i).x < emptyCols.get(k) && emptyCols.get(k) < galaxies.get(j).x)
              emptyColsAmount++;
          } else {
            if (galaxies.get(j).x < emptyCols.get(k) && emptyCols.get(k) < galaxies.get(i).x)
              emptyColsAmount++;
          }
        }
        res += emptyMultiplier * (emptyRowsAmount + emptyColsAmount) + Math.abs(galaxies.get(i).x - galaxies.get(j).x)
            + Math.abs(galaxies.get(i).y - galaxies.get(j).y);
      }
    }
    System.out.println(res);
  }

  private class Coord {
    int x, y;

    public Coord(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

}