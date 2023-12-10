import java.util.ArrayList;

public class Day9 {
  public static void main(String[] args) {
    new Day9();
  }

  public Day9() {
    ArrayList<String> input = ReadInput.read("res/input9.txt");
    partOne(input);
    partTwo(input);
  }

  private void partOne(ArrayList<String> input) {
    int res = 0;
    for (String s : input) {
      ArrayList<Integer> list = new ArrayList<Integer>();
      for (String n : s.split(" "))
        list.add(Integer.parseInt(n));
      res += getNextValue(list);
    }
    System.out.println(res);
  }

  private void partTwo(ArrayList<String> input) {
    int res = 0;
    for (String s : input) {
      ArrayList<Integer> list = new ArrayList<Integer>();
      for (String n : s.split(" "))
        list.add(Integer.parseInt(n));
      res += getPrevValues(list);
    }
    System.out.println(res);
  }

  private int getNextValue(ArrayList<Integer> list) {
    boolean allZeroes = true;
    for (int i : list) {
      if (i == 0)
        continue;
      allZeroes = false;
      break;
    }
    if (allZeroes)
      return 0;

    ArrayList<Integer> diffs = new ArrayList<Integer>();
    for (int i = 0; i < list.size() - 1; i++) {
      diffs.add(list.get(i + 1) - list.get(i));
    }

    int nextValue = getNextValue(diffs);

    return list.get(list.size() - 1) + nextValue;
  }

  private int getPrevValues(ArrayList<Integer> list) {
    boolean allZeroes = true;
    for (int i : list) {
      if (i == 0)
        continue;
      allZeroes = false;
      break;
    }
    if (allZeroes)
      return 0;

    ArrayList<Integer> diffs = new ArrayList<Integer>();
    for (int i = 0; i < list.size() - 1; i++) {
      diffs.add(list.get(i + 1) - list.get(i));
    }

    int prevValue = getPrevValues(diffs);

    return list.get(0) - prevValue;
  }

}
