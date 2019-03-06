package com.github.solarknight.fsm.core.processor;

import com.github.solarknight.fsm.core.element.FSMEvent;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author solarknight created on 18-6-3 下午4:32
 * @version 1.0
 */
public class EventPipeline {

  private static final Logger logger = LoggerFactory.getLogger(EventPipeline.class);

  private BlockingQueue<FSMEvent> queue = new LinkedBlockingQueue<>();

  public void enqueue(FSMEvent event) {
    try {
      queue.put(event);
    } catch (InterruptedException e) {
      logger.error("Event pipeline enqueue event error", e);
    }
  }

  public FSMEvent dequeue() {
    try {
      return queue.take();
    } catch (InterruptedException e) {
      logger.error("Event pipeline dequeue event error", e);
      return null;
    }
  }
}
