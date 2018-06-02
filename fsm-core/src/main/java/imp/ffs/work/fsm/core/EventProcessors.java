package imp.ffs.work.fsm.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;

import imp.ffs.work.fsm.element.EventProcessor;
import imp.ffs.work.fsm.element.FSMAction;
import imp.ffs.work.fsm.element.FSMEvent;
import imp.ffs.work.fsm.element.FSMState;
import imp.ffs.work.fsm.element.TransitionContext;
import imp.ffs.work.fsm.element.TransitionRule;

/**
 * @author peiheng.zph created on 18-6-2 下午11:00
 * @version 1.0
 */
public class EventProcessors implements EventProcessor {
  private static final Logger logger = LoggerFactory.getLogger(EventProcessors.class);

  private ServiceLoader<EventProcessor> loader;
  private boolean useDefault = false;

  private EventProcessors() {
    loader = ServiceLoader.load(EventProcessor.class);

    if (loader.iterator().hasNext()) {
      for (EventProcessor processor : loader) {
        logger.debug("Load external event processor, class {}", processor.getClass().getCanonicalName());
      }
    } else {
      useDefault = true;
      logger.debug("Found no external event processor, use default one");
    }
  }

  public static EventProcessors getInstance() {
    return Holder.instance;
  }

  @Override
  public <T extends FSMixin> void process(T t, FSMEvent event) {
    if (useDefault) {
      processByDefault(t, event);
    } else {
      processByExternal(t, event);
    }
  }

  private <T extends FSMixin> void processByDefault(T t, FSMEvent event) {
    SimpleProcessor.instance.process(t, event);
  }

  private <T extends FSMixin> void processByExternal(T t, FSMEvent event) {
    for (EventProcessor processor : loader) {
      try {
        processor.process(t, event);
      } catch (Exception e) {
        logger.error("Processor {} encountered a problem when process event {}", processor, event);
        throw e;
      }
    }
  }

  private static class Holder {
    private static EventProcessors instance = new EventProcessors();
  }

  private static class SimpleProcessor implements EventProcessor {
    private static final Logger logger = LoggerFactory.getLogger(SimpleProcessor.class);
    private static SimpleProcessor instance = new SimpleProcessor();

    @Override
    public <T extends FSMixin> void process(T t, FSMEvent event) {
      Class<? extends FSMixin> clazz = t.getClass();
      Optional<FSMModel> optionalModel = FSMRegistry.getModel(clazz);

      if (!optionalModel.isPresent()) {
        throw new IllegalStateException("Class " + clazz.getSimpleName() + " not registered as a FSM");
      }

      process(t, optionalModel.get(), event);
    }

    private static <T extends FSMixin> void process(T t, FSMModel model, FSMEvent event) {
      onEventSent(model, event);

      FSMState curState = getCurrentState(t, model);
      Optional<TransitionRule> optionalRule = findMatchedRule(model, curState, event);
      if (!optionalRule.isPresent()) {
        onEventIgnored(model, event);
        return;
      }

      TransitionContext context = buildTransitionContext(curState, event, optionalRule.get());
      performFSMAction(context);
      transferFSMState(t, model, context.matchedRule().toState());

      triggerStateListener(model, context);
      onEventHandled(model, event);
    }

    private static <T extends FSMixin> FSMState getCurrentState(T t, FSMModel model) {
      try {
        return (FSMState) model.getStateGetter().invoke(t);
      } catch (Throwable throwable) {
        throw new RuntimeException(throwable);
      }
    }

    private static <T extends FSMixin> void transferFSMState(T t, FSMModel model, FSMState state) {
      try {
        model.getStateSetter().invoke(t, state);
      } catch (Throwable throwable) {
        throw new RuntimeException(throwable);
      }
    }

    private static Optional<TransitionRule> findMatchedRule(FSMModel model, FSMState curState, FSMEvent event) {
      return model.getTransitionRules().stream()
          .filter(it -> Objects.equals(it.fromState(), curState) && Objects.equals(it.event(), event))
          .findFirst();
    }

    private static TransitionContext buildTransitionContext(FSMState fromState, FSMEvent event, TransitionRule rule) {
      return SimpleContext.create(fromState, event, rule);
    }

    private static void onEventSent(FSMModel model, FSMEvent event) {
      logger.debug("Sent event {}", event);
      model.getEventListeners().forEach(it -> it.onEventSent(event));
    }

    private static void onEventIgnored(FSMModel model, FSMEvent event) {
      logger.debug("Ignored event {}", event);
      model.getEventListeners().forEach(it -> it.onEventIgnored(event));
    }

    private static void onEventHandled(FSMModel model, FSMEvent event) {
      logger.debug("Handled event {}", event);
      model.getEventListeners().forEach(it -> it.onEventHandled(event));
    }

    private static void performFSMAction(TransitionContext context) {
      if (context.matchedRule().action().isPresent()) {
        FSMAction action = context.matchedRule().action().get();
        action.perform(context);
      }
    }

    private static void triggerStateListener(FSMModel model, TransitionContext context) {
      onStateExited(model, context.fromState());
      onStateEntered(model, context.matchedRule().toState());
      onStateChanged(model, context.fromState(), context.matchedRule().toState());
    }

    private static void onStateExited(FSMModel model, FSMState state) {
      logger.debug("Exited state {}", state);
      model.getStateListeners().forEach(it -> it.onStateExited(state));
    }

    private static void onStateEntered(FSMModel model, FSMState state) {
      logger.debug("Entered state {}", state);
      model.getStateListeners().forEach(it -> it.onStateEntered(state));
    }

    private static void onStateChanged(FSMModel model, FSMState fromState, FSMState toState) {
      logger.debug("State changed from {} to {}", fromState, toState);
      model.getStateListeners().forEach(it -> it.onStateChanged(fromState, toState));
    }

    static class SimpleContext implements TransitionContext {
      private FSMState fromState;
      private FSMEvent event;
      private TransitionRule rule;

      static TransitionContext create(FSMState fromState, FSMEvent event, TransitionRule rule) {
        SimpleContext context = new SimpleContext();
        context.fromState = fromState;
        context.event = event;
        context.rule = rule;
        return context;
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
}