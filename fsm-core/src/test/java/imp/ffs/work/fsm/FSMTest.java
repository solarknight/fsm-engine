package imp.ffs.work.fsm;

import org.junit.Test;

import java.util.stream.IntStream;

import imp.ffs.work.fsm.demo.Car;
import imp.ffs.work.fsm.core.FSMFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * @author peiheng.zph created on 18/5/7 下午4:48
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

    assertNotNull(car);
    car.start();
    assertSame(car.getState(), Car.State.STARTED);
  }

  @Test
  public void testStateTransfer2() {
    Car car = FSMFactory.create(Car.class);

    assertNotNull(car);
    car.start();
    car.upshift();
    assertSame(car.getState(), Car.State.RUNNING);
  }

  @Test
  public void testStateTransfer3() {
    Car car = FSMFactory.create(Car.class);

    assertNotNull(car);
    car.start();
    IntStream.range(0, 100).boxed().forEach((it) -> car.upshift());
    assertSame(car.getState(), Car.State.FLYING);
  }
}