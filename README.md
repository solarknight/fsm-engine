# fsm-engine

Lightweight FSM engine with clear syntax and low cost

## Sample

Define

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

Use

```java
Car car = FSMFactory.create(Car.class);
assertSame(car.getState(), Car.State.COLD);

car.send(Car.Event.IGNITE);
assertSame(car.getState(), Car.State.STARTED);
```

## Usage

### Define status and events

Implement `FSMState` `FSMEvent` interface for your status and events.

Use `@StateField` to mark the state field, or `@StateGetter` `@StateSetter` to mark the state Getter/Setter method. The FSM engine will update the state for you.

### Define transition rules

Use `FSMBuilder` to define your fsm transition rules. The definition starts with `startWith` method, and ends with `bind`.

The definition should be placed top of the class as an init block to be more readable.

### Create instance and Use

Use `FSMFactory` to create the FSM instance, and use `send` method inherited from `FSMMixin` to send events.

## Advanced

The FSM engine use java service loader mechanism to find event processors, or use the default one if not founded. 

The default event processor do the job in caller thread, which will cause race condition in multithreaded environment.

The fsm-async-processor module provides a processor which process events in a separate thread, so the state transition will be thread-safe.
