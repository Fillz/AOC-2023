import java.util.ArrayList;
import java.util.HashMap;

public class Day8 {
  public static void main(String[] args) {
    new Day8();
  }

  public Day8() {
    ArrayList<String> input = ReadInput.read("res/input8.txt");
    partOne(input);
    partTwo(input);
  }

  private void partOne(ArrayList<String> input) {
    HashMap<String, NodeDirections> map = new HashMap<String, NodeDirections>();
    String instructions = input.get(0);
    for (int i = 2; i < input.size(); i++) {
      String dirs = input.get(i).split("\\(")[1].split("\\)")[0];
      map.put(input.get(i).split(" ")[0], new NodeDirections(dirs.split(", ")[0], dirs.split(", ")[1]));
    }

    int steps = 0;
    String currentNode = "AAA";
    int i = 0;
    while (true) {
      if (currentNode.equals("ZZZ"))
        break;

      char c = instructions.charAt(i);
      if (c == 'L')
        currentNode = map.get(currentNode).left;
      else if (c == 'R')
        currentNode = map.get(currentNode).right;
      steps++;
      i = i + 1 >= instructions.length() ? 0 : i + 1;
    }
    System.out.println(steps);
  }

  private void partTwo(ArrayList<String> input) {
    HashMap<String, NodeDirections> map = new HashMap<String, NodeDirections>();
    ArrayList<String> startNodes = new ArrayList<String>();
    String instructions = input.get(0);
    for (int i = 2; i < input.size(); i++) {
      String dirs = input.get(i).split("\\(")[1].split("\\)")[0];
      String n = input.get(i).split(" ")[0];
      map.put(n, new NodeDirections(dirs.split(", ")[0], dirs.split(", ")[1]));
      if (n.charAt(n.length() - 1) == 'A')
        startNodes.add(n);
    }

    ArrayList<Integer> endingSteps = new ArrayList<Integer>();
    int steps = 0;
    int i = 0;
    while (true) {
      if (startNodes.size() == 0)
        break;

      for (int j = 0; j < startNodes.size(); j++) {
        if (startNodes.get(j).charAt(startNodes.get(j).length() - 1) == 'Z') {
          endingSteps.add(steps);
          startNodes.remove(j);
          j--;
        }
      }

      for (int j = 0; j < startNodes.size(); j++) {
        String currentNode = startNodes.get(j);

        char c = instructions.charAt(i);
        if (c == 'L')
          startNodes.set(j, map.get(currentNode).left);
        else if (c == 'R')
          startNodes.set(j, map.get(currentNode).right);
      }
      steps++;
      i = i + 1 >= instructions.length() ? 0 : i + 1;
    }
    System.out.println(lcmArray(endingSteps));
  }

  private long lcmArray(ArrayList<Integer> numbers) {
    long res = numbers.get(0);
    for (int i = 1; i < numbers.size(); i++) {
      res = lcm(res, numbers.get(i));
    }
    return res;
  }

  private long lcm(long a, long b) {
    return a * (b / gcd(a, b));
  }

  private long gcd(long a, long b) {
    while (b > 0) {
      long temp = b;
      b = a % b;
      a = temp;
    }
    return a;
  }

  private class NodeDirections {
    private String left;
    private String right;

    public NodeDirections(String l, String r) {
      left = l;
      right = r;
    }
  }

}
