import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;
import java.util.LinkedList;

public class Solver {
    private final MinPQ<SearchNode> pq;
    private final LinkedList<Board> solutionSeq;

    private class SearchNode {
        Board board;
        int moves;
        int priority;
        Board prev;
    }

    private class PriorityOrder implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            if (a.priority < b.priority) return -1;
            else if (a.priority > b.priority) return 1;
            else return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        /* Create MinPQ w/ min priority comparator */
        pq = new MinPQ<SearchNode>(priorityOrder());

        /* LinkedList for storing sequence of boards to solution */
        solutionSeq = new LinkedList<Board>();

        /* Initialize first search node object */
        SearchNode initNode = new SearchNode();
        initNode.board = initial;
        initNode.moves = 0;
        initNode.priority = initial.manhattan();
        initNode.prev = null;

        /* A* search algorithm */
        SearchNode currNode = initNode;
        pq.insert(initNode);
        while (!currNode.board.isGoal()) {
            currNode = pq.delMin();
            solutionSeq.add(currNode.board);
            Stack<Board> neighborStack = (Stack<Board>) currNode.board.neighbors();
            while (!neighborStack.isEmpty()) {
                Board neighbor = neighborStack.pop();
                if (!currNode.prev.equals(neighbor)) { // Optimization: Disallow previous boards
                    SearchNode newNode = new SearchNode();
                    newNode.board = neighbor;
                    newNode.moves++;
                    newNode.priority = neighbor.manhattan();
                    pq.insert(newNode);
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {

    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable())
            return
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solutionSeq;
    }

    public Comparator<SearchNode> priorityOrder() {
        return new PriorityOrder();
    }

    public static void main(String[] args) {

    }
}
