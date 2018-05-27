package imp.ffs.work.fsm.core.listener;

import imp.ffs.work.fsm.core.FSMEvent;

/**
 * @author peiheng.zph created on 18/5/9 下午10:21
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
