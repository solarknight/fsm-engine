package imp.ffs.work.fsm.element;

/**
 * @author solarknight created on 18/5/9 下午11:24
 * @version 1.0
 */
@FunctionalInterface
public interface FSMAction<T extends FSMMixin> {

  void perform(T t);

  default void perform(TransitionContext<T> context) {
    perform(context.target());
  }
}
