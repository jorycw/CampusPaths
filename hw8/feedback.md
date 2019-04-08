### HW8 Feedback

**CSE 331 18sp**

**Name:** Jory Cade Williams (jorycw)

### Score: 68/72
---
**Problem 0 - MVC:** 5/5

- We were hoping for you to distinguish between view and controller, but it's
  not a big deal. (-0)
- A better design would separate the view and controller at least by method by factoring all the print statements out into static view methods. (-0)

**Problems 1-3: Campus Paths:** 54/55

- Correctness: 35/35
- Style: 19/20
  - Cardinal directions should be computed in the model and represented by enums. (-0)
  - Missing AF/RI in Coord class. (-1)
  - Do not completely disable your checkRep––leave the constant time checks
    outside the debug flag check. (-0)
  - By the way, it is impossible for 'this' to be null in any context in Java.

**Problem 4 - Testing:** 9/10

- CampusPathsTest is really weak––you should check the actual return value or
  side effects of the thing your testing, not just check if it's null or if it
  successfully ran without any error. (-1)

**Turnin:** 2/2

