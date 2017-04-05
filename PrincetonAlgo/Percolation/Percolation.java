import java.lang.IllegalArgumentException;
import java.lang.IndexOutOfBoundsException;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int grid[];
    private int N;
    private WeightedQuickUnionUF uf;
    private int count;
    public Percolation(int n) {                // create n-by-n grid, with all sites blocked
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        N = n;
        uf = new WeightedQuickUnionUF(N * N + 2);
        grid = new int[n* n + 2];
        grid[0] = 1;
        grid[N * N + 1] = 0;
        for (int i = 1; i < n * n + 1; i ++) {
            grid[i] = -1;
        }
    }

    private void rangecheck(int row, int col) {
        if ((row - 1) * N + col > N * N || row < 1 || col < 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void open(int row, int col) {    // open site (row, col) if it is not open already
        rangecheck(row, col);
        if(isOpen(row, col)) return;
        count += 1;
        int index = index(row, col);
        grid[index] = 0;

        if (row != 1 && isOpen(row - 1, col)) {
            uf.union(index(row - 1, col), index);
        } else if (row == 1) {
            uf.union(0, index(row, col));
        }
        if (row != N && isOpen(row + 1, col)) {
            uf.union(index(row + 1, col), index);
        } else if (row == N){
            uf.union(index, N * N + 1);
        }
        if (col != 1 && isOpen(row, col - 1)) {
            uf.union(index(row, col - 1), index);
        }
        if (col != N && isOpen(row, col + 1)){
            uf.union(index(row, col + 1), index);
        }
    }

    private int index(int row, int col) {
            return (row - 1) * N + col;
        }



    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        rangecheck(row, col);
        return grid[index(row, col)] == 0;

    }
    public boolean isFull(int row, int col) {  // is site (row, col) full?
        rangecheck(row, col);
        return uf.connected(index(row, col), 0);

    }
    public int numberOfOpenSites() {       // number of open sites
        return count;
    }

    public boolean percolates() {           // does the system percolate?
        return uf.connected(0, N * N + 1);
    }

    public static void main(String[] args) {   // test client (optional)

    }
}