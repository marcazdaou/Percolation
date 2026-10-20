# Percolation

Estimating the **percolation threshold** of an n×n grid with a **Monte Carlo simulation**, comparing a
brute-force model with a **union-find** model. Written in Java for a data structures and algorithms course.

## The problem

Each site of an n×n grid is open or blocked. The system **percolates** if open sites connect the top row to
the bottom row (think water draining through porous rock). If sites are opened at random, what fraction must
be open before the system percolates? For large grids the answer is known to be close to **0.593**.

## Files (`Array Percolation/`)

| File | Approach |
|---|---|
| `ArrayPercolation.java` | Brute force: each `percolates()` / `isFull()` call flood-fills (recursive DFS) from every open site in the top row, O(n²) per call |
| `UFPercolation.java` | Weighted quick-union with virtual top and bottom sites, plus a second union-find without the bottom site to avoid "backwash" in `isFull()`; O(log n) per operation |
| `PercolationStats.java` | Monte Carlo experiment: runs `m` independent trials on `UFPercolation`, opening random sites until the grid percolates, and reports the mean, standard deviation and 95% confidence interval (mean ± 1.96·σ/√m) of the threshold |

## Run

Uses the course libraries (`stdlib`, `dsa`).

```bash
javac ArrayPercolation.java UFPercolation.java PercolationStats.java

java PercolationStats 200 100      # n = 200, m = 100 trials → mean, stddev, confidence interval
java UFPercolation input.txt       # open the sites listed in a file and report whether it percolates
```

## Concepts

Union-find · flood fill / depth-first search · Monte Carlo simulation · confidence intervals
