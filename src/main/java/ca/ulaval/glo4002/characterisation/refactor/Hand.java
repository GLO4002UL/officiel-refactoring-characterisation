package ca.ulaval.glo4002.characterisation.refactor;

import java.util.Arrays;
import java.util.Optional;

public class Hand {

  private final int[] cards;

  public Hand(int[] cards) {
    if (cards.length < 2 || cards.length > 3) {
      throw new RuntimeException("my struct must contains 2 or 3 integers.");
    }
    for (int c : cards) {
      if (c < 0 || c > 9) {
        throw new RuntimeException("all integers passed to c must be greater or equal to 0 and smaller than 10.");
      }
    }
    this.cards = cards;
  }

  @Override
  public String toString() {
    return Arrays.toString(cards);
  }

  public boolean hasThirdCard() {
    return cards.length == 3;
  }

  public Optional<Integer> thirdCard() {
    if (cards.length == 3) {
      return Optional.of(cards[2]);
    }
    return Optional.empty();
  }

  public boolean hasNaturalWin() {
    return naturalScore() >= 8;
  }

  private int naturalScore() {
    return (cards[0] + cards[1]) % 10;
  }

  public int score() {
    return Arrays.stream(cards).sum() % 10;
  }
}
