import java.util.concurrent.atomic.AtomicInteger;

public class ThreadClass extends Thread {
    private static final int UPPER_BOUND = 100;
    private final String threadName;
    private final AtomicInteger counter;

    public ThreadClass(String threadName, AtomicInteger counter) {
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
