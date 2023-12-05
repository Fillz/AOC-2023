import java.util.ArrayList;
import java.util.stream.LongStream;

public class Day5 {
  public static void main(String[] args) {
    new Day5();
  }

  ArrayList<Mapping> mappings;

  public Day5() {
    ArrayList<String> input = ReadInput.read("res/input5.txt");
    mappings = parseInput(input);
    partOne(input);
    partTwo(input);
  }

  private void partOne(ArrayList<String> input) {
    String[] seeds = input.get(0).split(": ")[1].split(" ");
    long lowest = Long.MAX_VALUE;

    for (String seed : seeds) {
      long loc = findLocation("seed", Long.parseLong(seed));
      if (loc < lowest)
        lowest = loc;
    }
    System.out.println(lowest);
  }

  private void partTwo(ArrayList<String> input) {
    String[] seeds = input.get(0).split(": ")[1].split(" ");

    for (int i = 0; i < seeds.length; i += 2) {
      long seed = Long.parseLong(seeds[i]);
      long range = Long.parseLong(seeds[i + 1]);
      LongStream.rangeClosed(seed, seed + range - 1).parallel().forEach(j -> {
        long loc = findLocation("seed", j);
        checkPartTwoLowest(loc);
      });
    }
    System.out.println(lowestPartTwo);
  }

  Long lowestPartTwo = Long.MAX_VALUE;

  private void checkPartTwoLowest(long l) {
    synchronized (lowestPartTwo) {
      if (l < lowestPartTwo)
        lowestPartTwo = l;
    }
  }

  private long findLocation(String source, long sourceType) {
    if (source.equals("location"))
      return sourceType;

    for (Mapping mapping : mappings) {
      if (!mapping.source.equals(source))
        continue;
      if (mapping.sourceRangeStart <= sourceType && sourceType < mapping.sourceRangeStart + mapping.rangeLength)
        return findLocation(mapping.destination, sourceType + mapping.destinationRangeStart - mapping.sourceRangeStart);
    }
    return findLocation(findDestination(source), sourceType);
  }

  private String findDestination(String source) {
    for (Mapping m : mappings) {
      if (m.source.equals(source))
        return m.destination;
    }
    return null;
  }

  private ArrayList<Mapping> parseInput(ArrayList<String> input) {
    ArrayList<Mapping> mappings = new ArrayList<Mapping>();
    for (int i = 2; i < input.size(); i++) {
      String inputLine = input.get(i);
      String source = inputLine.split("-to-")[0];
      String destination = inputLine.split("-to-")[1].split(" ")[0];

      int j = i + 1;
      while (true) {
        if (j >= input.size() || input.get(j).equals(""))
          break;
        String[] values = input.get(j).split(" ");
        mappings.add(new Mapping(source, destination, Long.parseLong(values[1]), Long.parseLong(values[0]),
            Long.parseLong(values[2])));
        j++;
      }
      i = j;
    }
    return mappings;
  }

  private class Mapping {
    String source;
    String destination;
    long sourceRangeStart;
    long destinationRangeStart;
    long rangeLength;

    public Mapping(String source, String destination, long sourceRangeStart, long destinationRangeStart,
        long rangeLength) {
      this.source = source;
      this.destination = destination;
      this.sourceRangeStart = sourceRangeStart;
      this.destinationRangeStart = destinationRangeStart;
      this.rangeLength = rangeLength;
    }
  }
}
