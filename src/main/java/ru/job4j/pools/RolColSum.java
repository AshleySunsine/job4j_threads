package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int colSum, int rowSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }
        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int gorizontal = matrix.length;
        int vertical = matrix[0].length;
        int[] col = new int[gorizontal];
        int[] row = new int[vertical];
        for (int c = 0; c < gorizontal; c++) {
            int colSum = 0;
            for (int r = 0; r < vertical; r++) {
                colSum += matrix[c][r];
            }
            col[c] = colSum;
        }
        for (int r = 0; r < gorizontal; r++) {
            int rowSum = 0;
            for (int c = 0; c < vertical; c++) {
                rowSum += matrix[c][r];
            }
            row[r] = rowSum;
        }
        Sums[] sums = new Sums[col.length];
        for (int i = 0; i < sums.length; i++) {
            sums[i] = new Sums(col[i], row[i]);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        CompletableFuture<Sums[]> s = sumCol(matrix).thenCombine(sumRow(matrix),
                (r1, r2) -> {
            int val = matrix.length;
            Sums[] sums = new Sums[val];
            for (int i = 0; i < val; i++) {
                sums[i] = new Sums(r1[i], r2[i]);
            }
            return sums;
        });
        return s.get();
    }

    private static CompletableFuture<int[]> sumCol(int[][] matrix) {
        int gorizontal = matrix.length;
        int vertical = matrix[0].length;
        return CompletableFuture.supplyAsync(() -> {
            int[] colL = new int[gorizontal];
            for (int c = 0; c < gorizontal; c++) {
                int colSum = 0;
                for (int r = 0; r < vertical; r++) {
                    colSum += matrix[c][r];
                }
                colL[c] = colSum;
            }
            return colL;
        });

    }

    private static CompletableFuture<int[]> sumRow(int[][] matrix) {
        int gorizontal = matrix.length;
        int vertical = matrix[0].length;
        return  CompletableFuture.supplyAsync(() -> {
            int[] rowL = new int[vertical];
            for (int r = 0; r < gorizontal; r++) {
                int rowSum = 0;
                for (int c = 0; c < vertical; c++) {
                    rowSum += matrix[c][r];
                }
                rowL[r] = rowSum;
            }
            return rowL;
        });
    }
}