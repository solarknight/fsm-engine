package imp.ffs.work.fsm.element;

/**
 * @author peiheng.zph created on 18/5/9 下午11:26
 * @version 1.0
 */
public interface TransitionContext<S extends FSMState, E extends FSMEvent> {

  FSMState fromState();

  FSMEvent event();

  TransitionRule matchedRule();
}
