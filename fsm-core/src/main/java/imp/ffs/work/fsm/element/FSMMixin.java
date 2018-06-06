package imp.ffs.work.fsm.element;

import imp.ffs.work.fsm.core.EventProcessors;

/**
 * @author peiheng.zph created on 18/5/7 下午5:04
 * @version 1.0
 */
public interface FSMMixin {

  default void send(FSMEvent event) {
    EventProcessors.getInstance().process(this, event);
  }
}
