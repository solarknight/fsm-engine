package imp.ffs.work.fsm.factory;

import java.util.Optional;

import imp.ffs.work.fsm.core.FSMixin;

/**
 * @author peiheng.zph created on 18/5/7 下午5:27
 * @version 1.0
 */
public class FSMFactory {

  public static <T extends FSMixin> T create(Class<T> clazz) {
    Optional<FSMModel> optional = FSMRegistry.getModel(clazz);
    if (!optional.isPresent()) {
      throw new IllegalStateException("Instance class not registered!");
    }

    FSMModel model = optional.get();
    final T t;

    try {
      t = clazz.newInstance();
//      model.get


    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    return t;
  }


}
