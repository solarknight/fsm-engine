package imp.ffs.work.fsm.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import imp.ffs.work.fsm.core.rule.DynamicRule;
import imp.ffs.work.fsm.core.rule.FixedRule;
import imp.ffs.work.fsm.element.FSMMixin;
import imp.ffs.work.fsm.element.FSMState;
import imp.ffs.work.fsm.element.TransitionRule;
import imp.ffs.work.fsm.element.listener.EventListener;
import imp.ffs.work.fsm.element.listener.StateListener;

/**
 * @author solarknight created on 18/5/10 上午12:16
 * @version 1.0
 */
public class FSMBuilder {

  private Class<? extends FSMMixin> bindClazz;
  private FSMState initialState;
  private List<TransitionRule> transitionRules;
  private List<StateListener> stateListeners;
  private List<EventListener> eventListeners;


  public static FSMBuilder startWith(FSMState state) {
    Objects.requireNonNull(state);

    FSMBuilder builder = FSMBuilder.emptyBuilder();
    builder.initialState = state;
    return builder;
  }

  private static FSMBuilder emptyBuilder() {
    FSMBuilder builder = new FSMBuilder();
    builder.transitionRules = new ArrayList<>();
    builder.stateListeners = new ArrayList<>();
    builder.eventListeners = new ArrayList<>();
    return builder;
  }

  public static FixedRule fixedRule() {
    return FixedRule.create();
  }

  public static DynamicRule dynamicRule() {
    return DynamicRule.create();
  }

  public FSMBuilder transition(TransitionRule rule) {
    Objects.requireNonNull(rule);

    if (!transitionRules.contains(rule)) {
      transitionRules.add(rule);
    }
    return this;
  }

  public FSMBuilder addStateListener(StateListener listener) {
    Objects.requireNonNull(listener);

    this.stateListeners.add(listener);
    return this;
  }

  public FSMBuilder addEventListener(EventListener listener) {
    Objects.requireNonNull(listener);

    this.eventListeners.add(listener);
    return this;
  }

  public void bind(Class<? extends FSMMixin> clazz) {
    Objects.requireNonNull(clazz);

    this.bindClazz = clazz;
    FSMRegistry.register(this);
  }

  public Class<? extends FSMMixin> getBindClazz() {
    return bindClazz;
  }

  public FSMState getInitialState() {
    return initialState;
  }

  public List<TransitionRule> getTransitionRules() {
    return transitionRules;
  }

  public List<StateListener> getStateListeners() {
    return stateListeners;
  }

  public List<EventListener> getEventListeners() {
    return eventListeners;
  }
}
