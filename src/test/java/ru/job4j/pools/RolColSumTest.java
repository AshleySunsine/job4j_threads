package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void syncSumCol() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] s = RolColSum.sum(matrix);
        assertEquals(s[2].getColSum(), 24);
    }

    @Test
    public void syncSumRow() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] s = RolColSum.sum(matrix);
        assertEquals(s[1].getRowSum(), 15);
    }


    @Test
    public void asyncSumCol() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] s = RolColSum.asyncSum(matrix);
        assertEquals(s[2].getColSum(), 24);
    }

    @Test
    public void asyncSumRow() throws InterruptedException, ExecutionException {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] s = RolColSum.asyncSum(matrix);
        assertEquals(s[1].getRowSum(), 15);
    }
}