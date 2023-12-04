import java.util.ArrayList;

public class Day4 {
  public static void main(String[] args) {
    new Day4();
  }

  public Day4() {
    ArrayList<String> input = ReadInput.read("res/input4.txt");
    partOne(input);
    partTwo(input);
  }

  private void partOne(ArrayList<String> input) {
    ArrayList<Card> cards = new ArrayList<Card>();
    for (int i = 0; i < input.size(); i++) {
      cards.add(new Card(input.get(i), i));
    }

    int res = 0;
    for (Card c : cards) {
      int winningNumbers = c.winningNumbersAmount();
      if (winningNumbers == 0)
        continue;
      res += Math.pow(2, winningNumbers - 1);
    }
    System.out.println(res);
  }

  private void partTwo(ArrayList<String> input) {
    ArrayList<Card> cards = new ArrayList<Card>();
    for (int i = 0; i < input.size(); i++) {
      cards.add(new Card(input.get(i), i));
    }

    int originalCardsSize = cards.size();
    int index = 0;
    while (true) {
      if (index >= cards.size())
        break;
      Card c = cards.get(index);
      int winningNumbers = c.winningNumbersAmount();
      for (int i = c.cardNumber + 1; i <= c.cardNumber + winningNumbers; i++) {
        if (i >= originalCardsSize)
          break;
        cards.add(cards.get(i));
      }
      index++;
    }
    System.out.println(cards.size());
  }

  private class Card {
    public ArrayList<Integer> winningNumbers;
    public ArrayList<Integer> pickedNumbers;
    public int cardNumber;

    public Card(String input, int cardNumber) {
      winningNumbers = new ArrayList<Integer>();
      pickedNumbers = new ArrayList<Integer>();
      this.cardNumber = cardNumber;

      String winningString = input.split(": ")[1].split(" \\| ")[0];
      String pickedString = input.split(": ")[1].split(" \\| ")[1];

      for (String w : winningString.split(" ")) {
        if (w.length() == 0)
          continue;
        winningNumbers.add(Integer.parseInt(w));
      }

      for (String p : pickedString.split(" ")) {
        if (p.length() == 0)
          continue;
        pickedNumbers.add(Integer.parseInt(p));
      }
    }

    public int winningNumbersAmount() {
      int res = 0;
      for (int pick : pickedNumbers) {
        if (winningNumbers.contains(pick))
          res++;
      }
      return res;
    }
  }
}
