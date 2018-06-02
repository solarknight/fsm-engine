package imp.ffs.work.fsm.element.listener;

import imp.ffs.work.fsm.element.FSMState;

/**
 * @author peiheng.zph created on 18/5/10 上午10:30
 * @version 1.0
 */
@FunctionalInterface
public interface StateChangedListener extends StateListener {

  void onStateChanged(FSMState from, FSMState to);
}
