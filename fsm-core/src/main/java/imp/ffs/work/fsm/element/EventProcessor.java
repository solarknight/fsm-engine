package imp.ffs.work.fsm.element;

/**
 * @author solarknight created on 18-6-2 下午11:03
 * @version 1.0
 */
public interface EventProcessor {

  <T extends FSMMixin> void process(T t, FSMEvent event);
}
