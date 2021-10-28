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
        CompletableFuture<Sums[]> s = CompletableFuture.supplyAsync(() -> {
            int[][] o = sumAll(matrix);
            int[] r1 = o[0];
            int[] r2 = o[1];
            int val = matrix.length;
            Sums[] sums = new Sums[val];
            for (int i = 0; i < val; i++) {
                sums[i] = new Sums(r1[i], r2[i]);
            }
            return sums;
        });
        return s.get();
    }

    private static int[][] sumAll(int[][] matrix) {
        int gorizontal = matrix.length;
        int vertical = matrix[0].length;
        int[] colL = new int[gorizontal];
        int[] rowL = new int[gorizontal];
        int[][] ret = new int[gorizontal][vertical];
        try {
            ret = CompletableFuture.supplyAsync(() -> {
                for (int c = 0; c < gorizontal; c++) {
                    int colSum = 0;
                    int rowSum = 0;
                    for (int r = 0; r < vertical; r++) {
                        colSum += matrix[c][r];
                        rowSum += matrix[c][r];
                        rowL[r] = rowSum;
                    }
                    colL[c] = colSum;
                }
                return new int[][]{rowL, colL};
            }).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}