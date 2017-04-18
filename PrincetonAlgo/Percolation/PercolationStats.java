import java.lang.IllegalArgumentException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] result;
    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
        result = new double[trials];
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        for (int i = 0; i < trials; i++) {
            Percolation test = new Percolation(n);
            int count = 0;
            while (!test.percolates()) {
                int index = StdRandom.uniform(n * n) + 1;
                int row;
                int col;
                col = index % n;
                if (col == 0) {
                    col = n;
                }
                row = (index - col) / n + 1;
//                    System.out.println(row);
//                    System.out.println(col);

//                int row=StdRandom.uniform(n)+1;
//                int col=StdRandom.uniform(n)+1;

                if (!test.isOpen(row, col)) {
                    test.open(row, col);
                    count += 1;
                    continue;
                }
            }
            result[i] = (1.0 * count) / (n * n);
            //System.out.println(count);
        }
    }

    public double mean() {                          // sample mean of percolation threshold
        return StdStats.mean(result);
    }

    public double stddev() {                        // sample standard deviation of percolation threshold
        return StdStats.stddev(result);
    }

    public double confidenceLo() {                  // low  endpoint of 95% confidence interval
        return mean()-((1.96*stddev())/Math.sqrt(result.length));
    }

    public double confidenceHi() {                  // high endpoint of 95% confidence interval
        return mean() + ((1.96 * stddev()) / Math.sqrt(result.length));
    }

    public static void main(String[] args) {        // test client (described below)
        PercolationStats ps=new PercolationStats(200,100);
        System.out.println("mean = "+ps.mean()+"\n");
        System.out.print("std dev = "+ps.stddev()+"\n");
        System.out.print("95% confidence interval = "+ps.confidenceLo()+", "+ps.confidenceHi());

    }
}

