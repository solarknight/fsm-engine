package imp.ffs.work.fsm.core.listener;

import imp.ffs.work.fsm.core.FSMState;

/**
 * @author peiheng.zph created on 18/5/10 上午10:32
 * @version 1.0
 */
@FunctionalInterface
public interface StateExitedListener extends StateListener {

  void onStateExited(FSMState state);
}
