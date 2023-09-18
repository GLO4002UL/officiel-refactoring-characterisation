package ca.ulaval.glo4002.characterisation.refactor;

import java.util.Arrays;

public class Dealer {

  public boolean shouldDealBankerThirdCard(Hand playerHand, Hand bankerHand) {
    if (bankerHand.hasThirdCard() || isNaturalWinCoup(playerHand, bankerHand)) {
      return false;
    }
    int bankerScore = bankerHand.score();
    return playerHand.thirdCard()
        .map((playerThirdCardValue) -> applySpecialRulesWhenPlayerHasThirdCard(bankerScore, playerThirdCardValue))
        .orElse(bankerScore <= 5);
  }

  private static boolean isNaturalWinCoup(Hand playerHand, Hand bankerHand) {
    return playerHand.hasNaturalWin() || bankerHand.hasNaturalWin();
  }

  private static boolean applySpecialRulesWhenPlayerHasThirdCard(int bankerScore, int playerThirdCardValue) {
    if (bankerScore <= 2) {
      return true;
    } else if (bankerScore == 3) {
      return playerThirdCardValue != 8;
    } else if (bankerScore == 4) {
      return !Arrays.asList(0, 1, 8, 9).contains(playerThirdCardValue);
    } else if (bankerScore == 5) {
      return !Arrays.asList(0, 1, 2, 3, 8, 9).contains(playerThirdCardValue);
    } else if (bankerScore == 6) {
      return !Arrays.asList(0, 1, 2, 3, 4, 5, 8, 9).contains(playerThirdCardValue);
    } else {
      return false;
    }
  }
}
