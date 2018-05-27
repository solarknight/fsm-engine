package imp.ffs.work.fsm.core.listener;

import imp.ffs.work.fsm.core.FSMState;
import imp.ffs.work.fsm.core.TransitionContext;

/**
 * @author peiheng.zph created on 18/5/10 上午10:31
 * @version 1.0
 */
@FunctionalInterface
public interface StateEnteredListener extends StateListener {

  void onStateEntered(FSMState state);

  @Override
  default void accept(TransitionContext transitionContext) {
    this.onStateEntered(transitionContext.matchedRule().toState());
  }
}
