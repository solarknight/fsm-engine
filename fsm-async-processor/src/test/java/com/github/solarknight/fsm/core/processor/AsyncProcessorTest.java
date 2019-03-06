package com.github.solarknight.fsm.core.processor;

import com.github.solarknight.fsm.core.core.FSMBuilder;
import com.github.solarknight.fsm.core.core.FSMFactory;
import org.junit.Test;

import com.github.solarknight.fsm.core.annotation.StateField;
import com.github.solarknight.fsm.core.element.FSMEvent;
import com.github.solarknight.fsm.core.element.FSMMixin;
import com.github.solarknight.fsm.core.element.FSMState;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * @author solarknight created on 18/5/7 下午4:48
 * @version 1.0
 */
public class AsyncProcessorTest {

  @Test
  public void testStateInit() {
    Car car = FSMFactory.create(Car.class);

    assertNotNull(car);
    assertSame(car.getState(), Car.State.COLD);
  }

  @Test(expected = AssertionError.class)
  public void testStateTransfer() {
    Car car = FSMFactory.create(Car.class);
    car.start();

    assertSame(car.getState(), Car.State.STARTED);
  }

  @Test
  public void testStateTransfer2() throws InterruptedException {
    Car car = FSMFactory.create(Car.class);
    car.start();

    Thread.sleep(1000);
    assertSame(car.getState(), Car.State.STARTED);
  }

  @Test
  public void testConcurrentEvent() throws InterruptedException {
    Car car = FSMFactory.create(Car.class);
    new Thread(car::start).start();

    Thread.sleep(200);
    new Thread(car::upshift).start();

    Thread.sleep(1500);
    assertSame(car.getState(), Car.State.RUNNING);
    assertSame(car.getSpeed(), 10);
  }

  public static class Car implements FSMMixin {

    @StateField
    private volatile State state;
    private volatile int speed;

    {
      FSMBuilder.startWith(State.COLD)
          .transition(FSMBuilder.fixedRule().when(State.COLD).occur(Event.IGNITE).perform(Car::igniteInCold).transfer(State.STARTED))
          .transition(FSMBuilder.fixedRule().when(State.STARTED).occur(Event.UPSHIFT).perform(Car::upshiftInStart).transfer(State.RUNNING))
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

    private void sleep(long ms) {
      try {
        Thread.sleep(ms);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    public State getState() {
      return state;
    }

    public int getSpeed() {
      return speed;
    }

    public enum State implements FSMState {
      COLD, STARTED, RUNNING
    }

    public enum Event implements FSMEvent {
      IGNITE, UPSHIFT
    }
  }
}