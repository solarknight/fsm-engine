package imp.ffs.work.fsm.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import imp.ffs.work.fsm.core.FSMixin;
import imp.ffs.work.fsm.element.EventProcessor;
import imp.ffs.work.fsm.element.FSMEvent;

/**
 * @author peiheng.zph created on 18-6-3 上午12:11
 * @version 1.0
 */
public class SyncEventProcessor implements EventProcessor {
  private static final Logger logger = LoggerFactory.getLogger(SyncEventProcessor.class);


  @Override
  public <T extends FSMixin> void process(T t, FSMEvent event) {

  }
}
