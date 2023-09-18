package ca.ulaval.glo4002.characterisation;

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

public class CharacterizeMeTest {

  public static final int SAMPLE_SIZE = 1000;

  private CharacterizeMe cut;

  @BeforeEach
  public void setUp() {
    cut = new CharacterizeMe();
  }

  private static Stream<Arguments> provideArgumentsBsLength3() {
    List<Arguments> arguments = new ArrayList<>();
    for (MyStruct ps : getAllPsCombinations()) {
      for (MyStruct bs : getAllThreeCombinations()) {
        arguments.add(Arguments.of(ps, bs, false));
      }
    }
    return sampleArguments(arguments);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsBsLength3")
  void givenBsWithLength3_whenD_thenReturnsFalse(MyStruct ps, MyStruct bs) {
    boolean observed = cut.d(ps, bs);

    assertFalse(observed);
  }

  private static Stream<Arguments> provideArgumentsNs8or9() {
    List<Arguments> arguments = new ArrayList<>();
    for (MyStruct ps : getAllPsCombinations()) {
      for (MyStruct bs : getAllBsCombinations()) {
        if (ps.ns() >= 8 || bs.ns() >= 8) {
          arguments.add(Arguments.of(ps, bs));
        }
      }
    }
    return sampleArguments(arguments);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsNs8or9")
  void givenBsLength2AndBsOrPsHasNs8or9_whenD_thenReturnsFalse(MyStruct ps, MyStruct bs) {
    boolean observed = cut.d(ps, bs);

    assertFalse(observed);
  }

  private static Stream<Arguments> provideArgumentsPsHasNoT() {
    List<Arguments> arguments = new ArrayList<>();
    for (MyStruct ps : getAllTwoCombinations()) {
      for (MyStruct bs : getAllBsCombinations()) {
        if (ps.ns() < 8 && bs.ns() < 8) {
          arguments.add(Arguments.of(ps, bs));
        }
      }
    }
    return sampleArguments(arguments);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsPsHasNoT")
  void givenBsWithLength2AndNeitherNs8or9AndPsHasNoT_whenD_thenReturnsTrueIfBsSIsLessOrEqualsTo5(MyStruct ps, MyStruct bs) {
    boolean expected = bs.s() <= 5;

    boolean observed = cut.d(ps, bs);

    assertEquals(expected, observed);
  }

  private static Stream<Arguments> provideArgumentsPsHasTAndBsSIsLessOrEqualsTo2() {
    List<Arguments> arguments = new ArrayList<>();
    for (MyStruct ps : getAllThreeCombinations()) {
      for (MyStruct bs : getAllBsCombinations()) {
        if (ps.ns() < 8 && bs.s() <= 2) {
          arguments.add(Arguments.of(ps, bs));
        }
      }
    }
    return sampleArguments(arguments);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsPsHasTAndBsSIsLessOrEqualsTo2")
  void givenBsWithLength2AndNeitherNs8or9AndPsHasTAndBsSIsLessOrEqualsTo2_whenD_thenReturnsTrue(MyStruct ps, MyStruct bs) {
    boolean observed = cut.d(ps, bs);

    assertTrue(observed);
  }

  private static Stream<Arguments> provideArgumentsPsHasTAndBsSEquals3() {
    return provideArgumentsPHasTAndBsSEqualsTo(3);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsPsHasTAndBsSEquals3")
  void givenBsWithLength2AndNeitherNs8or9AndPsHasTAndBsSEquals3_whenD_thenReturnsTrueIfPsTIsNot8(MyStruct ps, MyStruct bs) {
    boolean expected = ps.t().get() != 8;

    boolean observed = cut.d(ps, bs);

    assertEquals(expected, observed);
  }

  private static Stream<Arguments> provideArgumentsPsHasTAndBsSEquals4() {
    return provideArgumentsPHasTAndBsSEqualsTo(4);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsPsHasTAndBsSEquals4")
  void givenBsWithLength2AndNeitherNs8or9AndPsHasTAndBsSEquals4_whenD_thenReturnsTrueIfPsTIsBetween2And7(MyStruct ps, MyStruct bs) {
    boolean expected = ps.t().get() >= 2 && ps.t().get() <= 7;

    boolean observed = cut.d(ps, bs);

    assertEquals(expected, observed);
  }

  private static Stream<Arguments> provideArgumentsPsHasTAndBsSEquals5() {
    return provideArgumentsPHasTAndBsSEqualsTo(5);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsPsHasTAndBsSEquals5")
  void givenBsWithLength2AndNeitherNs8or9AndPsHasTAndBsSEquals5_whenD_thenReturnsTrueIfPsTIsBetween4And7(MyStruct ps, MyStruct bs) {
    boolean expected = ps.t().get() >= 4 && ps.t().get() <= 7;

    boolean observed = cut.d(ps, bs);

    assertEquals(expected, observed);
  }

  private static Stream<Arguments> provideArgumentsPsHasTAndBsSEquals6() {
    return provideArgumentsPHasTAndBsSEqualsTo(6);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsPsHasTAndBsSEquals6")
  void givenBsWithLength2AndNeitherNs8or9AndPsHasTAndBsSEquals6_whenD_thenReturnsTrueIfPsTIsBetween6And7(MyStruct ps, MyStruct bs) {
    boolean expected = ps.t().get() >= 6 && ps.t().get() <= 7;

    boolean observed = cut.d(ps, bs);

    assertEquals(expected, observed);
  }

  private static Stream<Arguments> provideArgumentsPsHasTAndBsSEquals7() {
    return provideArgumentsPHasTAndBsSEqualsTo(7);
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsPsHasTAndBsSEquals7")
  void givenBsWithLength2AndNeitherNs8or9AndPsHasTAndBsSEquals7_whenD_thenReturnsFalse(MyStruct ps, MyStruct bs) {
    boolean observed = cut.d(ps, bs);

    assertFalse(observed);
  }

  private static Stream<Arguments> provideArgumentsPHasTAndBsSEqualsTo(int bsS) {
    List<Arguments> arguments = new ArrayList<>();
    for (MyStruct ps : getAllThreeCombinations()) {
      for (MyStruct bs : getAllBsCombinations()) {
        if (ps.ns() < 8 && bs.s() == bsS) {
          arguments.add(Arguments.of(ps, bs));
        }
      }
    }
    return sampleArguments(arguments);
  }

  private static List<MyStruct> getAllPsCombinations() {
    List<MyStruct> combinations = new ArrayList<>();
    combinations.addAll(getAllTwoCombinations());
    combinations.addAll(getAllThreeCombinations());
    return combinations;
  }

  private static List<MyStruct> getAllBsCombinations() {
    return getAllTwoCombinations();
  }

  private static List<MyStruct> getAllTwoCombinations() {
    List<MyStruct> combinations = new ArrayList<>();
    for (int i : IntStream.range(0, 10).toArray()) {
      for (int j : IntStream.range(0, 10).toArray()) {
        combinations.add(new MyStruct(new int[]{i, j}));
      }
    }
    return combinations;
  }

  private static List<MyStruct> getAllThreeCombinations() {
    List<MyStruct> combinations = new ArrayList<>();
    for (int i : IntStream.range(0, 10).toArray()) {
      for (int j : IntStream.range(0, 10).toArray()) {
        for (int k : IntStream.range(0, 10).toArray()) {
          combinations.add(new MyStruct(new int[]{i, j, k}));
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
