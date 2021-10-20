package ru.job4j.pools;

import org.junit.Test;

import static org.junit.Assert.*;

public class IndexSearcherTest {
    @Test
    public void intCollectionTest() {
        Integer[] list = new Integer[50];
        for (int i = 0; i < 50; i++) {
            list[i] = i;
        }
        IndexSearcher<Integer> inSerch = new IndexSearcher<>();
        int indexExec = inSerch.search(list, 4);
        assertEquals(4, indexExec);
    }

    @Test
    public void stringCollectionTest() {
        String[] list = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q"};
        IndexSearcher<String> inSerch = new IndexSearcher<>();
        int indexExec = inSerch.search(list, "p");
        assertEquals(15, indexExec);
    }

    @Test
    public void notFoundObjectReturnNegativeOneDigital() {
        String[] list = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q"};
        IndexSearcher<String> inSerch = new IndexSearcher<>();
        int indexExec = inSerch.search(list, "x");
        assertEquals(-1, indexExec);
    }

}