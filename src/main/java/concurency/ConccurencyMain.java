package concurency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ConccurencyMain {
    private static final int NUMBER_OF_THREADS = 5;
    private static final int PART_OF_LIST = 200_000;

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 1_000_000; i++) {
            list.add(i);
        }

        List<Callable<Long>> callableList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            callableList.add(new CallableClass(PART_OF_LIST * i,
                    PART_OF_LIST * (i +1), list));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        long startCallable = System.currentTimeMillis();
        List<Future<Long>> futures = executorService.invokeAll(callableList);
        long endCallable = System.currentTimeMillis();
        executorService.shutdownNow();

        long sumCallable = futures.stream()
                .mapToLong(f -> {
                    try {
                        return f.get();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();

        System.out.println("Callable " + sumCallable + " processed for "
                + (startCallable - endCallable));

        long startFork = System.currentTimeMillis();
        ForkClass fork = new ForkClass(list);
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        Long sumFork = commonPool.invoke(fork);
        long endFork = System.currentTimeMillis();

        System.out.println("Fork " + sumFork + " processed for " + (startFork - endFork));
    }
}
