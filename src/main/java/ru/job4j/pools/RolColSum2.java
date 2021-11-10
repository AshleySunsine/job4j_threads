package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

public class RolColSum2 {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
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
        Sums[] sums = new Sums[matrix.length];
        for (int k = 0; k < matrix.length; k++) {
            ConcurrentHashMap<String, Integer> map = privateSum(matrix, k);
            sums[k] = new Sums(map.get("Row"),  map.get("Col"));
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int k = 0; k < matrix.length; k++) {
            CompletableFuture<ConcurrentHashMap<String, Integer>> com = doing(matrix, k);
            try {
                sums[k] = new Sums(com.get().get("Row"), com.get().get("Col"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return sums;
    }

    private static CompletableFuture<ConcurrentHashMap<String, Integer>> doing(int[][] matrix, int rowcol) {
        return CompletableFuture.supplyAsync(() -> {
           return privateSum(matrix, rowcol);
        });
    }

    private static ConcurrentHashMap<String, Integer> privateSum(int[][] matrix, int rowcol) {
        ConcurrentHashMap<String, Integer> rowColSum = new ConcurrentHashMap<>();
        int row = 0;
        int col = 0;
        for (int k = 0; k < matrix.length; k++) {
            row = row + matrix[rowcol][k];
            col = col + matrix[k][rowcol];
        }
        rowColSum.put("Row", row);
        rowColSum.put("Col", col);
        return rowColSum;
    }

}