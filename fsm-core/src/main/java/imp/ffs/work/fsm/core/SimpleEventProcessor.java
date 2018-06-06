package imp.ffs.work.fsm.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;

import imp.ffs.work.fsm.element.EventProcessor;
import imp.ffs.work.fsm.element.FSMAction;
import imp.ffs.work.fsm.element.FSMEvent;
import imp.ffs.work.fsm.element.FSMMixin;
import imp.ffs.work.fsm.element.FSMState;
import imp.ffs.work.fsm.element.TransitionContext;
import imp.ffs.work.fsm.element.TransitionRule;

/**
 * @author peiheng.zph created on 18-6-3 下午11:48
 * @version 1.0
 */
public class SimpleEventProcessor implements EventProcessor {
  private static final Logger logger = LoggerFactory.getLogger(SimpleEventProcessor.class);
  static SimpleEventProcessor instance = new SimpleEventProcessor();

  @Override
  public <T extends FSMMixin> void process(T t, FSMEvent event) {
    Class<? extends FSMMixin> clazz = t.getClass();
    Optional<FSMModel> optionalModel = FSMRegistry.getModel(clazz);

    if (!optionalModel.isPresent()) {
      throw new IllegalStateException("Class " + clazz.getSimpleName() + " not registered as a FSM");
    }

    process(t, optionalModel.get(), event);
  }

  private <T extends FSMMixin> void process(T t, FSMModel model, FSMEvent event) {
    onEventSent(model, event);

    FSMState curState = getCurrentState(t, model);
    Optional<TransitionRule> optionalRule = findMatchedRule(model, curState, event);
    if (!optionalRule.isPresent()) {
      onEventIgnored(model, event);
      return;
    }

    TransitionContext<T> context = buildTransitionContext(t, curState, event, optionalRule.get());
    performFSMAction(context);
    transferFSMState(t, model, context.matchedRule().toState(t));

    triggerStateListener(model, context);
    onEventHandled(model, event);
  }

  private <T extends FSMMixin> FSMState getCurrentState(T t, FSMModel model) {
    try {
      return (FSMState) model.getStateGetter().invoke(t);
    } catch (Throwable throwable) {
      throw new RuntimeException(throwable);
    }
  }

  private <T extends FSMMixin> void transferFSMState(T t, FSMModel model, FSMState state) {
    try {
      model.getStateSetter().invoke(t, state);
    } catch (Throwable throwable) {
      throw new RuntimeException(throwable);
    }
  }

  private Optional<TransitionRule> findMatchedRule(FSMModel model, FSMState curState, FSMEvent event) {
    return model.getTransitionRules().stream()
        .filter(it -> Objects.equals(it.fromState(), curState) && Objects.equals(it.event(), event))
        .findFirst();
  }

  private <T extends FSMMixin> TransitionContext<T> buildTransitionContext(T t, FSMState fromState,
                                                                                  FSMEvent event, TransitionRule rule) {
    return SimpleContext.create(t, fromState, event, rule);
  }

  private void onEventSent(FSMModel model, FSMEvent event) {
    logger.debug("Sent event {}", event);
    model.getEventListeners().forEach(it -> it.onEventSent(event));
  }

  private void onEventIgnored(FSMModel model, FSMEvent event) {
    logger.debug("Ignored event {}", event);
    model.getEventListeners().forEach(it -> it.onEventIgnored(event));
  }

  private void onEventHandled(FSMModel model, FSMEvent event) {
    logger.debug("Handled event {}", event);
    model.getEventListeners().forEach(it -> it.onEventHandled(event));
  }

  @SuppressWarnings("unchecked")
  private <T extends FSMMixin> void performFSMAction(TransitionContext<T> context) {
    if (context.matchedRule().action().isPresent()) {
      FSMAction<T> action = context.matchedRule().action().get();
      action.perform(context);
    }
  }

  private void triggerStateListener(FSMModel model, TransitionContext context) {
    onStateExited(model, context.fromState());
    onStateEntered(model, context.matchedRule().toState(context.target()));
    onStateChanged(model, context.fromState(), context.matchedRule().toState(context.target()));
  }

  private void onStateExited(FSMModel model, FSMState state) {
    logger.debug("Exited state {}", state);
    model.getStateListeners().forEach(it -> it.onStateExited(state));
  }

  private void onStateEntered(FSMModel model, FSMState state) {
    logger.debug("Entered state {}", state);
    model.getStateListeners().forEach(it -> it.onStateEntered(state));
  }

  private void onStateChanged(FSMModel model, FSMState fromState, FSMState toState) {
    logger.debug("State changed from {} to {}", fromState, toState);
    model.getStateListeners().forEach(it -> it.onStateChanged(fromState, toState));
  }

  static class SimpleContext<T extends FSMMixin> implements TransitionContext {
    private T target;
    private FSMState fromState;
    private FSMEvent event;
    private TransitionRule rule;

    @SuppressWarnings("unchecked")
    static <T extends FSMMixin> TransitionContext<T> create(T t, FSMState fromState, FSMEvent event, TransitionRule rule) {
      SimpleContext<T> context = new SimpleContext<>();
      context.target = t;
      context.fromState = fromState;
      context.event = event;
      context.rule = rule;
      return context;
    }

    @Override
    public FSMMixin target() {
      return target;
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
    public TransitionRule matchedRule() {
      return rule;
    }
  }
}
