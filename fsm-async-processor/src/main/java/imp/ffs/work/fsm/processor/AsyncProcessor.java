package imp.ffs.work.fsm.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import imp.ffs.work.fsm.core.SimpleEventProcessor;
import imp.ffs.work.fsm.element.FSMEvent;
import imp.ffs.work.fsm.element.FSMMixin;

/**
 * @author solarknight created on 18-6-3 上午12:11
 * @version 1.0
 */
public class AsyncProcessor extends SimpleEventProcessor {
  private static final Logger logger = LoggerFactory.getLogger(AsyncProcessor.class);

  private EventPipeline pipeline = new EventPipeline();
  private ExecutorService executor = Executors.newSingleThreadExecutor();
  private volatile boolean started;

  public AsyncProcessor() {
    started = true;
    executor.execute(this::processEvent);
  }

  @Override
  public <T extends FSMMixin> void process(T t, FSMEvent event) {
    logger.trace("Receive FSM event, target {} event {} ", t, event);

    ComposedEvent composedEvent = ComposedEvent.create(t, event);
    pipeline.enqueue(composedEvent);
  }

  private void processEvent() {
    while (started) {
      ComposedEvent ce = (ComposedEvent) pipeline.dequeue();
      if (ce == null) {
        return;
      }

      logger.trace("Start consume FSM event, target {} event {} ", ce.getTarget(), ce.getEvent());
      super.process(ce.getTarget(), ce.getEvent());
    }
  }
}
