package imp.ffs.work.fsm.factory.rule;

import java.util.Objects;
import java.util.Optional;

import imp.ffs.work.fsm.core.FSMAction;
import imp.ffs.work.fsm.core.FSMEvent;
import imp.ffs.work.fsm.core.FSMState;
import imp.ffs.work.fsm.core.TransitionRule;

/**
 * @author peiheng.zph created on 18/5/25 下午8:03
 * @version 1.0
 */
public class FixedRule implements TransitionRule {
  private FSMState fromState;
  private FSMEvent event;
  private FSMAction action;
  private FSMState toState;

  public static FixedRule create() {
    return new FixedRule();
  }

  public FixedRule when(FSMState state) {
    this.fromState = state;
    return this;
  }

  public FixedRule occur(FSMEvent event) {
    this.event = event;
    return this;
  }

  public FixedRule perform(FSMAction action) {
    this.action = action;
    return this;
  }

  public FixedRule transfer(FSMState state) {
    this.toState = state;
    return this;
  }

  @Override
  public FSMState fromState() {
    return fromState;
  }

  @Override
  public FSMEvent event() {
    return event;
  }

  @Override
  public Optional<FSMAction> action() {
    return Optional.ofNullable(action);
  }

  @Override
  public FSMState toState() {
    return toState;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FixedRule fixedRule = (FixedRule) o;
    return Objects.equals(fromState, fixedRule.fromState) &&
        Objects.equals(event, fixedRule.event) &&
        Objects.equals(toState, fixedRule.toState) &&
        Objects.equals(action, fixedRule.action);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromState, event, toState, action);
  }
}
