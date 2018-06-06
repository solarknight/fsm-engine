package imp.ffs.work.fsm.element.listener;

import imp.ffs.work.fsm.element.FSMEvent;

/**
 * @author peiheng.zph created on 18/5/10 上午10:29
 * @version 1.0
 */
@FunctionalInterface
public interface EventIgnoredListener extends EventListener {

  @Override
  void onEventIgnored(FSMEvent event);
}
