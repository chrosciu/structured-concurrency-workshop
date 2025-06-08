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

Theoretically, these three dishes can be prepared in parallel (yes, I know—just theoretically :)).  

**Your task is to simulate this process using structured concurrency**. 

To make it testable, it should be assumed that preparing each of the above takes a maximum of 10 seconds (ha ha ha).

## Task 2 - Not Everything in Life Goes as Planned

Sometimes even the best chef can have a bad day, and there's nothing that can be done about it!

Modify the code from the previous task so that at least one of the dishes cannot be prepared (e.g., the fries get burned).

Observe what happens to the process of preparing the remaining dishes in such a case.

## Task 3 - Better Something Than Nothing

When the guests are starving, they will be satisfied even with the simplest dish :)

Modify the code from the previous task again to ignore the errors that occur during the preparation of dishes and serve all the dishes that can be successfully prepared.

## Task 4 - No drinks, no life :)

A small disclaimer: this is absolutely not about alcohol :) But as might be expected, a grill party cannot happen "dry"—drinks are definitely needed!

Their delivery will be handled by the second guest—Bob. He is incredibly lazy (and stingy), so he decided not to spend much money and to do as little as possible.
To achieve this, it was decided to send his three children—each to a different store:
- the first child was asked to by Coke
- the second one — Fanta
- the third one — Sprite

It was also assumed that the child who manages to buy a drink first will notify the others to return home without making purchases (why spend so much money—one drink is enough for all the guests :)).

**Your task is to model the process described above.**

As part of the task, a situation where a maximum time is set for making the purchase should also be simulated — if none of the children manage to buy a drink within this time, an error should be reported.

## Task 5 - Too late in the day

Everything in life has its time limit, and exceeding it may render further actions meaningless :(

Modify the code from the previous task to set a maximum allowable time for shopping - if none of the children manage to complete their task within this time, the entire process is considered a failure.

## Task 6 - One grill to unite them all :)

Both Alice and Bob have already been assigned their tasks, but for the third guest—Carol—the situation is different! Her only concern is choosing the right outfit—which, of course, as is known, takes time :) 

However, the situation is the same for all guests - you complete your tasks and then head to the party.

**Your task is to simulate the process of guests arrival.**

Of course, each person performs their tasks independently of the others; additionally, if any guest fails to complete their part, the entire party is canceled.

## Task 7 - Even Bad News Is Better Than None

Not everyone will always manage to complete their tasks and make it to the party, but the most important thing is to let the host know in such a situation!

Modify the code from the previous task to wait until each guest knows whether they managed to arrive or not.

## Task 8 - It Takes Two to Tango :)

Sometimes, for the party to be successful, it is enough for more than one person to show up :)

Modify the code from the previous task again so that the party takes place when at least two out of three people arrive.
