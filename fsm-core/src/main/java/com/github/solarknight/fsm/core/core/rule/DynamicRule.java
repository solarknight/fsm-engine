package com.github.solarknight.fsm.core.core.rule;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import com.github.solarknight.fsm.core.element.FSMAction;
import com.github.solarknight.fsm.core.element.FSMEvent;
import com.github.solarknight.fsm.core.element.FSMMixin;
import com.github.solarknight.fsm.core.element.FSMState;
import com.github.solarknight.fsm.core.element.TransitionRule;

/**
 * @author solarknight created on 18/5/25 下午8:03
 * @version 1.0
 */
public class DynamicRule implements TransitionRule {
  private FSMState fromState;
  private FSMEvent event;
  private FSMAction<? extends FSMMixin> action;
  private Function<FSMMixin, FSMState> toStateFunc;

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

  public <T extends FSMMixin> DynamicRule perform(FSMAction<T> action) {
    this.action = action;
    return this;
  }

  @SuppressWarnings("unchecked")
  public <T extends FSMMixin> DynamicRule transfer(Function<T, FSMState> supplier) {
    this.toStateFunc = (Function<FSMMixin, FSMState>) supplier;
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
  public FSMState toState(FSMMixin mixin) {
    return toStateFunc.apply(mixin);
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
        Objects.equals(toStateFunc, fixedRule.toStateFunc) &&
        Objects.equals(action, fixedRule.action);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromState, event, toStateFunc, action);
  }
}
