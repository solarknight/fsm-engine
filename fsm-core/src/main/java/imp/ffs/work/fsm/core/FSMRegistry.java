package imp.ffs.work.fsm.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import imp.ffs.work.fsm.element.FSMMixin;

/**
 * @author peiheng.zph created on 18/5/7 下午5:44
 * @version 1.0
 */
public class FSMRegistry {
  private static final Logger logger = LoggerFactory.getLogger(FSMRegistry.class);

  private static ConcurrentHashMap<Class<? extends FSMMixin>, FSMModel> registryMap = new ConcurrentHashMap<>();

  public static Optional<FSMModel> getModel(Class<? extends FSMMixin> clazz) {
    return Optional.ofNullable(registryMap.get(clazz));
  }

  static void register(FSMBuilder fsmBuilder) {
    if (isAlreadyRegistered(fsmBuilder.getBindClazz())) {
      logger.debug("Skip already registered class {}", fsmBuilder.getBindClazz().getCanonicalName());
      return;
    }

    FSMModel model = FSMModel.from(fsmBuilder);
    registryMap.putIfAbsent(fsmBuilder.getBindClazz(), model);
    logger.info("Register new FSM success, class {}", fsmBuilder.getBindClazz().getCanonicalName());
  }

  private static boolean isAlreadyRegistered(Class<? extends FSMMixin> clazz) {
    return registryMap.containsKey(clazz);
  }
}
