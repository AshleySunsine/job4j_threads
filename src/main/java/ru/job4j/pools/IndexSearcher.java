package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexSearcher<T> {

    public int search(T[] list, T obj) {
        ForkJoinPool fjp = new ForkJoinPool();
        return (int) fjp.invoke(new Searcher(list, obj, 0, list.length - 1));
    }

    private class Searcher extends RecursiveTask {
      private final T[] list;
      private final T obj;
      private final int start;
      private final int finish;

        public Searcher(T[] list, T obj, int start, int finish) {
            this.list = list;
            this.obj = obj;
            this.start = start;
            this.finish = finish;
        }

        private int easySearch() {
            for (int i = start; i <= finish; i++) {
                if (list[i].equals(obj)) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        protected Integer compute() {
            if ((finish - start) <= 10) {
            return easySearch();
            }
                int mid = (start + finish) / 2;
                Searcher leftSearcher = new Searcher(list, obj, start, mid);
                Searcher rightSearcher = new Searcher(list, obj, mid + 1, finish);
                leftSearcher.fork();
                rightSearcher.fork();
                Integer a = (Integer) rightSearcher.join();
                Integer b = (Integer) leftSearcher.join();
                return a > b ? a : b;
            }
        }

    public static void main(String[] args) {
        int listSize = 15;
        int obj = 3;
        Integer[] list = new Integer[listSize];
        for (int i = 0; i < listSize; i++) {
            list[i] = i;
        }

        IndexSearcher<Integer> inSerch = new IndexSearcher<>();
        int index = inSerch.search(list, obj);
        System.out.println(index);
    }
}
