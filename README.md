# Percolation

A **Union-Find** based percolation model in Java with Monte Carlo simulation to estimate the **percolation threshold** of an N×N grid.

## Problem

An N×N grid of sites is either *blocked* or *open*. The system **percolates** when there is a path of open sites from the top row to the bottom row — modelling physical phenomena like water flowing through porous rock or electrical conductivity through composite materials.

**Question:** What fraction of sites must be open for the system to percolate?

## Implementations

| Class | Description |
|---|---|
| `ArrayPercolation.java` | Brute-force O(N²) connectivity check via full-grid scan |
| `UFPercolation.java` | Efficient O(log N) connectivity using Weighted Union-Find with path compression |
| `PercolationStats.java` | Monte Carlo simulation — runs T independent trials to estimate threshold |

## Monte Carlo Simulation

```
For each of T independent trials:
  1. Start with all N×N sites blocked
  2. Randomly open sites one at a time (uniform random order)
  3. Record the fraction of open sites when the system first percolates
Estimate threshold = mean(fractions across T trials)
95% confidence interval = mean ± 1.96 · stddev / √T
```

## Results

For large N, the percolation threshold converges to **≈ 0.593** (proven by statistical physics).

```
N = 200, T = 100 trials
Percolation threshold: 0.5929
95% confidence interval: [0.5912, 0.5946]
Elapsed time: 0.42 seconds
```

## Complexity

| Implementation | Open Site | Percolates? |
|---|---|---|
| `ArrayPercolation` | O(1) | O(N²) |
| `UFPercolation` | O(log* N) ≈ O(1) | O(log* N) ≈ O(1) |

*log\* = iterated logarithm (nearly constant with path compression)*

## Technologies

- **Java**
- Weighted Union-Find with path compression and union by rank
- Monte Carlo simulation

## Build & Run

```bash
javac ArrayPercolation.java UFPercolation.java PercolationStats.java

# Run Monte Carlo simulation: grid size N, T trials
java PercolationStats 200 100
```