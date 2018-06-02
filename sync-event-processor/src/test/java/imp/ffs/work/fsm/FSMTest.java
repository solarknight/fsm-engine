package imp.ffs.work.fsm;

import org.junit.Test;

import imp.ffs.work.fsm.core.FSMFactory;
import imp.ffs.work.fsm.demo.ConcurrentCar;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * @author peiheng.zph created on 18/5/7 下午4:48
 * @version 1.0
 */
public class FSMTest {

  @Test
  public void testStateInit() {
    ConcurrentCar car = FSMFactory.create(ConcurrentCar.class);

    assertNotNull(car);
    assertSame(car.getState(), ConcurrentCar.State.COLD);
  }

}