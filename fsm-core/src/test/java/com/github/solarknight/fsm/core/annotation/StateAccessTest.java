package com.github.solarknight.fsm.core.annotation;

import com.github.solarknight.fsm.core.core.FSMBuilder;
import com.github.solarknight.fsm.core.core.FSMFactory;
import org.junit.Test;

import com.github.solarknight.fsm.core.element.FSMEvent;
import com.github.solarknight.fsm.core.element.FSMMixin;
import com.github.solarknight.fsm.core.element.FSMState;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * @author solarknight created on 18-6-3 下午4:16
 * @version 1.0
 */
public class StateAccessTest {

  @Test
  public void testStateInit() {
    Car car = FSMFactory.create(Car.class);

    assertNotNull(car);
    assertSame(car.getState(), Car.State.COLD);
  }

  @Test
  public void testStateTransfer1() {
    Car car = FSMFactory.create(Car.class);

    assertNotNull(car);
    car.send(Car.Event.IGNITE);
    assertSame(car.getState(), Car.State.STARTED);
  }

  public static class Car implements FSMMixin {

    private State state;
    private int speed;

    {
      FSMBuilder.startWith(State.COLD)
          .transition(FSMBuilder.fixedRule().when(State.COLD).occur(Event.IGNITE).perform(Car::igniteInCold).transfer(State.STARTED))
          .transition(FSMBuilder.fixedRule().when(State.STARTED).occur(Event.UPSHIFT).perform(Car::upshiftInStart).transfer(State.RUNNING))
          .bind(this.getClass());
    }

    private void igniteInCold() {
      System.out.println("Fire!");
      speed = 0;
    }

    private void upshiftInStart() {
      speed += 10;
      System.out.println("Current speed is " + speed);
    }

    @StateGetter
    public State getState() {
      return state;
    }

    @StateSetter
    public void setState(State state) {
      this.state = state;
    }

    public int getSpeed() {
      return speed;
    }

    public void setSpeed(int speed) {
      this.speed = speed;
    }

    enum State implements FSMState {
      COLD, STARTED, RUNNING
    }

    enum Event implements FSMEvent {
      IGNITE, UPSHIFT
    }
  }
}
