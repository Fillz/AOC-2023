import java.util.ArrayList;
import java.util.HashMap;

public class Day2 {
  public static void main(String[] args) {
    new Day2();
  }

  public Day2() {
    ArrayList<String> input = ReadInput.read("res/input2.txt");
    partOne(input);
    partTwo(input);
  }

  private void partOne(ArrayList<String> input) {
    int res = 0;
    for (String line : input) {
      String[] colonSplit = line.split(": ");
      int id = Integer.parseInt(colonSplit[0].substring(5));
      if (gameIsPossible(colonSplit[1]))
        res += id;
    }
    System.out.println(res);
  }

  private void partTwo(ArrayList<String> input) {
    int res = 0;
    for (String line : input) {
      String[] colonSplit = line.split(": ");
      int id = Integer.parseInt(colonSplit[0].substring(5));
      res += calculatePower(colonSplit[1]);
    }
    System.out.println(res);
  }

  private boolean gameIsPossible(String data) {
    int redLimit = 12;
    int greenLimit = 13;
    int blueLimit = 14;
    HashMap<String, Integer> maxColorFound = new HashMap<String, Integer>();
    maxColorFound.put("red", 0);
    maxColorFound.put("green", 0);
    maxColorFound.put("blue", 0);

    String[] sets = data.split("; ");
    for (String set : sets) {
      String[] allCubesInSet = set.split(", ");
      for (String cubes : allCubesInSet) {
        int amount = Integer.parseInt(cubes.split(" ")[0]);
        String color = cubes.split(" ")[1];
        if (maxColorFound.get(color) < amount)
          maxColorFound.put(color, amount);
      }
    }

    if (maxColorFound.get("red") <= redLimit && maxColorFound.get("green") <= greenLimit
        && maxColorFound.get("blue") <= blueLimit)
      return true;
    return false;
  }

  private int calculatePower(String data) {
    HashMap<String, Integer> maxColorFound = new HashMap<String, Integer>();
    maxColorFound.put("red", 0);
    maxColorFound.put("green", 0);
    maxColorFound.put("blue", 0);

    String[] sets = data.split("; ");
    for (String set : sets) {
      String[] allCubesInSet = set.split(", ");
      for (String cubes : allCubesInSet) {
        int amount = Integer.parseInt(cubes.split(" ")[0]);
        String color = cubes.split(" ")[1];
        if (maxColorFound.get(color) < amount)
          maxColorFound.put(color, amount);
      }
    }

    return maxColorFound.get("red") * maxColorFound.get("green") * maxColorFound.get("blue");
  }

}
