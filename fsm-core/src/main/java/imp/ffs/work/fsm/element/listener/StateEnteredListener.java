package imp.ffs.work.fsm.element.listener;

import imp.ffs.work.fsm.element.FSMState;

/**
 * @author peiheng.zph created on 18/5/10 上午10:31
 * @version 1.0
 */
@FunctionalInterface
public interface StateEnteredListener extends StateListener {

  void onStateEntered(FSMState state);
}
