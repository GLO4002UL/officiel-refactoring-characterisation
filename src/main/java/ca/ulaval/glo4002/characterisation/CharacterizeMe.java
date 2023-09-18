package ca.ulaval.glo4002.characterisation;

import java.util.Arrays;

public class CharacterizeMe {

  public boolean d(MyStruct ps, MyStruct bs) {
    if (bs.l() == 3) {
      return false;
    }
    if (ps.ns() > 8 || bs.ns() > 8) {
      return false;
    }
    if (!ps.t().isPresent()) {
      return bs.s() <= 5;
    }
    boolean d = false;
    if (bs.s() <= 2) {
      d = true;
    } else if (bs.s() == 3) {
      return ps.t().get() != 8;
    } else if (bs.s() == 4) {
      d = !Arrays.asList(0, 1, 8, 9).contains(ps.t().get());
    } else if (bs.s() == 5) {
      return ps.t().get() < 8 && ps.t().get() > 3;
    } else if (bs.s() == 6) {
      d = Arrays.asList(6, 7).contains(ps.t().get());
    }
    return d;
  }
}
