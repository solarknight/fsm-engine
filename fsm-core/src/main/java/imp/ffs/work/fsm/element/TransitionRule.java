package imp.ffs.work.fsm.element;

import java.util.Optional;

/**
 * @author peiheng.zph created on 18/5/24 下午8:44
 * @version 1.0
 */
public interface TransitionRule {

  FSMState fromState();

  FSMEvent event();

  Optional<FSMAction> action();

  FSMState toState();
}
