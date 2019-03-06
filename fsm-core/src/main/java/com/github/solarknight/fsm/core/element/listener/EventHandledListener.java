package com.github.solarknight.fsm.core.element.listener;

import com.github.solarknight.fsm.core.element.FSMEvent;

/**
 * @author solarknight created on 18/5/10 上午10:28
 * @version 1.0
 */
@FunctionalInterface
public interface EventHandledListener extends EventListener {

  @Override
  void onEventHandled(FSMEvent event);
}
