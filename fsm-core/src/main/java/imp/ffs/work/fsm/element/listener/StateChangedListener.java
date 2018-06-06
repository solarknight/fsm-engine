package imp.ffs.work.fsm.element.listener;

import imp.ffs.work.fsm.element.FSMState;

/**
 * @author solarknight created on 18/5/10 上午10:30
 * @version 1.0
 */
@FunctionalInterface
public interface StateChangedListener extends StateListener {

  @Override
  void onStateChanged(FSMState from, FSMState to);
}
