package imp.ffs.work.fsm.element;

import imp.ffs.work.fsm.core.FSMixin;

/**
 * @author peiheng.zph created on 18-6-2 下午11:03
 * @version 1.0
 */
public interface EventProcessor {

  <T extends FSMixin> void process(T t, FSMEvent event);
}
