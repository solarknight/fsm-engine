package imp.ffs.work.fsm.element;

/**
 * @author peiheng.zph created on 18/5/9 下午11:24
 * @version 1.0
 */
@FunctionalInterface
public interface FSMAction {

  void perform();

  default <S extends FSMState, E extends FSMEvent> void perform(TransitionContext<S, E> context) {
    perform();
  }
}
