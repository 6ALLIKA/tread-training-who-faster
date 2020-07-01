package concurency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    private static final int NUMBER_OF_THREADS = 5;

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 1_000_000; i++) {
            list.add(i);
        }

        Callable<Integer> callList1 = new CallableClass(0, 200_000, list);
        Callable<Integer> callList2 = new CallableClass(200_000, 400_000, list);
        Callable<Integer> callList3 = new CallableClass(400_000, 600_000, list);
        Callable<Integer> callList4 = new CallableClass(600_000, 800_000, list);
        Callable<Integer> callList5 = new CallableClass(800_000, 1_000_000, list);
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        List<Future<Integer>> futures = executorService.invokeAll(List.of(callList1, callList2,
                callList3, callList4, callList5));
        int sum = futures.stream().mapToInt(integerFuture -> {
            try {
                return integerFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return 0;
        }).sum();

        System.out.println(sum);
    }
}
