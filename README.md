# fsm-engine

Lightweight FSM engine with clear syntax and low cost

## Example

```java
public class Car implements FSMMixin {

  @StateField
  private State state;
  private int speed;

  {
    startWith(State.COLD)
        .transition(fixedRule().when(State.COLD).occur(Event.IGNITE).perform(Car::igniteInCold).transfer(State.STARTED))
        .transition(fixedRule().when(State.STARTED).occur(Event.UPSHIFT).perform(Car::upshiftInStart).transfer(State.RUNNING))
        .bind(this.getClass());
  }

  private void igniteInCold() {
    speed = 0;
    System.out.println("Fire!");
  }

  private void upshiftInStart() {
    speed += 10;
    System.out.println("Current speed is " + speed);
  }
  
  enum State implements FSMState {
    COLD, STARTED, RUNNING
  }

  enum Event implements FSMEvent {
    IGNITE, UPSHIFT
  }
}
```
