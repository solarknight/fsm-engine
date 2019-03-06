package com.github.solarknight.fsm.core.element.listener;

import com.github.solarknight.fsm.core.element.FSMState;

/**
 * @author solarknight created on 18/5/10 上午10:32
 * @version 1.0
 */
@FunctionalInterface
public interface StateExitedListener extends StateListener {

  @Override
  void onStateExited(FSMState state);
}
