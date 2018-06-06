package imp.ffs.work.fsm.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

import imp.ffs.work.fsm.element.EventProcessor;
import imp.ffs.work.fsm.element.FSMEvent;
import imp.ffs.work.fsm.element.FSMMixin;

/**
 * @author solarknight created on 18-6-2 下午11:00
 * @version 1.0
 */
public class EventProcessors implements EventProcessor {
  private static final Logger logger = LoggerFactory.getLogger(EventProcessors.class);

  private ServiceLoader<EventProcessor> loader;
  private boolean useDefault = false;

  private EventProcessors() {
    loader = ServiceLoader.load(EventProcessor.class);

    if (loader.iterator().hasNext()) {
      for (EventProcessor processor : loader) {
        logger.debug("Load external event processor, class {}", processor.getClass().getCanonicalName());
      }
    } else {
      useDefault = true;
      logger.debug("Found no external event processor, use default one");
    }
  }

  public static EventProcessors getInstance() {
    return Holder.instance;
  }

  @Override
  public <T extends FSMMixin> void process(T t, FSMEvent event) {
    if (useDefault) {
      processByDefault(t, event);
    } else {
      processByExternal(t, event);
    }
  }

  private <T extends FSMMixin> void processByDefault(T t, FSMEvent event) {
    SimpleEventProcessor.instance.process(t, event);
  }

  private <T extends FSMMixin> void processByExternal(T t, FSMEvent event) {
    for (EventProcessor processor : loader) {
      try {
        processor.process(t, event);
      } catch (Exception e) {
        logger.error("Processor {} encountered a problem when process event {}", processor, event);
        throw e;
      }
    }
  }

  private static class Holder {
    private static EventProcessors instance = new EventProcessors();
  }

}
