package imp.ffs.work.fsm.core.listener;

import java.util.function.Consumer;

import imp.ffs.work.fsm.core.TransitionContext;

/**
 * @author peiheng.zph created on 18/5/9 下午10:16
 * @version 1.0
 */
public interface StateListener extends Consumer<TransitionContext> {

  @Override
  default void accept(TransitionContext transitionContext) {
  }
}
