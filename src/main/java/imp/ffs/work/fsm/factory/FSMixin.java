package imp.ffs.work.fsm.factory;

import imp.ffs.work.fsm.core.FSMEvent;

/**
 * @author peiheng.zph created on 18/5/7 下午5:04
 * @version 1.0
 */
public interface FSMixin {

  default void send(FSMEvent event) {
    FSMEventProcessor.process(this, event);
  }
}
