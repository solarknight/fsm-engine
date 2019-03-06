package com.github.solarknight.fsm.core.element.listener;

import com.github.solarknight.fsm.core.element.FSMState;

/**
 * @author solarknight created on 18/5/10 上午10:31
 * @version 1.0
 */
@FunctionalInterface
public interface StateEnteredListener extends StateListener {

  @Override
  void onStateEntered(FSMState state);
}
