import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Day10 {
  public static void main(String[] args) {
    new Day10();
  }

  public Day10() {
    ArrayList<String> input = ReadInput.read("res/input10.txt");
    partOne(input);
    partTwo(input);
  }

  private void partOne(ArrayList<String> input) {
    char[][] map = parseInput(input);
    int sx = -1;
    int sy = -1;
    for (int y = 0; y < map[0].length; y++) {
      for (int x = 0; x < map.length; x++) {
        if (map[x][y] == 'S') {
          sx = x;
          sy = y;
          break;
        }
      }
    }

    String firstStepPrev = "";
    int firstStepX = -1;
    int firstStepY = -1;
    if (sx < map.length - 1 && (map[sx + 1][sy] == 'J' || map[sx + 1][sy] == '-' || map[sx + 1][sy] == '7')) {
      firstStepPrev = "left";
      firstStepX = sx + 1;
      firstStepY = sy;

    } else if (sx >= 1 && (map[sx - 1][sy] == 'F' || map[sx - 1][sy] == '-' || map[sx - 1][sy] == 'L')) {
      firstStepPrev = "right";
      firstStepX = sx - 1;
      firstStepY = sy;
    } else if (sy < map[0].length - 1 && (map[sx][sy + 1] == 'J' || map[sx][sy + 1] == '|' || map[sx][sy + 1] == 'L')) {
      firstStepPrev = "up";
      firstStepX = sx;
      firstStepY = sy + 1;
    } else {
      firstStepPrev = "down";
      firstStepX = sx;
      firstStepY = sy - 1;
    }

    int steps = 1;
    Coord currentCoord = new Coord(firstStepX, firstStepY, firstStepPrev);
    while (true) {
      if (map[currentCoord.x][currentCoord.y] == 'S')
        break;
      currentCoord = nextCoord(map, currentCoord.x, currentCoord.y, currentCoord.prev);
      steps++;
    }
    System.out.println(steps / 2);
  }

  HashMap<Coord, Boolean> visited;

  private void partTwo(ArrayList<String> input) {
    visited = new HashMap<Coord, Boolean>();
    char[][] map = parseInput(input);
    int sx = -1;
    int sy = -1;
    for (int y = 0; y < map[0].length; y++) {
      for (int x = 0; x < map.length; x++) {
        if (map[x][y] == 'S') {
          sx = x;
          sy = y;
          break;
        }
      }
    }

    String firstStepPrev = "";
    int firstStepX = -1;
    int firstStepY = -1;
    if (sx < map.length - 1 && (map[sx + 1][sy] == 'J' || map[sx + 1][sy] == '-' || map[sx + 1][sy] == '7')) {
      firstStepPrev = "left";
      firstStepX = sx + 1;
      firstStepY = sy;

    } else if (sx >= 1 && (map[sx - 1][sy] == 'F' || map[sx - 1][sy] == '-' || map[sx - 1][sy] == 'L')) {
      firstStepPrev = "right";
      firstStepX = sx - 1;
      firstStepY = sy;
    } else if (sy < map[0].length - 1 && (map[sx][sy + 1] == 'J' || map[sx][sy + 1] == '|' || map[sx][sy + 1] == 'L')) {
      firstStepPrev = "up";
      firstStepX = sx;
      firstStepY = sy + 1;
    } else {
      firstStepPrev = "down";
      firstStepX = sx;
      firstStepY = sy - 1;
    }

    putVisited(new Coord(sx, sy, ""));

    int rightTurns = 0;
    int leftTurns = 0;
    ArrayList<Coord> leftCoords = new ArrayList<Coord>();
    ArrayList<Coord> rightCoords = new ArrayList<Coord>();
    Coord currentCoord = new Coord(firstStepX, firstStepY, firstStepPrev);
    while (true) {
      if (map[currentCoord.x][currentCoord.y] == 'S')
        break;

      putVisited(currentCoord);

      Coord oldCoord = new Coord(currentCoord.x, currentCoord.y, currentCoord.prev);
      currentCoord = nextCoord(map, currentCoord.x, currentCoord.y, currentCoord.prev);

      if (oldCoord.prev.equals("left")) {
        if (currentCoord.y > oldCoord.y) {
          rightTurns++;
          leftCoords.add(new Coord(oldCoord.x, oldCoord.y - 1, ""));
          leftCoords.add(new Coord(oldCoord.x + 1, oldCoord.y, ""));
        } else if (currentCoord.y < oldCoord.y) {
          leftTurns++;
          rightCoords.add(new Coord(oldCoord.x, oldCoord.y + 1, ""));
          rightCoords.add(new Coord(oldCoord.x + 1, oldCoord.y, ""));
        } else {
          leftCoords.add(new Coord(oldCoord.x, oldCoord.y - 1, ""));
          rightCoords.add(new Coord(oldCoord.x, oldCoord.y + 1, ""));
        }
      } else if (oldCoord.prev.equals("right")) {
        if (currentCoord.y > oldCoord.y) {
          leftTurns++;
          rightCoords.add(new Coord(oldCoord.x, oldCoord.y - 1, ""));
          rightCoords.add(new Coord(oldCoord.x - 1, oldCoord.y, ""));
        } else if (currentCoord.y < oldCoord.y) {
          rightTurns++;
          leftCoords.add(new Coord(oldCoord.x, oldCoord.y + 1, ""));
          leftCoords.add(new Coord(oldCoord.x - 1, oldCoord.y, ""));
        } else {
          leftCoords.add(new Coord(oldCoord.x, oldCoord.y + 1, ""));
          rightCoords.add(new Coord(oldCoord.x, oldCoord.y - 1, ""));
        }
      } else if (oldCoord.prev.equals("up")) {
        if (currentCoord.x > oldCoord.x) {
          leftTurns++;
          rightCoords.add(new Coord(oldCoord.x - 1, oldCoord.y, ""));
          rightCoords.add(new Coord(oldCoord.x, oldCoord.y + 1, ""));
        } else if (currentCoord.x < oldCoord.x) {
          rightTurns++;
          leftCoords.add(new Coord(oldCoord.x + 1, oldCoord.y, ""));
          leftCoords.add(new Coord(oldCoord.x, oldCoord.y + 1, ""));
        } else {
          leftCoords.add(new Coord(oldCoord.x + 1, oldCoord.y, ""));
          rightCoords.add(new Coord(oldCoord.x - 1, oldCoord.y, ""));
        }
      } else if (oldCoord.prev.equals("down")) {
        if (currentCoord.x > oldCoord.x) {
          rightTurns++;
          leftCoords.add(new Coord(oldCoord.x - 1, oldCoord.y, ""));
          leftCoords.add(new Coord(oldCoord.x, oldCoord.y - 1, ""));
        } else if (currentCoord.x < oldCoord.x) {
          leftTurns++;
          rightCoords.add(new Coord(oldCoord.x + 1, oldCoord.y, ""));
          rightCoords.add(new Coord(oldCoord.x, oldCoord.y - 1, ""));
        } else {
          leftCoords.add(new Coord(oldCoord.x - 1, oldCoord.y, ""));
          rightCoords.add(new Coord(oldCoord.x + 1, oldCoord.y, ""));
        }
      }
    }

    ArrayList<Coord> coordsToCheck;
    if (rightTurns > leftTurns)
      coordsToCheck = rightCoords;
    else
      coordsToCheck = leftCoords;

    int res = 0;
    for (Coord c : coordsToCheck) {
      res += findEnclosedTiles(c);
    }
    System.out.println(res);
  }

  private int findEnclosedTiles(Coord c) {
    if (isVisited(c))
      return 0;
    putVisited(c);
    return 1 + findEnclosedTiles(new Coord(c.x - 1, c.y, "")) + findEnclosedTiles(new Coord(c.x + 1, c.y, ""))
        + findEnclosedTiles(new Coord(c.x, c.y - 1, "")) + findEnclosedTiles(new Coord(c.x, c.y + 1, ""));
  }

  private boolean isVisited(Coord c) {
    return visited.get(c) != null ? visited.get(c) : false;
  }

  private void putVisited(Coord c) {
    visited.put(c, true);
  }

  private Coord nextCoord(char[][] map, int x, int y, String prev) {
    char c = map[x][y];
    if (prev.equals("left")) {
      if (c == 'J')
        return new Coord(x, y - 1, "down");
      if (c == '-')
        return new Coord(x + 1, y, "left");
      if (c == '7')
        return new Coord(x, y + 1, "up");
    }
    if (prev.equals("right")) {
      if (c == 'L')
        return new Coord(x, y - 1, "down");
      if (c == '-')
        return new Coord(x - 1, y, "right");
      if (c == 'F')
        return new Coord(x, y + 1, "up");
    }
    if (prev.equals("up")) {
      if (c == '|')
        return new Coord(x, y + 1, "up");
      if (c == 'J')
        return new Coord(x - 1, y, "right");
      if (c == 'L')
        return new Coord(x + 1, y, "left");
    }
    if (prev.equals("down")) {
      if (c == '|')
        return new Coord(x, y - 1, "down");
      if (c == '7')
        return new Coord(x - 1, y, "right");
      if (c == 'F')
        return new Coord(x + 1, y, "left");
    }
    return null;
  }

  private char[][] parseInput(ArrayList<String> input) {
    char[][] map = new char[input.get(0).length()][input.size()];
    for (int y = 0; y < input.size(); y++) {
      for (int x = 0; x < input.get(0).length(); x++) {
        map[x][y] = input.get(y).charAt(x);
      }
    }
    return map;
  }

  private class Coord {
    int hashCode;
    int x, y;
    String prev;

    public Coord(int x, int y, String prev) {
      this.x = x;
      this.y = y;
      this.prev = prev;
      this.hashCode = Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      Coord c = (Coord) o;
      return x == c.x && y == c.y;
    }

    @Override
    public int hashCode() {
      return this.hashCode;
    }
  }
}