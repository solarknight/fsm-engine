package imp.ffs.work.fsm.processor;

import imp.ffs.work.fsm.core.FSMixin;
import imp.ffs.work.fsm.element.FSMEvent;

/**
 * @author peiheng.zph created on 18-6-3 下午11:39
 * @version 1.0
 */
public class CompositeEvent implements FSMEvent {
  private FSMEvent event;
  private FSMixin target;

  public static <T extends FSMixin> CompositeEvent create(T t, FSMEvent event) {
    CompositeEvent compositeEvent = new CompositeEvent();
    compositeEvent.setEvent(event);
    compositeEvent.setTarget(t);
    return compositeEvent;
  }

  public FSMEvent getEvent() {
    return event;
  }

  public void setEvent(FSMEvent event) {
    this.event = event;
  }

  public FSMixin getTarget() {
    return target;
  }

  public void setTarget(FSMixin target) {
    this.target = target;
  }

}
