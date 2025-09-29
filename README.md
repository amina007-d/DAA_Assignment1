# Assignment 1 – Divide and Conquer Algorithms

## Introduction
This project implements and analyzes four classic divide-and-conquer algorithms:

- **MergeSort**
- **QuickSort** (randomized pivot)
- **Deterministic Select** (Median-of-Medians)
- **Closest Pair of Points (2D)**

The goals were:
- Ensure **safe recursion** and bounded recursion depth
- Collect detailed **metrics** (time, comparisons, moves, recursion depth)
- Compare **theory vs experiment** using recurrence relations and complexity analysis
- Benchmark **Deterministic Select vs Arrays.sort()**

---

## 1. Architecture Notes
- **Recursion Depth Control**
    - MergeSort: halving ensures $O(\log n)$ depth.
    - QuickSort: recurse on smaller partition, iterate on larger → expected depth $\approx 2\log n$.
    - Select: only recurse into one side (usually smaller).
    - Closest Pair: recursive halving in 2D, depth $O(\log n)$.

- **Memory Management**
    - MergeSort uses a **single reusable buffer**.
    - QuickSort uses **in-place partitioning**.
    - Select reuses array space with partitioning.
    - Closest Pair sorts by x and y, reuses auxiliary arrays.

---

## 2. Recurrence Analysis

| Algorithm            | Recurrence                                        | Complexity              | Recursion Depth        |
|----------------------|---------------------------------------------------|-------------------------|------------------------|
| **MergeSort**        | $T(n) = 2T(\tfrac{n}{2}) + \Theta(n)$             | $\Theta(n \log n)$      | $O(\log n)$            |
| **QuickSort** (avg.) | $T(k)+T(n-k-1)+\Theta(n),\ \mathbb{E}[k]\approx n/2$ | $\Theta(n \log n)$ (avg.), worst $\Theta(n^2)$ | $\approx 2 \log n$ (avg.), worst $n$ |
| **Deterministic Select** | $T(\tfrac{n}{5})+T(\tfrac{7n}{10})+\Theta(n)$ | $\Theta(n)$             | $O(\log n)$ (very small in practice) |
| **Closest Pair**     | $2T(\tfrac{n}{2}) + \Theta(n)$                    | $\Theta(n \log n)$      | $O(\log n)$            |

**Notes:**
- MergeSort and Closest Pair follow *Master Theorem, Case 2*.
- QuickSort: average $\Theta(n \log n)$ but worst-case quadratic.
- Select: proven linear via *Akra–Bazzi*.
- Strip check in Closest Pair adds only constant factor.

---

## 3. Experimental Results (CSV Summary)

### 3.1 MergeSort
| n     | Avg Time (ns) | Avg Depth | Avg Comparisons | Avg Moves |
|-------|---------------|-----------|-----------------|-----------|
| 100   | ~100k         | 4         | ~560            | ~1010     |
| 1000  | ~160k         | 7         | ~9100           | ~16600    |
| 5000  | ~0.8M         | 9         | ~50k            | ~92k      |

### 3.2 QuickSort
| n     | Avg Time (ns) | Avg Depth | Avg Comparisons | Avg Moves |
|-------|---------------|-----------|-----------------|-----------|
| 100   | ~180k         | 4         | ~600            | ~930      |
| 1000  | ~300k         | 6         | ~11k            | ~18k      |
| 5000  | ~1.5M         | 9–10      | ~65k            | ~110k     |

### 3.3 Deterministic Select
| n     | Avg Time (ns) | Depth | Avg Comparisons | Avg Moves |
|-------|---------------|-------|-----------------|-----------|
| 500   | ~0.12M        | 1     | ~1050           | ~2400     |
| 1000  | ~0.17M        | 1     | ~2100           | ~4900     |
| 5000  | ~0.35M        | 1–2   | ~10k            | ~23k      |

### 3.4 Closest Pair (2D)
| n     | Avg Time (ns) | Avg Depth | Remarks |
|-------|---------------|-----------|---------|
| 1000  | ~0.4M         | 8         | Matches $n \log n$ trend |
| 5000  | ~2.5M         | 12        | Strip step adds constant overhead |

---

## 4. Plots

### 4.1 Time vs n
![Graph 1](plots\graph_1.png)

### 4.2 Recursion Depth vs n
![Graph 2](plots\graph_2.png)

### 4.3 Comparisons vs n
![Graph 3](plots\graph_3.png)

### 4.4 Select Benchmark (Select vs Arrays.sort)
![Graph 4](plots\graph_4.png)

---

## 5. Discussion
- **MergeSort**: perfectly aligns with $\Theta(n \log n)$,  stable recursion depth.
- **QuickSort**: faster than MergeSort on some inputs but shows higher variance. Depth ≈ $2\log n$.
- **Select**: linear scaling, recursion depth practically constant. JMH confirms **2× faster than Arrays.sort** for selection tasks.
- **Closest Pair**: matches $\Theta(n \log n)$, constant factor overhead visible due to geometry checks.

---

## 6. Conclusion
- All algorithms confirm theoretical predictions.
- **Recursion safety** achieved by design (depth bounded).
- **Metrics** (time, comparisons, moves) align with asymptotic expectations.
- **Benchmarks** show that **Deterministic Select** outperforms sorting-based selection.
- Project goals (implementation, analysis, validation) successfully met.

---
