package imp.ffs.work.fsm.annotation;

import org.junit.Test;

import imp.ffs.work.fsm.core.FSMFactory;
import imp.ffs.work.fsm.core.FSMixin;
import imp.ffs.work.fsm.element.FSMEvent;
import imp.ffs.work.fsm.element.FSMState;

import static imp.ffs.work.fsm.core.FSMBuilder.fixedRule;
import static imp.ffs.work.fsm.core.FSMBuilder.startWith;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * @author peiheng.zph created on 18-6-3 下午4:16
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

  @Test
  public void testStateTransfer2() {
    Car car = FSMFactory.create(Car.class);

    assertNotNull(car);
    car.send(Car.Event.IGNITE);
    car.send(Car.Event.UPSHIFT);
    assertSame(car.getState(), Car.State.RUNNING);
  }

  public static class Car implements FSMixin {

    private State state;
    private int speed;

    {
      startWith(State.COLD)
          .transition(fixedRule().when(State.COLD).occur(Event.IGNITE).perform(this::igniteInCold).transfer(State.STARTED))
          .transition(fixedRule().when(State.STARTED).occur(Event.UPSHIFT).perform(this::upshiftInStart).transfer(State.RUNNING))
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
