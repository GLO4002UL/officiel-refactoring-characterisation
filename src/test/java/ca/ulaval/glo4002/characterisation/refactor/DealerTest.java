package ca.ulaval.glo4002.characterisation.refactor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DealerTest {

  private Dealer dealer;

  @Mock
  private Hand playerHand;

  @Mock
  private Hand bankerHand;

  @BeforeEach
  public void setUp() {
    dealer = new Dealer();
  }

  @Test
  public void givenBankerHandHasAlreadyThreeCards_whenShouldDealBankerThirdCard_thenReturnsFalse() {
    when(bankerHand.hasThirdCard()).thenReturn(true);

    boolean shouldDealBankerThirdCard = dealer.shouldDealBankerThirdCard(playerHand, bankerHand);

    assertFalse(shouldDealBankerThirdCard);
  }

  @Test
  public void givenPlayerHasNaturalWin_whenShouldDealBankerThirdCard_thenReturnsFalse() {
    when(bankerHand.hasThirdCard()).thenReturn(false);
    when(playerHand.hasNaturalWin()).thenReturn(true);

    boolean shouldDealBankerThirdCard = dealer.shouldDealBankerThirdCard(playerHand, bankerHand);

    assertFalse(shouldDealBankerThirdCard);
  }

  @Test
  public void givenBankerHasNaturalWin_whenShouldDealBankerThirdCard_thenReturnsFalse() {
    when(bankerHand.hasThirdCard()).thenReturn(false);
    when(playerHand.hasNaturalWin()).thenReturn(false);
    when(bankerHand.hasNaturalWin()).thenReturn(true);

    boolean shouldDealBankerThirdCard = dealer.shouldDealBankerThirdCard(playerHand, bankerHand);

    assertFalse(shouldDealBankerThirdCard);
  }

  @Test
  public void givenPlayerHasOnlyTwoCardsAndBankerHasScoreLessOrEqualsTo5_whenShouldDealBankerThirdCard_thenReturnsTrue() {
    when(bankerHand.hasThirdCard()).thenReturn(false);
    when(playerHand.hasNaturalWin()).thenReturn(false);
    when(bankerHand.hasNaturalWin()).thenReturn(false);
    when(playerHand.thirdCard()).thenReturn(Optional.empty());
    when(bankerHand.score()).thenReturn(5);

    boolean shouldDealBankerThirdCard = dealer.shouldDealBankerThirdCard(playerHand, bankerHand);

    assertTrue(shouldDealBankerThirdCard);
  }

  @Test
  public void givenPlayerHasOnlyTwoCardsAndBankerHasScoreGreaterThan5_whenShouldDealBankerThirdCard_thenReturnsFalse() {
    when(bankerHand.hasThirdCard()).thenReturn(false);
    when(playerHand.hasNaturalWin()).thenReturn(false);
    when(bankerHand.hasNaturalWin()).thenReturn(false);
    when(playerHand.thirdCard()).thenReturn(Optional.empty());
    when(bankerHand.score()).thenReturn(6);

    boolean shouldDealBankerThirdCard = dealer.shouldDealBankerThirdCard(playerHand, bankerHand);

    assertFalse(shouldDealBankerThirdCard);
  }

  private static Stream<Arguments> provideArgumentsBankerScoreLessOrEqualsTo2() {
    List<Arguments> arguments = new ArrayList<>();
    for (int i : IntStream.range(0, 10).toArray()) {
      for (int j : IntStream.range(0, 3).toArray()) {
        arguments.add(Arguments.of(i, j));
      }
    }
    return arguments.stream();
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsBankerScoreLessOrEqualsTo2")
  public void givenPlayerHasThirdCardAndBankerHasScoreLessOrEqualsTo2_whenShouldDealBankerThirdCard_thenReturnsTrue(int playerThirdCardValue, int bankerScore) {
    when(bankerHand.hasThirdCard()).thenReturn(false);
    when(playerHand.hasNaturalWin()).thenReturn(false);
    when(bankerHand.hasNaturalWin()).thenReturn(false);
    when(playerHand.thirdCard()).thenReturn(Optional.of(playerThirdCardValue));
    when(bankerHand.score()).thenReturn(bankerScore);

    boolean shouldDealBankerThirdCard = dealer.shouldDealBankerThirdCard(playerHand, bankerHand);

    assertTrue(shouldDealBankerThirdCard);
  }

  private static Stream<Arguments> provideArgumentsBankerScoreEquals3() {
    List<Arguments> arguments = new ArrayList<>();
    for (int i : IntStream.range(0, 10).toArray()) {
      arguments.add(Arguments.of(i, 3, i != 8));
    }
    return arguments.stream();
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsBankerScoreEquals3")
  public void givenPlayerHasThirdCardAndBankerHasScoreEquals3_whenShouldDealBankerThirdCard_thenReturnsTrueIfPlayerThirdCardIsNot8(
      int playerThirdCardValue,
      int bankerScore,
      boolean expected) {
    when(bankerHand.hasThirdCard()).thenReturn(false);
    when(playerHand.hasNaturalWin()).thenReturn(false);
    when(bankerHand.hasNaturalWin()).thenReturn(false);
    when(playerHand.thirdCard()).thenReturn(Optional.of(playerThirdCardValue));
    when(bankerHand.score()).thenReturn(bankerScore);

    boolean shouldDealBankerThirdCard = dealer.shouldDealBankerThirdCard(playerHand, bankerHand);

    assertEquals(expected, shouldDealBankerThirdCard);
  }

  private static Stream<Arguments> provideArgumentsBankerScoreEquals4() {
    List<Arguments> arguments = new ArrayList<>();
    for (int i : IntStream.range(0, 10).toArray()) {
      arguments.add(Arguments.of(i, 4, Arrays.asList(2, 3, 4, 5, 6, 7).contains(i)));
    }
    return arguments.stream();
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsBankerScoreEquals4")
  public void givenPlayerHasThirdCardAndBankerHasScoreEquals4_whenShouldDealBankerThirdCard_thenReturnsTrueIfPlayerThirdCardIsBetween2And7(
      int playerThirdCardValue,
      int bankerScore,
      boolean expected) {
    when(bankerHand.hasThirdCard()).thenReturn(false);
    when(playerHand.hasNaturalWin()).thenReturn(false);
    when(bankerHand.hasNaturalWin()).thenReturn(false);
    when(playerHand.thirdCard()).thenReturn(Optional.of(playerThirdCardValue));
    when(bankerHand.score()).thenReturn(bankerScore);

    boolean shouldDealBankerThirdCard = dealer.shouldDealBankerThirdCard(playerHand, bankerHand);

    assertEquals(expected, shouldDealBankerThirdCard);
  }

  private static Stream<Arguments> provideArgumentsBankerScoreEquals5() {
    List<Arguments> arguments = new ArrayList<>();
    for (int i : IntStream.range(0, 10).toArray()) {
      arguments.add(Arguments.of(i, 5, Arrays.asList(4, 5, 6, 7).contains(i)));
    }
    return arguments.stream();
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsBankerScoreEquals5")
  public void givenPlayerHasThirdCardAndBankerHasScoreEquals5_whenShouldDealBankerThirdCard_thenReturnsTrueIfPlayerThirdCardIsBetween4And7(
      int playerThirdCardValue,
      int bankerScore,
      boolean expected) {
    when(bankerHand.hasThirdCard()).thenReturn(false);
    when(playerHand.hasNaturalWin()).thenReturn(false);
    when(bankerHand.hasNaturalWin()).thenReturn(false);
    when(playerHand.thirdCard()).thenReturn(Optional.of(playerThirdCardValue));
    when(bankerHand.score()).thenReturn(bankerScore);

    boolean shouldDealBankerThirdCard = dealer.shouldDealBankerThirdCard(playerHand, bankerHand);

    assertEquals(expected, shouldDealBankerThirdCard);
  }

  private static Stream<Arguments> provideArgumentsBankerScoreEquals6() {
    List<Arguments> arguments = new ArrayList<>();
    for (int i : IntStream.range(0, 10).toArray()) {
      arguments.add(Arguments.of(i, 6, Arrays.asList(6, 7).contains(i)));
    }
    return arguments.stream();
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsBankerScoreEquals6")
  public void givenPlayerHasThirdCardAndBankerHasScoreEquals6_whenShouldDealBankerThirdCard_thenReturnsTrueIfPlayerThirdCardIsBetween6And7(
      int playerThirdCardValue,
      int bankerScore,
      boolean expected) {
    when(bankerHand.hasThirdCard()).thenReturn(false);
    when(playerHand.hasNaturalWin()).thenReturn(false);
    when(bankerHand.hasNaturalWin()).thenReturn(false);
    when(playerHand.thirdCard()).thenReturn(Optional.of(playerThirdCardValue));
    when(bankerHand.score()).thenReturn(bankerScore);

    boolean shouldDealBankerThirdCard = dealer.shouldDealBankerThirdCard(playerHand, bankerHand);

    assertEquals(expected, shouldDealBankerThirdCard);
  }

  private static Stream<Arguments> provideArgumentsBankerScoreEquals7() {
    List<Arguments> arguments = new ArrayList<>();
    for (int i : IntStream.range(0, 10).toArray()) {
      arguments.add(Arguments.of(i, 7));
    }
    return arguments.stream();
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsBankerScoreEquals7")
  public void givenPlayerHasThirdCardAndBankerHasScoreEquals7_whenShouldDealBankerThirdCard_thenReturnsFalse(int playerThirdCardValue, int bankerScore) {
    when(bankerHand.hasThirdCard()).thenReturn(false);
    when(playerHand.hasNaturalWin()).thenReturn(false);
    when(bankerHand.hasNaturalWin()).thenReturn(false);
    when(playerHand.thirdCard()).thenReturn(Optional.of(playerThirdCardValue));
    when(bankerHand.score()).thenReturn(bankerScore);

    boolean shouldDealBankerThirdCard = dealer.shouldDealBankerThirdCard(playerHand, bankerHand);

    assertFalse(shouldDealBankerThirdCard);
  }
}
