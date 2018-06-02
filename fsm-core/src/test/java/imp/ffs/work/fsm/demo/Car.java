package imp.ffs.work.fsm.demo;

import imp.ffs.work.fsm.annotation.StateField;
import imp.ffs.work.fsm.element.FSMEvent;
import imp.ffs.work.fsm.element.FSMState;
import imp.ffs.work.fsm.element.listener.EventIgnoredListener;
import imp.ffs.work.fsm.element.listener.StateEnteredListener;
import imp.ffs.work.fsm.core.FSMixin;

import static imp.ffs.work.fsm.core.FSMBuilder.dynamicRule;
import static imp.ffs.work.fsm.core.FSMBuilder.fixedRule;
import static imp.ffs.work.fsm.core.FSMBuilder.startWith;

/**
 * @author peiheng.zph created on 18/5/7 下午4:55
 * @version 1.0
 */
public class Car implements FSMixin {

  public enum State implements FSMState {
    COLD, STARTED, RUNNING, FLYING
  }

  public enum Event implements FSMEvent {
    IGNITE, UPSHIFT, DOWNSHIFT
  }

  {
    startWith(State.COLD)
        .transition(fixedRule().when(State.COLD).occur(Event.IGNITE).perform(this::igniteInCold).transfer(State.STARTED))
        .transition(fixedRule().when(State.STARTED).occur(Event.UPSHIFT).perform(this::upshiftInStart).transfer(State.RUNNING))
        .transition(dynamicRule().when(State.RUNNING).occur(Event.UPSHIFT).perform(this::upshiftInStart).transfer(this::stateWhenUpshift))
        .addStateListener((StateEnteredListener) System.out::println)
        .addEventListener((EventIgnoredListener) System.out::println)
        .bind(this.getClass());
  }

  private static final int MAX_SPEED = 100;

  @StateField
  private State state;

  private int speed;

  public void start() {
    send(Event.IGNITE);
  }

  public void upshift() {
    send(Event.UPSHIFT);
  }

  private void igniteInCold() {
    System.out.println("Fire!");
    speed = 0;
  }

  private void upshiftInStart() {
    speed += 10;
    System.out.println("Current speed is " + speed);
  }

  private State stateWhenUpshift() {
    return speed >= MAX_SPEED ? State.FLYING : State.RUNNING;
  }

  public State getState() {
    return state;
  }

  public int getSpeed() {
    return speed;
  }
}
