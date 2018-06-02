package imp.ffs.work.fsm.core;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import imp.ffs.work.fsm.annotation.StateField;
import imp.ffs.work.fsm.annotation.StateGetter;
import imp.ffs.work.fsm.annotation.StateSetter;
import imp.ffs.work.fsm.element.FSMState;
import imp.ffs.work.fsm.element.TransitionRule;
import imp.ffs.work.fsm.element.listener.EventListener;
import imp.ffs.work.fsm.element.listener.StateListener;
import imp.ffs.work.fsm.util.Pair;

/**
 * @author peiheng.zph created on 18/5/10 下午8:00
 * @version 1.0
 */
public class FSMModel {

  private static final MethodHandles.Lookup lookup = MethodHandles.lookup();

  private Class<? extends FSMixin> clazz;
  private FSMState initialState;
  private MethodHandle stateGetter;
  private MethodHandle stateSetter;

  private List<TransitionRule> transitionRules;
  private List<StateListener> stateListeners;
  private List<EventListener> eventListeners;

  public static FSMModel from(FSMBuilder fsmBuilder) {
    FSMModel model = new FSMModel();
    fillAttributes(model, fsmBuilder);
    fillStateHandle(model, fsmBuilder.getBindClazz());
    return model;
  }

  private static void fillAttributes(FSMModel model, FSMBuilder fsmBuilder) {
    model.setClazz(fsmBuilder.getBindClazz());
    model.setInitialState(fsmBuilder.getInitialState());
    model.setTransitionRules(fsmBuilder.getTransitionRules());
    model.setStateListeners(fsmBuilder.getStateListeners());
    model.setEventListeners(fsmBuilder.getEventListeners());
  }

  private static void fillStateHandle(FSMModel model, Class<? extends FSMixin> clazz) {
    Pair<MethodHandle, MethodHandle> pair;
    try {
      pair = parseStateHandleFromField(clazz);
      if (pair == Pair.EMPTY) {
        pair = parseStateHandleFromMethod(clazz);
      }
    } catch (Exception e) {
      throw new IllegalStateException("Parse FSM state accessor failed");
    }

    if (pair == Pair.EMPTY) {
      throw new IllegalStateException("Can't parse FSM state accessor");
    }

    model.setStateGetter(pair.fst);
    model.setStateSetter(pair.snd);
  }

  private static Pair<MethodHandle, MethodHandle> parseStateHandleFromField(Class<? extends FSMixin> clazz) throws IllegalAccessException {
    Optional<Field> optional = Arrays.stream(clazz.getDeclaredFields())
        .filter(it -> it.getAnnotation(StateField.class) != null)
        .findFirst();

    if (!optional.isPresent()) {
      return Pair.empty();
    }

    Field field = optional.get();
    field.setAccessible(true);
    return Pair.of(lookup.unreflectGetter(field), lookup.unreflectSetter(field));
  }

  private static Pair<MethodHandle, MethodHandle> parseStateHandleFromMethod(Class<? extends FSMixin> clazz) throws IllegalAccessException {
    Optional<Method> optionalGetter = Arrays.stream(clazz.getDeclaredMethods())
        .filter(it -> it.getAnnotation(StateGetter.class) != null)
        .findFirst();
    Optional<Method> optionalSetter = Arrays.stream(clazz.getDeclaredMethods())
        .filter(it -> it.getAnnotation(StateSetter.class) != null)
        .findFirst();

    if (!optionalGetter.isPresent() || !optionalSetter.isPresent()) {
      return Pair.empty();
    }

    return Pair.of(lookup.unreflect(optionalGetter.get()), lookup.unreflect(optionalSetter.get()));
  }

  public Class<? extends FSMixin> getClazz() {
    return clazz;
  }

  public FSMModel setClazz(Class<? extends FSMixin> clazz) {
    this.clazz = clazz;
    return this;
  }

  public FSMState getInitialState() {
    return initialState;
  }

  public FSMModel setInitialState(FSMState initialState) {
    this.initialState = initialState;
    return this;
  }

  public MethodHandle getStateGetter() {
    return stateGetter;
  }

  public void setStateGetter(MethodHandle stateGetter) {
    this.stateGetter = stateGetter;
  }

  public MethodHandle getStateSetter() {
    return stateSetter;
  }

  public void setStateSetter(MethodHandle stateSetter) {
    this.stateSetter = stateSetter;
  }

  public List<TransitionRule> getTransitionRules() {
    return transitionRules;
  }

  public FSMModel setTransitionRules(List<TransitionRule> transitionRules) {
    this.transitionRules = transitionRules;
    return this;
  }

  public List<StateListener> getStateListeners() {
    return stateListeners;
  }

  public FSMModel setStateListeners(List<StateListener> stateListeners) {
    this.stateListeners = stateListeners;
    return this;
  }

  public List<EventListener> getEventListeners() {
    return eventListeners;
  }

  public FSMModel setEventListeners(List<EventListener> eventListeners) {
    this.eventListeners = eventListeners;
    return this;
  }
}
