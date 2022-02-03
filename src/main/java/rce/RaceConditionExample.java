package rce;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Nov 4, 2015 Created by Mary Zheng, See https://examples.javacodegeeks.com/multithreading-in-java-tutorial/
 * which also refers to an additional video at https://www.youtube.com/watch?v=wEKgxTVq4Bg
 * <p>
 * Feb 3, 2022 Adapted by Ray Matthes and Chida Jeyabalan
 */
public class RaceConditionExample {

    public void raceConditionDemo(String type) {

        int poolSize = 10;
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        int threadCount = 10000;
        List<TaskThread> tasks = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            tasks.add(new TaskThread(type));
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Starting count value is " + getCount());

        try {
            List<Future<Integer>> results = executor.invokeAll(tasks, 20L, TimeUnit.SECONDS);
            assert results.size() > 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }

    public int getCount() {
        return DstMqService.getInstance().getCount();
    }

    public static void main(String[] args) {
        RaceConditionExample example = new RaceConditionExample();
        String type = (args.length > 0) ? args[0] : "RaceCondition";
        example.raceConditionDemo(type);
        System.out.println();
        System.out.println("Count = " + example.getCount());
    }

}
