import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

public class Solver {
    private boolean canBeSolve = false;
    private SearchNode result;
    private class SearchNode implements Comparable<SearchNode> {
        Board current;
        SearchNode prev;
        int moves;
        SearchNode(Board board) {
            moves = 0;
            current = board;
            prev = null;
        }
        SearchNode(Board board, SearchNode prev, int move) {
            moves = move;
            current = board;
            this.prev = prev;
        }
        public int priority() {
            return this.current.manhattan() + this.moves;
        }

        public int compareTo(SearchNode a) {
            if (this.priority() > a.priority()) return 1;
            if (this.priority() < a.priority()) return -1;
            else return 0;
        }
    }
    private MinPQ<SearchNode> pq = new MinPQ<>();
    private MinPQ<SearchNode> twinpq = new MinPQ<>();
    public Solver(Board initial) {           // find a solution to the initial board (using the A* algorithm)
        pq.insert(new SearchNode(initial));
        twinpq.insert(new SearchNode(initial.twin()));
        SearchNode root = pq.delMin();
        SearchNode twroot = twinpq.delMin();
        while (!root.current.isGoal() && !twroot.current.isGoal()) {
            for (Board b: root.current.neighbors()) {
                if (root.prev == null || !b.equals(root.prev.current)) pq.insert(new SearchNode(b, root, root.moves + 1));
            }

            for (Board b: twroot.current.neighbors()) {
                if (twroot.prev == null || !b.equals(twroot.prev.current)) {
                    twinpq.insert(new SearchNode(b, twroot, twroot.moves + 1));
                }
            }
            root = pq.delMin();
            twroot = twinpq.delMin();
        }

        if (root.current.isGoal()) {
            canBeSolve = true;
            result = root;
        }
    }



    public boolean isSolvable() {            // is the initial board solvable?
        return canBeSolve;
    }

    public int moves() {                     // min number of moves to solve initial board; -1 if unsolvable
        if (isSolvable()) return result.moves;
        else return -1;
    }

    public Iterable<Board> solution() {      // sequence of boards in a shortest solution; null if unsolvable
        if (!isSolvable()) return null;
        Stack<Board> rs = new Stack<>();
        SearchNode temp = result;
        while (temp != null) {
            rs.push(temp.current);
            temp = temp.prev;
        }
        return rs;
    }
    public static void main(String[] args) { // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            System.out.println("No solution possible");
        else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                System.out.println(board);
        }
    }

}