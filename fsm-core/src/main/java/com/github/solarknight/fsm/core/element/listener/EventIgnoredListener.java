package com.github.solarknight.fsm.core.element.listener;

import com.github.solarknight.fsm.core.element.FSMEvent;

/**
 * @author solarknight created on 18/5/10 上午10:29
 * @version 1.0
 */
@FunctionalInterface
public interface EventIgnoredListener extends EventListener {

  @Override
  void onEventIgnored(FSMEvent event);
}
