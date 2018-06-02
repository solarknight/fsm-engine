package imp.ffs.work.fsm.core.rule;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import imp.ffs.work.fsm.element.FSMAction;
import imp.ffs.work.fsm.element.FSMEvent;
import imp.ffs.work.fsm.element.FSMState;
import imp.ffs.work.fsm.element.TransitionRule;

/**
 * @author peiheng.zph created on 18/5/25 下午8:03
 * @version 1.0
 */
public class DynamicRule implements TransitionRule {

  private FSMState fromState;
  private FSMEvent event;
  private FSMAction action;
  private Supplier<FSMState> toStateSupplier;

  public static DynamicRule create() {
    return new DynamicRule();
  }

  public DynamicRule when(FSMState state) {
    this.fromState = state;
    return this;
  }

  public DynamicRule occur(FSMEvent event) {
    this.event = event;
    return this;
  }

  public DynamicRule perform(FSMAction action) {
    this.action = action;
    return this;
  }

  public DynamicRule transfer(Supplier<FSMState> supplier) {
    this.toStateSupplier = supplier;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DynamicRule fixedRule = (DynamicRule) o;
    return Objects.equals(fromState, fixedRule.fromState) &&
        Objects.equals(event, fixedRule.event) &&
        Objects.equals(toStateSupplier, fixedRule.toStateSupplier) &&
        Objects.equals(action, fixedRule.action);
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
    return toStateSupplier.get();
  }
}
