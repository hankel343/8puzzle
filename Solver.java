import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.LinkedList;

public class Solver {
    private class SearchNode {
        Board board;
        SearchNode parent;
        int moves;
        int priority;

        SearchNode(Board board, SearchNode parent, int moveCnt) {
            this.board = board;
            this.parent = parent;
            moves = moveCnt;
            priority = board.manhattan();
        }
    }

    private class PriorityOrder implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            return Integer.compare(a.priority + a.moves, b.priority + b.moves);
            /*
            if (a.priority + a.moves < b.priority + b.moves) return -1;
            else if (a.priority + a.moves > b.priority + b.moves) return 1;
            else return 0;
             */
        }
    }

    private final LinkedList<Board> solutionSeq;
    private boolean solvable = false;
    private SearchNode solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        /* Create MinPQs w/ min priority comparator */
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>(priorityOrder());
        MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>(priorityOrder());

        /* Insert initial board and twin into PQs */
        pq.insert(new SearchNode(initial, null, 0));
        pqTwin.insert(new SearchNode(initial.twin(), null, 0));

        /* LinkedList for storing sequence of boards to solution */
        solutionSeq = new LinkedList<Board>();

        while (true) {
            SearchNode tmp = pq.delMin();
            if (tmp.board.isGoal()) {
                solvable = true;
                solutionSeq.add(tmp.board);
                solution = tmp;
                return;
            }
            solutionSeq.add(tmp.board);

            SearchNode tmptwin = pqTwin.delMin();
            if (tmptwin.board.isGoal()) return;

            for (Board neighbor : tmp.board.neighbors()) {
                if (tmp.parent != null && neighbor.equals(tmp.parent.board))
                    continue;
                pq.insert(new SearchNode(neighbor, tmp, tmp.moves + 1));
            }

            for (Board neighbor : tmptwin.board.neighbors()) {
                if (tmp.parent != null && neighbor.equals(tmptwin.parent.board))
                    continue;
                pqTwin.insert(new SearchNode(neighbor, tmptwin, tmptwin.moves + 1));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (solvable)
            return solution.moves;

        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solvable)
            return solutionSeq;

        return null;
    }

    private Comparator<SearchNode> priorityOrder() {
        return new PriorityOrder();
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
