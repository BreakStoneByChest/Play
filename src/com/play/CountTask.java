package com.play;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join 大任务拆成小任务
 * Created by louisliuyi on 2018/6/5.
 */
public class CountTask extends RecursiveTask<Long> {

    private int start;
    private int end;
    private static final int threshold = 200;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {

        long sum = 0;
        boolean canCompute = (end - start) <= threshold;
        if(canCompute){
            for(int i = start; i <= end; i++){
                sum +=i;
            }
        }else{
            // split to two task
            int middle = (start + end)/2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle+1, end);
            System.out.println("任务分解，one: " + start + "~" + middle + ", two: " + (middle+1) + "~" + end);

            // fork
            leftTask.fork();
            rightTask.fork();

            // join
            long leftResult = leftTask.join();
            long rightResult = rightTask.join();

            // merge
            sum = leftResult + rightResult;

        }

        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(1, 10000);
        Future<Long> result = forkJoinPool.submit(task);
        try {
            System.out.println(result.get());
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
