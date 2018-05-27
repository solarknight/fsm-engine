package imp.ffs.work.fsm.core.listener;

import imp.ffs.work.fsm.core.FSMEvent;

/**
 * @author peiheng.zph created on 18/5/10 上午10:28
 * @version 1.0
 */
@FunctionalInterface
public interface EventHandledListener extends EventListener {

  @Override
  void onEventHandled(FSMEvent event);
}
