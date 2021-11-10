package ru.job4j.pools;

import org.junit.Test;

import static org.junit.Assert.*;

public class RolColSum2Test {

    @Test
    public void syncSumCol0() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum2.Sums[] s = RolColSum2.sum(matrix);
        assertEquals(12, s[0].getColSum());
    }

    @Test
    public void syncSumCol1() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum2.Sums[] s = RolColSum2.sum(matrix);
        assertEquals(15, s[1].getColSum());
    }

    @Test
    public void syncSumCol2() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum2.Sums[] s = RolColSum2.sum(matrix);
        assertEquals(18, s[2].getColSum());
    }

    @Test
    public void syncSumRow0() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum2.Sums[] s = RolColSum2.sum(matrix);
        assertEquals(6, s[0].getRowSum());
    }

    @Test
    public void syncSumRow1() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum2.Sums[] s = RolColSum2.sum(matrix);
        assertEquals(15, s[1].getRowSum());
    }

    @Test
    public void syncSumRow2() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum2.Sums[] s = RolColSum2.sum(matrix);
        assertEquals(24, s[2].getRowSum());
    }

    @Test
    public void asyncSumCol0() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum2.Sums[] s = RolColSum2.asyncSum(matrix);
        assertEquals(24, s[2].getRowSum());
    }

    @Test
    public void asyncSumCol1() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum2.Sums[] s = RolColSum2.asyncSum(matrix);
        assertEquals(15, s[1].getColSum());
    }

    @Test
    public void asyncSumCol2() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum2.Sums[] s = RolColSum2.asyncSum(matrix);
        assertEquals(18, s[2].getColSum());
    }

    @Test
    public void asyncSumRow0() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum2.Sums[] s = RolColSum2.asyncSum(matrix);
        assertEquals(6, s[0].getRowSum());
    }

    @Test
    public void asyncSumRow1() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum2.Sums[] s = RolColSum2.asyncSum(matrix);
        assertEquals(15, s[1].getRowSum());
    }

    @Test
    public void asyncSumRow2() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum2.Sums[] s = RolColSum2.asyncSum(matrix);
        assertEquals(24, s[2].getRowSum());
    }
}