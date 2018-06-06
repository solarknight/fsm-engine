package imp.ffs.work.fsm.util;

import java.util.Objects;

/**
 * @author solarknight created on 18/5/26 下午7:59
 * @version 1.0
 */
public class Pair<E, T> {

  public static final Pair EMPTY = of(null, null);

  public final E fst;
  public final T snd;

  public Pair(E fst, T snd) {
    this.fst = fst;
    this.snd = snd;
  }

  @SuppressWarnings("unchecked")
  public static <E, T> Pair<E, T> of(E fst, T snd) {
    return new Pair(fst, snd);
  }

  @SuppressWarnings("unchecked")
  public static <E, T> Pair<E, T> empty() {
    return (Pair<E, T>) EMPTY;
  }

  @Override
  public String toString() {
    return "Pair[" + this.fst + "," + this.snd + "]";
  }

  @Override
  public boolean equals(Object var1) {
    return var1 instanceof Pair && Objects.equals(this.fst, ((Pair) var1).fst) && Objects.equals(this.snd, ((Pair) var1).snd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fst, snd) + 1;
  }
}
