package imp.ffs.work.fsm;

import org.junit.Test;

import imp.ffs.work.fsm.demo.Car;
import imp.ffs.work.fsm.factory.FSMBuilder;
import imp.ffs.work.fsm.factory.FSMFactory;

import static org.junit.Assert.assertTrue;

/**
 * @author peiheng.zph created on 18/5/7 下午4:48
 * @version 1.0
 */
public class FSMTest {

  @Test
  public void testStateTransfer() {
    Car car = FSMFactory.create(Car.class);

    car.start();
    assertTrue(car.getState() == Car.State.STARTED);
  }

  @Test
  public void testFSMBuilder() {
    FSMBuilder.startWith(null);
  }
}