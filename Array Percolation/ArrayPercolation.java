import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using a 2D array.
public class ArrayPercolation implements Percolation {
    private int n;  // private n
    private int openSites;  // private openSites
    private boolean[][] open;  // private boolean[][] open


    // Constructs an n x n percolation system, with all sites blocked.
    public ArrayPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");  // ("Illegal n") if n ≤ 0.
        }


        this.n = n;  // Initialize instance variables.
        open = new boolean[n][n];
        openSites = 0;

    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || j < 0 || i > n - 1 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        } // If site (i, j) is not open:
        if (!open[i][j]) {  // Open the site.

            open[i][j] = true;  // Increment openSites by one.
            openSites++;
        }
    }


            // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || j < 0 || i > n - 1 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");  // ("Illegal n") if n ≤ 0.
        }
        // Boundary check.
        return open[i][j];
    }


    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if (i < 0 || j < 0 || i > n - 1 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");  //("Illegal n") if n ≤ 0.
        }
        // Boundary check.
        boolean[][] full = new boolean[n][n];  // Create an n × n array of booleans called full
        for (int k = 0; k < n; k++) {  // Call floodFill() on every site in the first row
            floodFill(full, 0, k);

        }
        return full[i][j];  // Return full[i][j].
    }





    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        boolean[][] full = new boolean[n][n];  // Create an n × n array of booleans called full
        for (int k = 0; k < n; k++) {
            floodFill(full, 0, k);  // Call floodFill() on every site in the first row
        }
        for (int k = 0; k < n; k++) {  // last row contains at least one full site
            if (full[n - 1][k]) {
                return true;
            }
        }
        return false;
    }








    // Recursively flood fills full[][] using depth-first exploration, starting at (i, j).
    private void floodFill(boolean[][] full, int i, int j) {
        // Return if i or j is out of bounds; or site (i, j) is not open; or site (i, j) is full .
        if (i < 0 || j < 0 || i > n - 1 || j > n - 1 || !isOpen(i, j) || full[i][j]) {
            return;
        }
                // Fill site (i, j).
        full[i][j] = true;
        // floodFill() recursively on the sites to the north, east, west, and south of site (i, j).
        floodFill(full, i - 1, j);
        floodFill(full, i + 1, j);
        floodFill(full, i, j - 1);
        floodFill(full, i, j + 1);
    }




    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        ArrayPercolation perc = new ArrayPercolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}