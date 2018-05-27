package imp.ffs.work.fsm.core.listener;

import imp.ffs.work.fsm.core.FSMState;
import imp.ffs.work.fsm.core.TransitionContext;

/**
 * @author peiheng.zph created on 18/5/10 上午10:30
 * @version 1.0
 */
@FunctionalInterface
public interface StateChangedListener extends StateListener {

  void onStateChanged(FSMState from, FSMState to);

  @Override
  default void accept(TransitionContext transitionContext) {
    this.onStateChanged(transitionContext.fromState(), transitionContext.matchedRule().toState());
  }
}
