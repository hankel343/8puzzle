public class Board {
    private final int[][] board;
    private final int[][] goal;
    private int N; //Linear dimension of the board

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        N = tiles[0].length;

        /* Init boards */
        board = tiles;
        goal = new int[N][N];

        /* Create boards */
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                goal[i][j] = i * N + j + 1; // Row-major order
                if (i == N - 1 && j == N - 1) // Save the last spot for 0 (i.e. empty space)
                    break;
            }

        goal[N - 1][N - 1] = 0;
    }

    // string representation of this board
    public String toString() {
        String s = Integer.toString(N) + "\n";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1) {
                    s += Integer.toString(board[i][j]);
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
                if (board[i][j] != goal[i][j])
                    cnt++;
            }
        }

        return cnt;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

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
        if (this.dimension() != ((Board) y).dimension()) {
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
        if (i >= N && j >= N)
            throw new IllegalArgumentException();

        return board[i][j];
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}
