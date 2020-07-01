import java.util.concurrent.atomic.AtomicInteger;

public class ThreadClass extends Thread {
    private static final int UPPER_BOUND = 100;
    private final AtomicInteger counter;

    public ThreadClass(AtomicInteger counter) {
        this.counter = counter;
    }

    @Override
    public synchronized void run() {
        while (counter.get() < UPPER_BOUND) {
            System.out.println(Thread.currentThread().getName() + ": " + counter.getAndIncrement());
        }
    }
}
