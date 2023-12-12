import java.util.ArrayList;
import java.util.HashMap;

public class Day12 {
  public static void main(String[] args) {
    new Day12();
  }

  public Day12() {
    ArrayList<String> input = ReadInput.read("res/input12.txt");
    partOne(input);
    partTwo(input);
  }

  private void partOne(ArrayList<String> input) {
    long res = 0;
    for (String line : input) {
      ArrayList<Integer> springGroups = new ArrayList<Integer>();
      for (String s : line.split(" ")[1].split(","))
        springGroups.add(Integer.parseInt(s));
      res += getArrangementsAmount(line.split(" ")[0], 0, springGroups, springGroups);
    }
    System.out.println(res);
  }

  private void partTwo(ArrayList<String> input) {
    for (int i = 0; i < input.size(); i++) {
      ArrayList<String> conditions = new ArrayList<String>();
      ArrayList<String> groups = new ArrayList<String>();
      for (int j = 0; j < 5; j++) {
        conditions.add(input.get(i).split(" ")[0]);
        groups.add(input.get(i).split(" ")[1]);
      }
      input.set(i, String.join("?", conditions) + " " + String.join(",", groups));
    }

    long res = 0;
    for (String line : input) {
      ArrayList<Integer> springGroups = new ArrayList<Integer>();
      for (String s : line.split(" ")[1].split(","))
        springGroups.add(Integer.parseInt(s));
      res += getArrangementsAmount(line.split(" ")[0], 0, springGroups, springGroups);
    }
    System.out.println(res);
  }

  HashMap<String, Long> cache = new HashMap<String, Long>();

  private long getArrangementsAmount(String springs, int currentIndex, ArrayList<Integer> currentSpringGroups,
      ArrayList<Integer> initialSpringGroups) {
    if (currentSpringGroups.isEmpty() || currentIndex >= springs.length()
        || !springs.substring(currentIndex).contains("?")) {
      if (validateArrangement(springs.replaceAll("\\?", "."), initialSpringGroups))
        return 1;
      return 0;
    }

    long l = getCache(springs.substring(currentIndex), currentSpringGroups);
    if (l != -1)
      return l;

    String currentSprings = springs.substring(currentIndex);

    if (currentSprings.charAt(0) == '.') {
      long res = getArrangementsAmount(springs, currentIndex + 1, currentSpringGroups, initialSpringGroups);
      storeCache(currentSprings, currentSpringGroups, res);
      return res;
    }

    int currentGroupSize = currentSpringGroups.get(0);

    long res = 0;

    if (currentSprings.matches("^(#|\\?){" + currentGroupSize + "}([^#]{1}.*|$)")) {
      ArrayList<Integer> currentSpringGroupsDeepCopy = deepCopy(currentSpringGroups);
      currentSpringGroupsDeepCopy.remove(0);
      String springsUpdated = springs.substring(0, currentIndex) + String.valueOf("#").repeat(currentGroupSize)
          + currentSprings.substring(currentGroupSize);
      res += getArrangementsAmount(springsUpdated, currentIndex + currentGroupSize + 1, currentSpringGroupsDeepCopy,
          initialSpringGroups);
    }

    if (currentSprings.charAt(0) == '?') {
      String springsUpdated = springs.substring(0, currentIndex) + "." + currentSprings.substring(1);
      res += getArrangementsAmount(springsUpdated, currentIndex + 1, currentSpringGroups, initialSpringGroups);
    }

    storeCache(currentSprings, currentSpringGroups, res);
    return res;
  }

  private void storeCache(String springs, ArrayList<Integer> groups, long arrangements) {
    StringBuilder sb = new StringBuilder();
    sb.append(springs);
    for (int i : groups)
      sb.append(i).append(",");
    this.cache.put(sb.toString(), arrangements);
  }

  private long getCache(String springs, ArrayList<Integer> groups) {
    StringBuilder sb = new StringBuilder();
    sb.append(springs);
    for (int i : groups)
      sb.append(i).append(",");
    return cache.get(sb.toString()) != null ? cache.get(sb.toString()) : -1;
  }

  private boolean validateArrangement(String springs, ArrayList<Integer> springGroups) {
    ArrayList<Integer> grp = deepCopy(springGroups);
    for (int i = 0; i < springs.length(); i++) {
      if (grp.size() == 0) {
        if (i >= springs.length() || !springs.substring(i).contains("#"))
          return true;
        return false;
      }

      if (springs.charAt(i) == '.')
        continue;
      if (i + grp.get(0) - 1 >= springs.length())
        return false;
      for (int j = i; j < i + grp.get(0); j++) {
        if (springs.charAt(j) != '#')
          return false;
      }
      if (i + grp.get(0) < springs.length() && springs.charAt(i + grp.get(0)) != '.')
        return false;

      i = i + grp.get(0) - 1;
      grp.remove(0);

    }
    if (grp.size() != 0)
      return false;
    return true;
  }

  private ArrayList<Integer> deepCopy(ArrayList<Integer> list) {
    ArrayList<Integer> deepCopy = new ArrayList<Integer>();
    for (int i : list)
      deepCopy.add(i);
    return deepCopy;
  }
}