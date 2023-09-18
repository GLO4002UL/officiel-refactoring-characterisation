package ca.ulaval.glo4002.characterisation.refactor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DealerCharacterizationTest {

  public static final int SAMPLE_SIZE = 1000;

  private Dealer cut;

  @BeforeEach
  public void setUp() {
    cut = new Dealer();
  }

  private static Stream<Arguments> provideArgumentsBsLength3() {
    List<Arguments> arguments = new ArrayList<>();
    for (Hand ps : getAllPsCombinations()) {
      for (Hand bs : getAllThreeCombinations()) {
        arguments.add(Arguments.of(ps, bs, false));
      }
    }
    return sampleArguments(arguments);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsBsLength3")
  void givenBsWithLength3_whenD_thenReturnsFalse(Hand ps, Hand bs) {
    boolean observed = cut.shouldDealBankerThirdCard(ps, bs);

    assertFalse(observed);
  }

  private static Stream<Arguments> provideArgumentsNs8or9() {
    List<Arguments> arguments = new ArrayList<>();
    for (Hand ps : getAllPsCombinations()) {
      for (Hand bs : getAllBsCombinations()) {
        if (ps.hasNaturalWin() || bs.hasNaturalWin()) {
          arguments.add(Arguments.of(ps, bs));
        }
      }
    }
    return sampleArguments(arguments);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsNs8or9")
  void givenBsLength2AndBsOrPsHasNs8or9_whenD_thenReturnsFalse(Hand ps, Hand bs) {
    boolean observed = cut.shouldDealBankerThirdCard(ps, bs);

    assertFalse(observed);
  }

  private static Stream<Arguments> provideArgumentsPsHasNoT() {
    List<Arguments> arguments = new ArrayList<>();
    for (Hand ps : getAllTwoCombinations()) {
      for (Hand bs : getAllBsCombinations()) {
        if (!ps.hasNaturalWin() && bs.hasNaturalWin()) {
          arguments.add(Arguments.of(ps, bs));
        }
      }
    }
    return sampleArguments(arguments);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsPsHasNoT")
  void givenBsWithLength2AndNeitherNs8or9AndPsHasNoT_whenD_thenReturnsTrueIfBsSIsLessOrEqualsTo5(Hand ps, Hand bs) {
    boolean expected = bs.score() <= 5;

    boolean observed = cut.shouldDealBankerThirdCard(ps, bs);

    assertEquals(expected, observed);
  }

  private static Stream<Arguments> provideArgumentsPsHasTAndBsSIsLessOrEqualsTo2() {
    List<Arguments> arguments = new ArrayList<>();
    for (Hand ps : getAllThreeCombinations()) {
      for (Hand bs : getAllBsCombinations()) {
        if (!ps.hasNaturalWin() && bs.score() <= 2) {
          arguments.add(Arguments.of(ps, bs));
        }
      }
    }
    return sampleArguments(arguments);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsPsHasTAndBsSIsLessOrEqualsTo2")
  void givenBsWithLength2AndNeitherNs8or9AndPsHasTAndBsSIsLessOrEqualsTo2_whenD_thenReturnsTrue(Hand ps, Hand bs) {
    boolean observed = cut.shouldDealBankerThirdCard(ps, bs);

    assertTrue(observed);
  }

  private static Stream<Arguments> provideArgumentsPsHasTAndBsSEquals3() {
    return provideArgumentsPHasTAndBsSEqualsTo(3);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsPsHasTAndBsSEquals3")
  void givenBsWithLength2AndNeitherNs8or9AndPsHasTAndBsSEquals3_whenD_thenReturnsTrueIfPsTIsNot8(Hand ps, Hand bs) {
    boolean expected = ps.thirdCard().get() != 8;

    boolean observed = cut.shouldDealBankerThirdCard(ps, bs);

    assertEquals(expected, observed);
  }

  private static Stream<Arguments> provideArgumentsPsHasTAndBsSEquals4() {
    return provideArgumentsPHasTAndBsSEqualsTo(4);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsPsHasTAndBsSEquals4")
  void givenBsWithLength2AndNeitherNs8or9AndPsHasTAndBsSEquals4_whenD_thenReturnsTrueIfPsTIsBetween2And7(Hand ps, Hand bs) {
    boolean expected = ps.thirdCard().get() >= 2 && ps.thirdCard().get() <= 7;

    boolean observed = cut.shouldDealBankerThirdCard(ps, bs);

    assertEquals(expected, observed);
  }

  private static Stream<Arguments> provideArgumentsPsHasTAndBsSEquals5() {
    return provideArgumentsPHasTAndBsSEqualsTo(5);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsPsHasTAndBsSEquals5")
  void givenBsWithLength2AndNeitherNs8or9AndPsHasTAndBsSEquals5_whenD_thenReturnsTrueIfPsTIsBetween4And7(Hand ps, Hand bs) {
    boolean expected = ps.thirdCard().get() >= 4 && ps.thirdCard().get() <= 7;

    boolean observed = cut.shouldDealBankerThirdCard(ps, bs);

    assertEquals(expected, observed);
  }

  private static Stream<Arguments> provideArgumentsPsHasTAndBsSEquals6() {
    return provideArgumentsPHasTAndBsSEqualsTo(6);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsPsHasTAndBsSEquals6")
  void givenBsWithLength2AndNeitherNs8or9AndPsHasTAndBsSEquals6_whenD_thenReturnsTrueIfPsTIsBetween6And7(Hand ps, Hand bs) {
    boolean expected = ps.thirdCard().get() >= 6 && ps.thirdCard().get() <= 7;

    boolean observed = cut.shouldDealBankerThirdCard(ps, bs);

    assertEquals(expected, observed);
  }

  private static Stream<Arguments> provideArgumentsPsHasTAndBsSEquals7() {
    return provideArgumentsPHasTAndBsSEqualsTo(7);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsPsHasTAndBsSEquals7")
  void givenBsWithLength2AndNeitherNs8or9AndPsHasTAndBsSEquals7_whenD_thenReturnsFalse(Hand ps, Hand bs) {
    boolean observed = cut.shouldDealBankerThirdCard(ps, bs);

    assertFalse(observed);
  }

  private static Stream<Arguments> provideArgumentsPHasTAndBsSEqualsTo(int bsS) {
    List<Arguments> arguments = new ArrayList<>();
    for (Hand ps : getAllThreeCombinations()) {
      for (Hand bs : getAllBsCombinations()) {
        if (!ps.hasNaturalWin() && bs.score() == bsS) {
          arguments.add(Arguments.of(ps, bs));
        }
      }
    }
    return sampleArguments(arguments);
  }

  private static List<Hand> getAllPsCombinations() {
    List<Hand> combinations = new ArrayList<>();
    combinations.addAll(getAllTwoCombinations());
    combinations.addAll(getAllThreeCombinations());
    return combinations;
  }

  private static List<Hand> getAllBsCombinations() {
    return getAllTwoCombinations();
  }

  private static List<Hand> getAllTwoCombinations() {
    List<Hand> combinations = new ArrayList<>();
    for (int i : IntStream.range(0, 10).toArray()) {
      for (int j : IntStream.range(0, 10).toArray()) {
        combinations.add(new Hand(new int[]{i, j}));
      }
    }
    return combinations;
  }

  private static List<Hand> getAllThreeCombinations() {
    List<Hand> combinations = new ArrayList<>();
    for (int i : IntStream.range(0, 10).toArray()) {
      for (int j : IntStream.range(0, 10).toArray()) {
        for (int k : IntStream.range(0, 10).toArray()) {
          combinations.add(new Hand(new int[]{i, j, k}));
        }
      }
    }
    return combinations;
  }

  private static Stream<Arguments> sampleArguments(List<Arguments> arguments) {
    Collections.shuffle(arguments);
    return arguments.subList(0, Math.min(arguments.size() - 1, SAMPLE_SIZE)).stream();
  }
}
