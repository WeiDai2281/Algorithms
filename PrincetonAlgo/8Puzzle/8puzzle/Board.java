import java.util.*;
import edu.princeton.cs.algs4.*;
public class Board {
    private int[][] blocks;
    public Board(int[][] blocks) {           // construct a board from an n-by-n array of blocks
        int n = blocks.length;
        this.blocks = new int[n][n];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }
    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {                 // board dimension n
        return blocks.length;
    }
    public int hamming() {                   // number of blocks out of place
        int count = 0;
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * n + j + 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public int manhattan() {                 // sum of Manhattan distances between blocks and goal
        int count = 0;
        int n = dimension();
        int tempx;
        int tempy;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0) {
                    tempx = (blocks[i][j] - 1) / n;
                    tempy = (blocks[i][j] - 1) % n;
                    count += Math.abs(tempx - i) + Math.abs(tempy - j);
                }
            }
        }
        return count;
    }

    public boolean isGoal() {                // is this board the goal board?
        return manhattan() == 0;
    }

    public Board twin() {                    // a board that is obtained by exchanging any pair of blocks
        Board newblocks = new Board(blocks);
        if (blocks[0][0] == 0) {
            exch(newblocks.blocks, 0, 1, 1, 1);
        } else if (blocks[0][1] == 0) {
            exch(newblocks.blocks, 0, 0, 1, 0);
        } else {
            exch(newblocks.blocks, 0, 0, 0, 1);
        }
        return newblocks;
    }

    private void exch(int[][] p, int ax, int ay, int bx, int by) {
        int temp = p[ax][ay];
        p[ax][ay] = p[bx][by];
        p[bx][by] = temp;
    }

    public boolean equals(Object y) {        // does this board equal y?
        if (y == null) return false;
        if (y == this) return true;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != that.blocks[i][j]) return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {     // all neighboring boards
        int zx = 0;
        int zy = 0;
        int n = dimension();
        List<Board> neighbor = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    zx = i;
                    zy = j;
                }
            }
        }
        if (zx != 0) {
            Board newboard = new Board(blocks);
            exch(newboard.blocks, zx, zy, zx - 1, zy);
            neighbor.add(newboard);
        }
        if (zy != 0) {
            Board newboard = new Board(blocks);
            exch(newboard.blocks, zx, zy, zx, zy - 1);
            neighbor.add(newboard);
        }
        if (zx != n - 1) {
            Board newboard = new Board(blocks);
            exch(newboard.blocks, zx, zy, zx + 1, zy);
            neighbor.add(newboard);
        }
        if (zy != n - 1) {
            Board newboard = new Board(blocks);
            exch(newboard.blocks, zx, zy, zx, zy + 1);
            neighbor.add(newboard);
        }
        return neighbor;

    }
    public String toString() {               // string representation of this board (in the output format specified below)
        int n = dimension();
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();

    }

    public static void main(String[] args) { // unit tests (not graded)
        // unit tests (not graded)
        // create initial board from file

	    In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);
	    System.out.println(initial.hamming());
    }
}