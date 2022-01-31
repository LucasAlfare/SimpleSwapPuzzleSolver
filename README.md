# The puzzle:
- each move just swaps its related items;
- the solved state is shown below.

               0=U
             ------
           | 0    1 |
      3=L  |        |   1=R
           | 3    2 |
             ------
               2=D

Performing a "U" move should brings the puzzle to the following state:

               0=U
             ------
           | 1    0 |
      3=L  |        |   1=R
           | 3    2 |
             ------
               2=D

The other moves follows the same idea.

# The algorithm

The algorithm to search solutions is quite simple even it seems a bitt lazy in a first look. Then, the solution is found through two different "phases": a "indexing phase" and a "searching phase".

# Indexing

Indexing means that the program should map all possible different states of the puzzle to a single number, in this case to a number indicating the lexicographic order of the state inside the total number of possible states.

To this example puzzle, we can found the total number of states just by looking the amount of different "pieces". If we do a factorial operation of the all 4 different pieces we get the result ```24```.

So, knowning the puzzle only has 24 different states we can create a array of length 24, where each index is a "encrypted version" of one of that states and the associated value is a depth value.

# The depth

Depth is a concept used to measure how far a state is from the solved state. For example, if we take a solved state, apply a "U" move on it, the result state will be "1 move" far from the solved one, because we known that we just need apply a "U" move to lead the resulting state to the solved one.

Knowning this, each possible state of a puzzle (and yes, this is valid to all existing puzzles which envolves moving) can have this value associated, which is the key concept to, after all, search the final solution.

# Searching

After all indexings, finding the solution is easy.

We can take a state and just check which moves can lead that state to a state near to the solved state and, of course, to measure that "distance" we can use the depthes previously indexed.

# Scrambling

As a final approach in this context we can genearate good scrambles sequences based on that solutioning.

For example, if you have a scrambled state and get its solution you can note that the reverse of this solution is a sequeence that brings a solved puzzle to the state that was solved.

Finnally, knowning all states have a index associated to it, we can just generate a random number between 0 and N (where N is the number of different states) and solve this state, reverse its solution and, finllay, get a good scramble.

But we know that, in this study puzzle that only have 24 different states, generating scrambles is not to interesting, once everyone could just memorize all of those states kkkkkk
