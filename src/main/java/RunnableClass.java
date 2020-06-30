import java.util.concurrent.atomic.AtomicInteger;

public class RunnableClass implements Runnable {
    private static final int UPPER_BOUND = 100;
    private final AtomicInteger counter;

    public RunnableClass(AtomicInteger counter) {
        this.counter = counter;
    }

    @Override
    public synchronized void run() {
        while (counter.get() < UPPER_BOUND) {
            System.out.println(Thread.currentThread().getName() + ": " + counter.getAndIncrement());
        }
    }
}
