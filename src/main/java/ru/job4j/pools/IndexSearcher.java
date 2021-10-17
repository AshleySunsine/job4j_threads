package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexSearcher {

    public static void main(String[] args) {
        int listSize = 60;
        int obj = 2;
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

    private class Searcher extends RecursiveTask {
        List<Integer> list;
        int obj;
        int start;
        int finish;

        public Searcher(List<Integer> list, int obj, int start, int finish) {
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
                int a = (int) rightSearcher.join();
                int b = (int) leftSearcher.join();
                if (a != (-1)) {
                    o = a;
                }
                if (b != (-1)) {
                    o = b;
                }
                o = -1;
            }
           if (start == finish) {
                if (obj == list.get(start)) {
                    System.out.println("дебаг " + start);
                    o = start;
                }
            }
            return o;
        }
    }
}
