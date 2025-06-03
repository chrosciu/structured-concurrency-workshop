# Workshop Description

During the structured concurrency workshop, the preparations for a grill party will be modeled.

Such an event is a complex process, so it will be divided into simpler stages. In each of them, a slightly different type of `Joiner` for `StructuredTaskScope` will be used.

To simulate individual preparation stages, the provided `Action` class can be used, but creating custom ones and letting imagination run wild is highly encouraged :)

It is recommended that what happens in the application is logged to the console (with timestamps), e.g., to show when the salad preparation starts.

Ambitious participants are encouraged to write unit tests to verify the simulation's behavior—similar to those already present in the repository.

## Required Software

- JDK 25 (available at https://jdk.java.net/25/ or via a package manager like SDKMAN)
- IntelliJ IDEA (Community version is sufficient)

## Documentation

- https://openjdk.org/jeps/505
- https://download.java.net/java/early_access/loom/docs/api/java.base/java/util/concurrent/StructuredTaskScope.html
- [slides in PDF - TODO]

## Task 1 - Something to snack on :)

No grill party can happen without good food, so the first guest—Alice—was tasked with preparing three dishes:
- salad
- pizza
- fries

Theoretically, these three dishes can be prepared in parallel (yes, I know—just theoretically :))—so such a process should be modeled. To make it testable, it should be assumed that preparing each of the above takes a maximum of 10 seconds (ha ha ha).

As part of the task, a situation where one of the dishes cannot be prepared (e.g., the fries get burned) should also be simulated. Two possible scenarios should be considered:
- failure to prepare any dish ruins the entire process (either all three dishes are ready, or none)—in this case, the remaining dishes should be stopped from being prepared.
- or the dishes that were successfully completed should be served.

## Task 2 - No drinks, no life :)

A small disclaimer: this is absolutely not about alcohol :) But as might be expected, a grill party cannot happen "dry"—drinks are needed!

Their delivery will be handled by the second guest—Bob. He is incredibly lazy (and stingy), so it was decided not to spend much money and to do as little as possible.
To achieve this, it was decided to send his three children—each to a different store:
- the first child was tasked with buying Coca-Cola
- the second—Fanta
- the third—Sprite

It was also assumed that the child who manages to buy a drink first will notify the others to return home without making purchases (why spend so much money—one drink is enough for all the guests :)).

The process described above should be modeled.

As part of the task, a situation where a maximum time is set for making the purchase should also be simulated—if none of the children manage to buy a drink within this time, an error should be reported.

## Task 3 - One grill to unite them all :)

Both Alice and Bob have already been assigned their tasks, but for the third guest—Carol—the situation is different! Her only concern is choosing the right outfit—which, of course, as is known, takes time :)

The process of all guests arriving at the party should be simulated.

Of course, each person performs their tasks independently of the others; additionally, if any guest fails to complete their part, the entire party is canceled.

As part of the task, two other scenarios should also be simulated:
- the party can start if at least two out of three invited guests arrive.
- the party starts regardless of whether the invited guests complete their tasks, but information from each of them on whether they succeeded or not is awaited.