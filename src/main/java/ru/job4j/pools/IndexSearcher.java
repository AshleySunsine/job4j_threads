/*package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexSearcher {

    public static void main(String[] args) {
        int listSize = 12;
        String obj = "67";
        List<String> list = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            list.add(String.valueOf(i));
        }

        IndexSearcher inSerch = new IndexSearcher();
        if(list.size() > 10) {
            int index = inSerch.search(list, obj);
            System.out.println(index);
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(obj)) {
                    System.out.println(list.get(i));
                }
            }
        }
    }

    private int search(List<String> list, String obj) {
        ForkJoinPool fjp = new ForkJoinPool();
        return (int)fjp.invoke(new Searcher<String>(list, obj, 0, list.size() - 1, 0));
    }

    private class Searcher<T> extends RecursiveTask {
        List<T> list;
        T obj;
        int start;
        int finish;
        int zzz;

        public Searcher(List<T> list, T obj, int start, int finish, int zzz) {
            this.list = list.subList(start, finish);
            this.obj = obj;
            this.start = start;
            this.finish = finish;
            this.zzz = zzz;
        }

        @Override
        protected Integer compute() {
            if (start == finish) {
                if (list.get(start).equals(obj)) {
                return start;
            } else {
                    return -1;
                }
            } else {
                int zzz;
                int mid = (start + finish) / 2;
                System.out.println(Thread.currentThread().getName() + " ->  " + "s=" + start + "; f= " + finish + "; m= " + mid);

               Searcher<T> leftSearcher = new Searcher<>(list, obj, start, mid);
                Searcher<T> rightSearcher = new Searcher<>(list, obj, mid + 1, finish);
                leftSearcher.fork();
                rightSearcher.fork();

                int right = (int)rightSearcher.join();
                int left = (int) leftSearcher.join();
                if (right != (-1)) {
                    return right;
                }
                return left;
            }
        }
    }
}
*/