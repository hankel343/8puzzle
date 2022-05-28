public class board {
    private final int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        int N = tiles.length(); //Get dimension of given board
        board = new int[N][N];  //Init board to given dimension
        for (int i = 0; i < N; i++) //Copy elements
            for (int j = 0; j < N; j++)
                board[i][j] = tiles[i][j];
    }

    // string representation of this board
    public String toString() {

    }

    // board dimension n
    public int dimension() {

    }

    // number of tiles out of place
    public int hamming() {

    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

    }

    // is this board the goal board?
    public boolean isGoal() {

    }

    // does this board equal y?
    public boolean equals(Object y) {

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
