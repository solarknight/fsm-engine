package com.github.solarknight.fsm.core.core;

import static java.util.Objects.requireNonNull;

import com.github.solarknight.fsm.core.core.rule.DynamicRule;
import com.github.solarknight.fsm.core.core.rule.FixedRule;
import com.github.solarknight.fsm.core.element.FSMMixin;
import com.github.solarknight.fsm.core.element.FSMPersistence;
import com.github.solarknight.fsm.core.element.FSMState;
import com.github.solarknight.fsm.core.element.TransitionRule;
import com.github.solarknight.fsm.core.element.listener.EventListener;
import com.github.solarknight.fsm.core.element.listener.StateListener;
import java.util.ArrayList;
import java.util.List;

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
  private FSMPersistence<? extends FSMMixin> persistence;

  public static FSMBuilder startWith(FSMState state) {
    requireNonNull(state);

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
    requireNonNull(rule);

    if (!transitionRules.contains(rule)) {
      transitionRules.add(rule);
    }
    return this;
  }

  public FSMBuilder addStateListener(StateListener listener) {
    requireNonNull(listener);

    this.stateListeners.add(listener);
    return this;
  }

  public FSMBuilder addEventListener(EventListener listener) {
    requireNonNull(listener);

    this.eventListeners.add(listener);
    return this;
  }

  public <T extends FSMMixin> FSMBuilder persistence(FSMPersistence<T> persistence) {
    requireNonNull(persistence);

    this.persistence = persistence;
    return this;
  }

  public void bind(Class<? extends FSMMixin> clazz) {
    requireNonNull(clazz);

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

  public FSMPersistence<? extends FSMMixin> getPersistence() {
    return persistence;
  }
}
