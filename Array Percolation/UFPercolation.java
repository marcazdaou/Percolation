import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using the UF data structure.
public class UFPercolation implements Percolation {
    private int n;  // int n
    private boolean[][] open;  // boolean[][]open
    private int openSites;  // int openSites
    // Virtual source instance variable
    private int source;  // int source

    // Virtual sink instance variable
    private int sink;  // int sink
    private WeightedQuickUnionUF uf;  // uf
    private WeightedQuickUnionUF backWash; // backWash


    // Constructs an n x n percolation system, with all sites blocked.
    public UFPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        this.n = n;
        source = 0;
        sink = n * n + 1;
        open = new boolean[n][n];
        openSites = 0;
        uf = new WeightedQuickUnionUF(n * n + 2);
        backWash = new WeightedQuickUnionUF(n * n + 1);

        // Connect the virtual source and sink to the first and last rows, respectively

    }




    // end UFPercolation constructor


    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || j < 0 || i >= n || j >= n) {
            throw new IndexOutOfBoundsException("Illegal i or j");  // illegal i or j
        }
        if (!open[i][j]) {  // open site
            open[i][j] = true;  // mark site open
            openSites++;  // increment by 1

            int p = encode(i, j);  // Encode the site as a unique integer
            uf.union(encode(0, i), source);
            backWash.union(encode(0, i), source);
            uf.union(encode(n - 1, i), sink);

            // Connect with neighboring sites if they are open
            if (i == 0) {  // If the site is in the top row, connect it to the virtual source node
                uf.union(source, p);
                backWash.union(source, p);
            }
            if (i == n - 1) {  // If bottom row, connect it to the virtual sink node
                uf.union(sink, p);
            }
            if (i > 0 && open[i - 1][j]) {  // If the site above is open, connect them
                uf.union(encode(i - 1, j), p);
                backWash.union(encode(i - 1, j), p);
            }
            if (i < n - 1 && open[i + 1][j]) {  // If the site below is open, connect it.
                uf.union(encode(i + 1, j), p);
                backWash.union(encode(i + 1, j), p);
            }
            if (j > 0 && open[i][j - 1]) {  // If the site to the left is open, connect it.
                uf.union(encode(i, j - 1), p);
                backWash.union(encode(i, j - 1), p);
            }
            if (j < n - 1 && open[i][j + 1]) {  // If the site to the right is open, connect it.
                uf.union(encode(i, j + 1), p);
                backWash.union(encode(i, j + 1), p);
            }
        }
    }


    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || j < 0 || i > n - 1 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }

        return open[i][j];  // return open [i][j]
    }






    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if (i < 0 || j < 0 || i > n - 1 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }  // site is full if it is open and its corresponding uf site is connected to
        // the source.


        int p = encode(i, j);
        return open[i][j] && backWash.connected(source, p);

    }


    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }  // return open site


    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        if (n <= 1) {
            return false;
        }
        return uf.connected(sink, source);  // – Return whether the system percolates or not
    }


    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }

        return i * n + j + 1;
    }


    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        UFPercolation perc = new UFPercolation(n);
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