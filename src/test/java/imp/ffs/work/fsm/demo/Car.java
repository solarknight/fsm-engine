package imp.ffs.work.fsm.demo;

import imp.ffs.work.fsm.annotation.StateField;
import imp.ffs.work.fsm.core.FSMEvent;
import imp.ffs.work.fsm.core.FSMState;
import imp.ffs.work.fsm.core.FSMixin;
import imp.ffs.work.fsm.core.listener.EventIgnoredListener;
import imp.ffs.work.fsm.core.listener.StateEnteredListener;

import static imp.ffs.work.fsm.factory.FSMBuilder.dynamicRule;
import static imp.ffs.work.fsm.factory.FSMBuilder.fixedRule;
import static imp.ffs.work.fsm.factory.FSMBuilder.startWith;

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
        .transition(fixedRule().when(State.COLD).occur(Event.IGNITE).perform(this::igniteOnCold).transfer(State.STARTED))
        .transition(dynamicRule().when(State.STARTED).occur(Event.UPSHIFT).transfer(this::stateWhenUpshift))
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

  public void upshift(int gears) {
    send(Event.UPSHIFT, gears);
  }

  private void igniteOnCold() {
    System.out.println("Fire!");
    speed = 0;
  }

  public State stateWhenUpshift() {
    return speed >= MAX_SPEED ? State.FLYING : State.RUNNING;
  }


  public State getState() {
    return state;
  }

  public int getSpeed() {
    return speed;
  }
}
