package imp.ffs.work.fsm.element.listener;

import imp.ffs.work.fsm.element.FSMEvent;

/**
 * @author solarknight created on 18/5/9 下午10:21
 * @version 1.0
 */
public interface EventListener {

  default void onEventSent(FSMEvent event) {
  }

  default void onEventHandled(FSMEvent event) {
  }

  default void onEventIgnored(FSMEvent event) {
  }
}
