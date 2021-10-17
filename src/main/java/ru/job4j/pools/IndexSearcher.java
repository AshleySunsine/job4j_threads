package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexSearcher {

    public static void main(String[] args) {
        int listSize = 15;
        int obj = 8;
        List<Integer> list = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            list.add(i);
        }

        IndexSearcher inSerch = new IndexSearcher();
        if (list.size() > 10) {
            int index = inSerch.search(list, obj);
            System.out.println(index);
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(obj)) {
                    System.out.println("** " + i);
                }
            }
        }
    }

    private int search(List<Integer> list, Integer obj) {
        ForkJoinPool fjp = new ForkJoinPool();
        return (int) fjp.invoke(new Searcher(list, obj, 0, list.size() - 1));
    }

    private class Searcher<T> extends RecursiveTask {
        List<T> list;
        T obj;
        int start;
        int finish;

        public Searcher(List<T> list, T obj, int start, int finish) {
            this.list = list;
            this.obj = obj;
            this.start = start;
            this.finish = finish;
        }

        @Override
        protected Integer compute() {
            int o = -1;
            if (start < finish) {
                int mid = (start + finish) / 2;
                System.out.println(Thread.currentThread().getName() + "->  " + "start=" + start + "; finish= " + finish + "; mid= " + mid + "; list= " + list.toString());
                Searcher leftSearcher = new Searcher(list, obj, start, mid);
                Searcher rightSearcher = new Searcher(list, obj, mid + 1, finish);
                leftSearcher.fork();
                rightSearcher.fork();
                Integer a = (Integer) rightSearcher.join();
                Integer b = (Integer) leftSearcher.join();
                if (!a.equals(-1)) {
                    o = a;
                }
                if (!b.equals(-1)) {
                    o = b;
                }
                o = -1;
            }
           if (start == finish) {
                if (obj.equals(list.get(start))) {
                    System.out.println("Дебаг. Найденый элемент, индекс " + start);
                    o = start;
                }
            }
            return o;
        }
    }
}
