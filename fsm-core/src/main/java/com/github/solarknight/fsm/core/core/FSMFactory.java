package com.github.solarknight.fsm.core.core;

import java.util.Optional;

import com.github.solarknight.fsm.core.element.FSMMixin;

/**
 * @author solarknight created on 18/5/7 下午5:27
 * @version 1.0
 */
public class FSMFactory {

  public static <T extends FSMMixin> T create(Class<T> clazz) {
    final T t;
    try {
      t = clazz.newInstance();
      setInitialState(t, clazz);

    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
    return t;
  }

  private static <T extends FSMMixin> void setInitialState(T t, Class<T> clazz) throws Throwable {
    Optional<FSMModel> optional = FSMRegistry.getModel(clazz);
    if (!optional.isPresent()) {
      throw new IllegalStateException("Class " + clazz.getSimpleName() + " not registered as a FSM");
    }

    FSMModel model = optional.get();
    model.getStateSetter().invoke(t, model.getInitialState());
  }

}
