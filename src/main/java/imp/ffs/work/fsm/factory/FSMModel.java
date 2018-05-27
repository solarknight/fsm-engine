package imp.ffs.work.fsm.factory;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.util.List;

import imp.ffs.work.fsm.core.FSMState;
import imp.ffs.work.fsm.core.FSMixin;
import imp.ffs.work.fsm.core.TransitionRule;
import imp.ffs.work.fsm.core.listener.EventListener;
import imp.ffs.work.fsm.core.listener.StateListener;
import imp.ffs.work.fsm.util.Pair;

/**
 * @author peiheng.zph created on 18/5/10 下午8:00
 * @version 1.0
 */
public class FSMModel {

  private Class<? extends FSMixin> clazz;
  private FSMState initialState;
  private MethodHandle stateGetter;
  private MethodHandle stateSetter;

  private List<TransitionRule> transitionRules;
  private List<StateListener> stateListeners;
  private List<EventListener> eventListeners;

  public static FSMModel from(FSMBuilder fsmBuilder) {
    FSMModel model = new FSMModel();
    model.setClazz(fsmBuilder.getBindClazz());
    model.setInitialState(fsmBuilder.getInitialState());
    model.setTransitionRules(fsmBuilder.getTransitionRules());
    model.setStateListeners(fsmBuilder.getStateListeners());
    model.setEventListeners(fsmBuilder.getEventListeners());

    parseStateHandle(model, fsmBuilder.getBindClazz());

    return model;
  }

  private static void parseStateHandle(FSMModel model, Class<? extends FSMixin> clazz) {

  }

  private static Pair<MethodHandle, MethodHandle> parseStateHandleFromField(Class<? extends FSMixin> clazz) {
    Field[] fields = clazz.getFields();

    return Pair.of(null, null);
  }


  public void initState() {

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
