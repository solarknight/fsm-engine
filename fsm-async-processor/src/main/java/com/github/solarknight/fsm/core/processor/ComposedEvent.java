package com.github.solarknight.fsm.core.processor;

import com.github.solarknight.fsm.core.element.FSMEvent;
import com.github.solarknight.fsm.core.element.FSMMixin;

/**
 * @author solarknight created on 18-6-3 下午11:39
 * @version 1.0
 */
public class ComposedEvent implements FSMEvent {
  private FSMEvent event;
  private FSMMixin target;

  public static <T extends FSMMixin> ComposedEvent create(T t, FSMEvent event) {
    ComposedEvent composedEvent = new ComposedEvent();
    composedEvent.setEvent(event);
    composedEvent.setTarget(t);
    return composedEvent;
  }

  public FSMEvent getEvent() {
    return event;
  }

  public void setEvent(FSMEvent event) {
    this.event = event;
  }

  public FSMMixin getTarget() {
    return target;
  }

  public void setTarget(FSMMixin target) {
    this.target = target;
  }

}
