import java.util.ArrayList;

public class Day6 {
  public static void main(String[] args) {
    new Day6();
  }

  public Day6() {
    ArrayList<String> input = ReadInput.read("res/input6.txt");
    parseInput(input);
    partOne();
    partTwo();
  }

  ArrayList<Long> time = new ArrayList<Long>();
  ArrayList<Long> dist = new ArrayList<Long>();

  private void partOne() {
    int res = 1;
    for (int i = 0; i < time.size(); i++) {
      res *= calculateAmountToBeatRecord(time.get(i), dist.get(i));
    }
    System.out.println(res);
  }

  private void partTwo() {
    StringBuilder newTime = new StringBuilder();
    StringBuilder newDist = new StringBuilder();
    for (int i = 0; i < time.size(); i++) {
      newTime.append(time.get(i));
      newDist.append(dist.get(i));
    }
    System.out.println(
        calculateAmountToBeatRecord(Long.parseLong(newTime.toString()), Long.parseLong(newDist.toString())));
  }

  private long calculateAmountToBeatRecord(long time, long recordDistance) {
    long beatRecordAmount = 0;
    for (long i = 1; i < time; i++) {
      long timeRemaining = time - i;
      long dist = timeRemaining * i;
      if (dist > recordDistance)
        beatRecordAmount++;
    }
    return beatRecordAmount;
  }

  private void parseInput(ArrayList<String> input) {
    String[] timeArray = input.get(0).split(":\\s+")[1].split("\\s+");
    String[] distArray = input.get(1).split(":\\s+")[1].split("\\s+");
    for (int i = 0; i < timeArray.length; i++) {
      time.add(Long.parseLong(timeArray[i]));
      dist.add(Long.parseLong(distArray[i]));
    }
  }

}
