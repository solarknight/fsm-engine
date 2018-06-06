package imp.ffs.work.fsm.core;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * @author solarknight created on 18/5/7 下午4:48
 * @version 1.0
 */
public class FSMTest {

  @Test
  public void testStateInit() {
    Car car = FSMFactory.create(Car.class);

    assertNotNull(car);
    assertSame(car.getState(), Car.State.COLD);
  }

  @Test
  public void testStateTransfer1() {
    Car car = FSMFactory.create(Car.class);
    car.start();

    assertSame(car.getState(), Car.State.STARTED);
  }

  @Test
  public void testStateTransfer2() {
    Car car = FSMFactory.create(Car.class);
    car.start();
    car.upshift();

    assertSame(car.getState(), Car.State.RUNNING);
  }

  @Test
  public void testStateTransfer3() {
    Car car = FSMFactory.create(Car.class);
    car.start();
    IntStream.range(0, 100).boxed().forEach((it) -> car.upshift());

    assertSame(car.getState(), Car.State.FLYING);
    assertSame(car.getSpeed(), Car.MAX_SPEED);
  }

  @Test(expected = AssertionError.class)
  public void testConcurrentEvent() throws InterruptedException {
    Car car = FSMFactory.create(Car.class);
    new Thread(car::start).start();

    Thread.sleep(200);
    new Thread(car::upshift).start();

    Thread.sleep(1500);
    assertSame(car.getState(), Car.State.RUNNING);
    assertSame(car.getSpeed(), 10);
  }
}