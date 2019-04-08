### HW6 Feedback

**CSE 331 18sp**

**Name:** Jory Cade Williams (jorycw)

### Score: 74/77
--- 
**Problems 1 & 2: Pathfinding Implementation:** 57/60

- Correctness: 50/50
- Style: 7/10
  - Note that the actual reason that HashSet/HashMap is faster than ArrayList in this case is because in order to find something in an ArrayList, you have to look through every single element. The same is not true for HashSet/HashMap. (CSE 332 goes into detail how HashSet/HashMap does this.)
  - Do not write "throws SomeException" in the method header of main, because the only client that catches that exception is the person running your command line application, who potentially has no idea what a computer is.  If you call any method in main that throws a checked exception, you must catch it and then handle it gracefully. (-1)
  - For complicated algorithms like BFS, internal comments should be there to guide any reader of your code (including future you!) through the main ideas of the code.  This is especially helpful in debugging. (-1)
  - You probably forgot to delete the "might need to flip" part of a comment in findPath. (-0)
  - No Javadoc comment for what happens if there is no path. (-1)
  - The stuff in the @effects tag of findPath should probably go in the method description or @returns. (-0)

**Problem 3 - Testing:** 12/12

  - Good Job!
  - Note that testUnknownChar does, in fact, count as a proper unit test. It just happens to be that it tests for a simple case.

**Problem 4 - Command Line Interface :** 3/3

- Good Job!

**Turnin:** 2/2
