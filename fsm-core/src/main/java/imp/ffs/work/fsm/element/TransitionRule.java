package imp.ffs.work.fsm.element;

import java.util.Optional;

/**
 * @author solarknight created on 18/5/24 下午8:44
 * @version 1.0
 */
public interface TransitionRule {

  FSMState fromState();

  FSMEvent event();

  Optional<FSMAction> action();

  FSMState toState(FSMMixin mixin);
}
