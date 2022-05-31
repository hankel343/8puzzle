import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int[][] board;
    private Board twin;
    private int N; //Linear dimension of the board

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        N = tiles[0].length;

        /* Init boards */
        board = new int[N][N];
        twin = null;

        /* Create copy of ctor argument */
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = tiles[i][j];
            }
        }
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
                if (i == N - 1 && j == N - 1 && board[i][j] != 0) {
                    cnt++;
                }
                else if (board[i][j] != 0 && board[i][j] != i * N + j + 1) {
                    cnt++;
                }
            }
        }

        return cnt;
    }

    /* REFACTOR GOAL BOARD */
    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != 0) {

                    // Manhattan distance Y
                    if (board[i][j] % N == 0) {
                        sum += Math.abs((board[i][j] / N - 1) - i);
                    }
                    else {
                        sum += Math.abs((board[i][j] / N) - i);
                    }

                    //Manhattan distance X
                    if (board[i][j] % N == 0) {
                        sum += Math.abs(N - 1 - j);
                    }
                    else {
                        sum += Math.abs(board[i][j] - 1 - (board[i][j] / N) * N - j);
                    }
                }
            }
        }

        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1 && board[i][j] != 0) {
                    return false;
                }
                else if (board[i][j] != 0 && board[i][j] != i * N + j + 1) {
                    return false;
                }
            }
        }

        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (this.N != that.N) return false;
        /* Compare by tile */
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != that.tileAt(i, j))
                    return false;
            }
        }

        return true;
    }

    // Return element in the board at the specified indices
    private int tileAt(int i, int j) {
        if (i >= N || j >= N)
            throw new IllegalArgumentException();

        return board[i][j];
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        boolean isEmpty = false;
        Stack<Board> boardStack = new Stack<Board>();
        for (int i = 0; i < N && !isEmpty; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0) {
                    isEmpty = true;
                    Board neighbor;
                    if (i > 0) { // Row above
                        neighbor = new Board(board);
                        neighbor.swap(i, j, i - 1, j);
                        neighbor.twin();
                        boardStack.push(neighbor);
                    }

                    if (i < N - 1) { // Row below
                        neighbor = new Board(board);
                        neighbor.swap(i, j, i + 1, j);
                        neighbor.twin();
                        boardStack.push(neighbor);
                    }

                    if (j > 0) { // Left column
                        neighbor = new Board(board);
                        neighbor.swap(i, j, i, j - 1);
                        neighbor.twin();
                        boardStack.push(neighbor);
                    }

                    if (j < N - 1) { // Right column
                        neighbor = new Board(board);
                        neighbor.swap(i, j, i, j + 1);
                        neighbor.twin();
                        boardStack.push(neighbor);
                    }
                    break;
                }
            }
        }
        return boardStack;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (twin != null)
            return twin;

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
        twin = twinBoard;
        return twin;
    }

    // Helper function for neighbors()
    private void swap(int i, int j, int n, int m) {
        int temp = board[i][j];
        board[i][j] = board[n][m];
        board[n][m] = temp;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] nums1 = {
                { 0, 1, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 },
                };

        Board b1 = new Board(nums1);
        System.out.println(b1.hamming());
    }
}
