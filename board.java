import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int[][] board;
    private final int[][] goal;
    private int N; //Linear dimension of the board

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        N = tiles[0].length;

        /* Init boards */
        board = new int[N][N];
        goal = new int[N][N];

        /* Create copy of ctor argument */
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = tiles[i][j];
            }
        }

        /* Create goal board */
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                goal[i][j] = i * N + j + 1; // Row-major order
            }
        }

        goal[N - 1][N - 1] = 0;
    }

    // string representation of this board
    public String toString() {
        String s = Integer.toString(N) + "\n";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1) {
                    s += Integer.toString(board[i][j]) + "\n";
                }
                else if (j == N - 1) {
                    s += Integer.toString(board[i][j]) + "\n";
                }
                else {
                    s += Integer.toString(board[i][j]) + " ";
                }
            }
        }

        return s;
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != goal[i][j] && board[i][j] != 0)
                    cnt++;
            }
        }

        return cnt;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != 0) {
                    sum += Math.abs(manDistX(board[i][j]) - j) + Math
                            .abs(manDistY(board[i][j]) - i);
                }
            }
        }

        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != goal[i][j])
                    return false;
            }
        }

        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || this.dimension() != ((Board) y).dimension()) {
            return false;
        }

        /* Compare by tile */
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != ((Board) y).tileAt(i, j))
                    return false;
            }
        }

        return true;
    }

    // Return element in the board at the specified indices
    public int tileAt(int i, int j) {
        if (i >= N || j >= N)
            throw new IllegalArgumentException();

        return board[i][j];
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> boardStack = new Stack<Board>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0) {
                    Board neighbor;
                    if (i > 0) { // Row above
                        neighbor = new Board(board);
                        neighbor.swap(i, j, i - 1, j);
                        boardStack.push(neighbor);
                    }

                    if (i < N - 1) { // Row below
                        neighbor = new Board(board);
                        neighbor.swap(i, j, i + 1, j);
                        boardStack.push(neighbor);
                    }

                    if (j > 0) { // Left column
                        neighbor = new Board(board);
                        neighbor.swap(i, j, i, j - 1);
                        boardStack.push(neighbor);
                    }

                    if (j < N - 1) { // Right column
                        neighbor = new Board(board);
                        neighbor.swap(i, j, i, j + 1);
                        boardStack.push(neighbor);
                    }
                }
            }
        }
        return boardStack;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twinBoard = new Board(board);
        int i = StdRandom.uniform(N), j = StdRandom.uniform(N);
        int n = StdRandom.uniform(N), m = StdRandom.uniform(N);
        while (twinBoard.tileAt(i, j) == 0 || twinBoard.tileAt(n, m) == 0) {
            i = StdRandom.uniform(N);
            j = StdRandom.uniform(N);
            n = StdRandom.uniform(N);
            m = StdRandom.uniform(N);
        }

        twinBoard.swap(i, j, n, m);
        return twinBoard;
    }

    // Helper function for neighbors()
    private void swap(int i, int j, int n, int m) {
        int temp = board[i][j];
        board[i][j] = board[n][m];
        board[n][m] = temp;
    }

    //Helper function of manhattan()
    private int manDistY(int val) {
        if (val == 0)
            return N - 1;

        int i = N - 1;
        while (goal[i][0] > val) {
            i--;
        }
        return i;
    }

    //Helper function of manhattan()
    private int manDistX(int val) {
        int j = 0, i = 0;
        if (val > goal[N - 1][0]) {
            i = 1;
            while (val > goal[N - 1][i])
                i++;
        }
        else {
            while (val > goal[j][i]) {
                if (val > goal[j][N - 1]) {
                    j += 1;
                }
                else {
                    while (val > goal[j][i])
                        i++;
                }
            }
        }
        return i;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };

        int[][] tiles2 = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };
    }
}
