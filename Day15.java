import java.util.ArrayList;

public class Day15 {
  public static void main(String[] args) {
    new Day15();
  }

  public Day15() {
    ArrayList<String> input = ReadInput.read("res/input15.txt");
    partOne(input);
    partTwo(input);
  }

  private void partOne(ArrayList<String> input) {
    int res = 0;
    for (String s : input.get(0).split(","))
      res += hash(s);
    System.out.println(res);
  }

  private void partTwo(ArrayList<String> input) {
    Box[] boxes = new Box[256];
    for (int i = 0; i < boxes.length; i++)
      boxes[i] = new Box();

    for (String s : input.get(0).split(",")) {
      String label = s.split("-|=")[0];
      if (s.contains("-"))
        boxes[hash(label)].removeLens(label);
      else {
        int value = Integer.parseInt(s.split("=")[1]);
        boxes[hash(label)].addLens(label, value);
      }
    }

    int res = 0;
    for (int i = 0; i < boxes.length; i++) {
      for (int j = 0; j < boxes[i].lenses.size(); j++) {
        res += (1 + i) * (j + 1) * boxes[i].lenses.get(j).value;
      }
    }
    System.out.println(res);
  }

  private int hash(String s) {
    int res = 0;
    for (char c : s.toCharArray()) {
      res += (int) c;
      res *= 17;
      res %= 256;
    }
    return res;
  }

  private class Box {
    ArrayList<Lens> lenses;

    public Box() {
      lenses = new ArrayList<Lens>();
    }

    public void removeLens(String label) {
      for (int i = 0; i < lenses.size(); i++) {
        if (lenses.get(i).label.equals(label)) {
          lenses.remove(i);
          return;
        }
      }
    }

    public void addLens(String label, int value) {
      for (int i = 0; i < lenses.size(); i++) {
        if (lenses.get(i).label.equals(label)) {
          lenses.get(i).value = value;
          return;
        }
      }
      lenses.add(new Lens(label, value));
    }
  }

  private class Lens {
    String label;
    int value;

    public Lens(String l, int v) {
      label = l;
      value = v;
    }
  }

}
