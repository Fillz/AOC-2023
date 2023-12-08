import java.util.ArrayList;
import java.util.Collections;

public class Day7 {
  public static void main(String[] args) {
    new Day7();
  }

  public Day7() {
    ArrayList<String> input = ReadInput.read("res/input7.txt");
    partOne(input);
    partTwo(input);
  }

  private void partOne(ArrayList<String> input) {
    ArrayList<Hand> hands = new ArrayList<Hand>();
    for (String s : input)
      hands.add(new Hand(s.split(" ")[0], Integer.parseInt(s.split(" ")[1])));
    Collections.sort(hands);

    int res = 0;
    for (int i = 0; i < hands.size(); i++)
      res += (i + 1) * hands.get(i).bid;
    System.out.println(res);
  }

  private void partTwo(ArrayList<String> input) {
    ArrayList<HandPartTwo> hands = new ArrayList<HandPartTwo>();
    for (String s : input)
      hands.add(new HandPartTwo(s.split(" ")[0], Integer.parseInt(s.split(" ")[1])));
    Collections.sort(hands);

    int res = 0;
    for (int i = 0; i < hands.size(); i++)
      res += (i + 1) * hands.get(i).bid;
    System.out.println(res);
  }

  private class Hand implements Comparable<Hand> {
    public ArrayList<CardGroup> cardGroups;
    public int bid;
    public String handString;

    public Hand(String handString, int bid) {
      cardGroups = new ArrayList<CardGroup>();
      for (char c : handString.toCharArray()) {
        boolean cardExists = false;
        for (CardGroup cg : cardGroups) {
          if (cg.card == c) {
            cg.amount++;
            cardExists = true;
            break;
          }
        }
        if (!cardExists)
          cardGroups.add(new CardGroup(c, 1));
      }
      this.bid = bid;
      this.handString = handString;
    }

    /**
     * 1 = High card
     * 2 = One pair
     * 3 = Two pair
     * 4 = Three of a kind
     * 5 = Full house
     * 6 = Four of a kind
     * 7 = Five of a kind
     */
    public int getHandType() {
      if (cardGroups.size() == 1)
        return 7;

      if (cardGroups.size() == 2) {
        if (cardGroups.get(0).amount == 2 || cardGroups.get(0).amount == 3)
          return 5;
        return 6;
      }
      for (CardGroup cg : cardGroups) {
        if (cg.amount == 3)
          return 4;
      }

      int pairAmount = 0;
      for (CardGroup cg : cardGroups) {
        if (cg.amount == 2)
          pairAmount++;
      }
      if (pairAmount == 2)
        return 3;
      if (pairAmount == 1)
        return 2;
      return 1;
    }

    private int getCardValue(char c) {
      String s = "23456789TJQKA";
      for (int i = 0; i < s.length(); i++) {
        if (c == s.charAt(i))
          return i;
      }
      return -1;
    }

    @Override
    public int compareTo(Hand h) {
      if (getHandType() < h.getHandType())
        return -1;
      if (getHandType() > h.getHandType())
        return 1;

      for (int i = 0; i < 5; i++) {
        if (getCardValue(handString.charAt(i)) < getCardValue(h.handString.charAt(i)))
          return -1;
        if (getCardValue(handString.charAt(i)) > getCardValue(h.handString.charAt(i)))
          return 1;
      }
      return 0;
    }
  }

  private class HandPartTwo implements Comparable<HandPartTwo> {
    public ArrayList<CardGroup> cardGroups;
    public int bid;
    public String handString;

    public HandPartTwo(String handString, int bid) {
      cardGroups = new ArrayList<CardGroup>();
      for (char c : handString.toCharArray()) {
        boolean cardExists = false;
        for (CardGroup cg : cardGroups) {
          if (cg.card == c) {
            cg.amount++;
            cardExists = true;
            break;
          }
        }
        if (!cardExists)
          cardGroups.add(new CardGroup(c, 1));
      }
      this.bid = bid;
      this.handString = handString;
    }

    /**
     * 1 = High card
     * 2 = One pair
     * 3 = Two pair
     * 4 = Three of a kind
     * 5 = Full house
     * 6 = Four of a kind
     * 7 = Five of a kind
     */
    public int getHandType() {
      int jokerAmount = 0;
      for (CardGroup cg : cardGroups) {
        if (cg.card == 'J')
          jokerAmount = cg.amount;
      }

      if (cardGroups.size() == 1)
        return 7;

      if (cardGroups.size() == 2) {
        if (jokerAmount > 0)
          return 7;
        if (cardGroups.get(0).amount == 2 || cardGroups.get(0).amount == 3)
          return 5;
        return 6;
      }
      for (CardGroup cg : cardGroups) {
        if (cg.amount == 3 && cg.card == 'J')
          return 6;
        if (cg.amount == 3 && cg.card != 'J') {
          if (jokerAmount == 1)
            return 6;
          return 4;
        }
      }

      int pairAmount = 0;
      boolean jokerPairExists = false;
      for (CardGroup cg : cardGroups) {
        if (cg.amount == 2) {
          pairAmount++;
          if (cg.card == 'J')
            jokerPairExists = true;
        }
      }
      if (pairAmount == 2 && !jokerPairExists) {
        if (jokerAmount > 0)
          return 5;
        return 3;
      }
      if (pairAmount == 2 && jokerPairExists)
        return 6;
      if (pairAmount == 1 && !jokerPairExists) {
        if (jokerAmount > 0)
          return 4;
        return 2;
      }
      if (pairAmount == 1 && jokerPairExists)
        return 4;
      return 1 + jokerAmount;
    }

    private int getCardValuePartTwo(char c) {
      String s = "J23456789TQKA";
      for (int i = 0; i < s.length(); i++) {
        if (c == s.charAt(i))
          return i;
      }
      return -1;
    }

    @Override
    public int compareTo(HandPartTwo h) {
      if (getHandType() < h.getHandType())
        return -1;
      if (getHandType() > h.getHandType())
        return 1;

      for (int i = 0; i < 5; i++) {
        if (getCardValuePartTwo(handString.charAt(i)) < getCardValuePartTwo(h.handString.charAt(i)))
          return -1;
        if (getCardValuePartTwo(handString.charAt(i)) > getCardValuePartTwo(h.handString.charAt(i)))
          return 1;
      }
      return 0;
    }
  }

  private class CardGroup {
    public char card;
    public int amount;

    public CardGroup(char c, int a) {
      card = c;
      amount = a;
    }
  }
}
