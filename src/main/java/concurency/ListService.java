package concurency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import lombok.SneakyThrows;

public class ListService {
    private static final int NUMBER_OF_THREADS = 5;
    private static final int PART_OF_LIST = 200_000;
    private List<Integer> list = new ArrayList<>();

    public ListService(int capacity) {
        for (int i = 0; i < capacity; i++) {
            list.add(i);
        }
    }

    public long calculateCallablesResult() {
        return calculateWithCallabla().stream()
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
    }

    public long calculateForkResult() {
        ForkClass fork = new ForkClass(list);
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        Long sumFork = commonPool.invoke(fork);
        return sumFork;
    }

    @SneakyThrows
    private List<Future<Long>> calculateWithCallabla() {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        List<Future<Long>> futures = executorService.invokeAll(getCallables());
        executorService.shutdownNow();
        return futures;
    }

    private List<Callable<Long>> getCallables() {
        List<Callable<Long>> callableList = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            callableList.add(new CallableClass(PART_OF_LIST * i,
                    PART_OF_LIST * (i + 1), list));
        }
        return callableList;
    }
}
