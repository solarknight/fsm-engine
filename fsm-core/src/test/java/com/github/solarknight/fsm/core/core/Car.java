package com.github.solarknight.fsm.core.core;

import com.github.solarknight.fsm.core.annotation.StateField;
import com.github.solarknight.fsm.core.element.FSMEvent;
import com.github.solarknight.fsm.core.element.FSMMixin;
import com.github.solarknight.fsm.core.element.FSMState;
import com.github.solarknight.fsm.core.element.listener.EventIgnoredListener;
import com.github.solarknight.fsm.core.element.listener.StateEnteredListener;

/**
 * @author solarknight created on 18/5/7 下午4:55
 * @version 1.0
 */
public class Car implements FSMMixin {

  public static final int MAX_SPEED = 100;

  @StateField
  private State state;
  private int speed;

  {
    FSMBuilder.startWith(State.COLD)
        .transition(FSMBuilder.fixedRule().when(State.COLD).occur(Event.IGNITE).perform(Car::igniteInCold).transfer(State.STARTED))
        .transition(FSMBuilder.fixedRule().when(State.STARTED).occur(Event.UPSHIFT).perform(Car::upshiftInStart).transfer(State.RUNNING))
        .transition(FSMBuilder.dynamicRule().when(State.RUNNING).occur(Event.UPSHIFT).perform(Car::upshiftInStart).transfer(Car::stateWhenUpshift))
        .addStateListener((StateEnteredListener) System.out::println)
        .addEventListener((EventIgnoredListener) System.out::println)
        .persistence(Car::save)
        .bind(this.getClass());
  }

  public void start() {
    send(Event.IGNITE);
  }

  public void upshift() {
    send(Event.UPSHIFT);
  }

  private void igniteInCold() {
    sleep(500);

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

  private void sleep(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void save() {
    System.out.println("Save current state success, state = " + state);
  }

  public enum State implements FSMState {
    COLD, STARTED, RUNNING, FLYING
  }

  public enum Event implements FSMEvent {
    IGNITE, UPSHIFT
  }
}
