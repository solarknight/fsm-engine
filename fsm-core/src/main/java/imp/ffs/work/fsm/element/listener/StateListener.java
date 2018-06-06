package imp.ffs.work.fsm.element.listener;

import imp.ffs.work.fsm.element.FSMState;

/**
 * @author solarknight created on 18/5/9 下午10:16
 * @version 1.0
 */
public interface StateListener {

  default void onStateExited(FSMState state) {
  }

  default void onStateEntered(FSMState state) {
  }

  default void onStateChanged(FSMState from, FSMState to) {
  }
}
