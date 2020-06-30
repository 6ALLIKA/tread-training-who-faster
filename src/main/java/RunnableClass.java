import java.util.concurrent.atomic.AtomicInteger;

public class RunnableClass implements Runnable {
    private static final int UPPER_BOUND = 100;
    private final String threadName;
    private final AtomicInteger counter;

    public RunnableClass(String threadName, AtomicInteger counter) {
        this.threadName = threadName;
        this.counter = counter;
        System.out.println("Creating " + threadName);
    }

    @Override
    public synchronized void run() {
        while (counter.get() < UPPER_BOUND) {
            System.out.println(threadName + ": " + counter.getAndIncrement());
        }
    }
}
