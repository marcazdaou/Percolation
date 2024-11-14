import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.StdStats;

public class PercolationStats {
    private int m;  // int m
    private int n;  // int n
    private double[]x;  // double[]x


    // Performs m independent experiments on an n x n percolation system.
    public PercolationStats(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Illegal n or m");  // ("Illegal n or m").
        }
        this.m = m;
        this.n = n;
        this.x = new double[m];
        for (int i = 0; i < m; i++) {  //  Perform the following experiment m times:
            UFPercolation percolation = new UFPercolation(n);  // UFPercolation implementation
            int openSites = 0;
            while (!percolation.percolates()) {  // Until the system percolates
                int row = StdRandom.uniform(0, n);  //  choose a site (i, j) at random and open
                int col = StdRandom.uniform(0, n);
                if (!percolation.isOpen(row, col)) {  // if it is not already open, open it
                    percolation.open(row, col);
                    openSites++;
                }
            }
            x[i] = (double) openSites / (n * n);  // store it in x
        }
    }
    // Returns sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(x);  // Return the mean µ of the values in x[].
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(x);  // Return the standard deviation σ of the values in x[]
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(m));  // formula
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(m));  // formula
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, m);
        StdOut.printf("Percolation threshold for a %d x %d system:\n", n, n);
        StdOut.printf("  Mean                = %.3f\n", stats.mean());
        StdOut.printf("  Standard deviation  = %.3f\n", stats.stddev());
        StdOut.printf("  Confidence interval = [%.3f, %.3f]\n", stats.confidenceLow(),
                stats.confidenceHigh());
    }
}