package com.github.solarknight.fsm.core.element.listener;

import com.github.solarknight.fsm.core.element.FSMState;

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
