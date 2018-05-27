package imp.ffs.work.fsm.factory;

import java.util.Optional;

import imp.ffs.work.fsm.core.FSMEvent;
import imp.ffs.work.fsm.core.FSMixin;

/**
 * @author peiheng.zph created on 18/5/26 下午4:42
 * @version 1.0
 */
public class FSMEventProcessor {

  public static void process(Class<? extends FSMixin> clazz, FSMEvent event) {
    Optional<FSMModel> optional = FSMRegistry.getModel(clazz);
    if (!optional.isPresent()) {
      throw new IllegalStateException("Instance class not registered!");
    }

    FSMModel model = optional.get();


  }
}
