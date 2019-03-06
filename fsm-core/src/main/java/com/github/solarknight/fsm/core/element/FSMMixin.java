package com.github.solarknight.fsm.core.element;

import com.github.solarknight.fsm.core.core.EventProcessors;

/**
 * @author solarknight created on 18/5/7 下午5:04
 * @version 1.0
 */
public interface FSMMixin {

  default void send(FSMEvent event) {
    EventProcessors.getInstance().process(this, event);
  }
}
