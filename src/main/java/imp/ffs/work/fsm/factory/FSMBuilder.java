package imp.ffs.work.fsm.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import imp.ffs.work.fsm.core.FSMState;
import imp.ffs.work.fsm.core.FSMixin;
import imp.ffs.work.fsm.core.TransitionRule;
import imp.ffs.work.fsm.core.listener.EventListener;
import imp.ffs.work.fsm.core.listener.StateListener;
import imp.ffs.work.fsm.factory.rule.DynamicRule;
import imp.ffs.work.fsm.factory.rule.FixedRule;

/**
 * @author peiheng.zph created on 18/5/10 上午12:16
 * @version 1.0
 */
public class FSMBuilder {

  private Class<? extends FSMixin> bindClazz;
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

  public FSMBuilder transition(TransitionRule rule) {
    Objects.requireNonNull(rule);

    if (!transitionRules.contains(rule)) {
      transitionRules.add(rule);
    }
    return this;
  }

  public static FixedRule fixedRule() {
    return FixedRule.create();
  }

  public static DynamicRule dynamicRule() {
    return DynamicRule.create();
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

  public void bind(Class<? extends FSMixin> clazz) {
    Objects.requireNonNull(clazz);

    this.bindClazz = clazz;
    FSMRegistry.register(this);
  }

  public Class<? extends FSMixin> getBindClazz() {
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
