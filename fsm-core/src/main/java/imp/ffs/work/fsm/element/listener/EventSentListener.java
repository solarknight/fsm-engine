package imp.ffs.work.fsm.element.listener;

import imp.ffs.work.fsm.element.FSMEvent;

/**
 * @author peiheng.zph created on 18/5/10 上午10:23
 * @version 1.0
 */
@FunctionalInterface
public interface EventSentListener extends EventListener {

  @Override
  void onEventSent(FSMEvent event);
}
