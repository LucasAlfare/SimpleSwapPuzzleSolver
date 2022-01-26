import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings({"PointlessArithmeticExpression", "SameParameterValue"})
public class Main {

    /*
    the puzzle:
    - each move just swaps its related items;
    - the solved state is shown below.

               0=U
             ------
           | 0    1 |
      3=L  |        |   1=R
           | 3    2 |
             ------
               2=D
     */

    static final String[] MOVES = {"U", "R", "D", "L"};
    static final int N = 4 * 2 * 3 * 1; // 4! different states
    static int[] distances = new int[N];

    public static void main(String[] args) {
        // init indexes
        initIndexing();

        // sets up a simple scrambled puzzle to test
        var test = move(new byte[]{0, 1, 2, 3}, 2);
        test = move(test, 3);
        print(test);

        // prints out its optimal solution, using moves names
        System.out.println(formattedSolution(searchSolution(test)));
    }

    private static void initIndexing() {
        Arrays.fill(distances, -1);
        int depth = 0;
        distances[0] = depth;
        int nVisited;
        while (true) {
            nVisited = 0;
            for (int i = 0; i < distances.length; i++) {
                if (distances[i] == depth) {
                    byte[] state = indexToPermutation(i, 4);

                    for (int j = 0; j < 4; j++) {
                        byte[] nextState = move(state, j);
                        int nextIndex = permutationToIndex(nextState);
                        if (distances[nextIndex] == -1) {
                            distances[nextIndex] = depth + 1;
                            nVisited++;
                        }
                    }
                }
            }

            if (nVisited == 0) break;

            depth++;
            //System.out.println(nVisited + " positions at depth " + depth);
        }
    }

    private static ArrayList<Integer> searchSolution(byte[] state) {
        var solution = new ArrayList<Integer>();

        byte[] aux = copyOf(state);
        while (true) {
            int currentIndex = permutationToIndex(aux);

            if (distances[currentIndex] == 0) break;

            for (int i = 0; i < 4; i++) {
                var nextState = move(aux, i);
                var nextIndex = permutationToIndex(nextState);
                if (distances[nextIndex] < distances[currentIndex]) {
                    solution.add(i);
                    aux = copyOf(nextState);
                    break;
                }
            }
        }

        return solution;
    }

    private static byte[] move(byte[] puzzle, int m) {
        byte[] res = copyOf(puzzle);
        byte tmp = res[m % 4];
        res[m % 4] = res[(m + 1) % 4];
        res[(m + 1) % 4] = tmp;
        return res;
    }

    private static void print(byte[] puzzle) {
        System.out.println(puzzle[0] + "\t" + puzzle[1]);
        System.out.println(puzzle[3] + "\t" + puzzle[2]);
    }

    private static int permutationToIndex(byte[] permutation) {
        int index = 0;
        for (int i = 0; i < permutation.length - 1; i++) {
            index *= permutation.length - i;
            for (int j = i + 1; j < permutation.length; j++) {
                if (permutation[i] > permutation[j]) {
                    index++;
                }
            }
        }

        return index;
    }

    private static byte[] indexToPermutation(int index, int length) {
        byte[] permutation = new byte[length];
        permutation[length - 1] = 0;
        for (int i = length - 2; i >= 0; i--) {
            permutation[i] = (byte) (index % (length - i));
            index /= length - i;
            for (int j = i + 1; j < length; j++) {
                if (permutation[j] >= permutation[i]) {
                    permutation[j]++;
                }
            }
        }

        return permutation;
    }

    private static byte[] copyOf(byte[] arr) {
        byte[] res = new byte[4];
        System.arraycopy(arr, 0, res, 0, arr.length);
        return res;
    }

    private static String formattedSolution(ArrayList<Integer> solution) {
        final StringBuilder builder = new StringBuilder();
        solution.forEach(e -> {
            builder.append(MOVES[e]);
            builder.append(" ");
        });
        return builder.toString().trim();
    }
}
