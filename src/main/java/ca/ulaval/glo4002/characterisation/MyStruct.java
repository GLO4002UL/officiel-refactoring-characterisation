package ca.ulaval.glo4002.characterisation;

import java.util.Arrays;
import java.util.Optional;

public class MyStruct {

  private int[] cs;

  public MyStruct(int[] cs) {
    if (cs.length < 2 || cs.length > 3) {
      throw new RuntimeException("my struct must contains 2 or 3 integers.");
    }
    for (int c : cs) {
      if (c < 0 || c > 9) {
        throw new RuntimeException("all integers passed to c must be greater or equal to 0 and smaller than 10.");
      }
    }
    this.cs = cs;
  }

  @Override
  public String toString() {
    return Arrays.toString(cs);
  }

  public int l() {
    return cs.length;
  }

  public int s() {
    return Arrays.stream(cs).sum() % 10;
  }

  public int ns() {
    return (cs[0] + cs[1]) % 10;
  }

  public Optional<Integer> t() {
    if (cs.length == 3) {
      return Optional.of(cs[2]);
    }
    return Optional.empty();
  }
}
