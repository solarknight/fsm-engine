package imp.ffs.work.fsm.processor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import imp.ffs.work.fsm.core.FSMixin;
import imp.ffs.work.fsm.core.SimpleEventProcessor;
import imp.ffs.work.fsm.element.FSMEvent;

/**
 * @author peiheng.zph created on 18-6-3 上午12:11
 * @version 1.0
 */
public class AsyncProcessor extends SimpleEventProcessor {

  private EventPipeline pipeline = new EventPipeline();
  private ExecutorService executor = Executors.newSingleThreadExecutor();
  private volatile boolean started;

  public AsyncProcessor() {
    started = true;
    executor.execute(this::processEvent);
  }

  @Override
  public <T extends FSMixin> void process(T t, FSMEvent event) {
    CompositeEvent compositeEvent = CompositeEvent.create(t, event);
    pipeline.enqueue(compositeEvent);
  }

  private void processEvent() {
    while (started) {
      CompositeEvent ce = (CompositeEvent) pipeline.dequeue();
      super.process(ce.getTarget(), ce.getEvent());
    }
  }
}
