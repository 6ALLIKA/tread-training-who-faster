import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger counter = new AtomicInteger();
        Thread firstThread = new ThreadClass(counter);
        Thread secondThread = new Thread(new RunnableClass(counter));
        firstThread.setName("Thread");
        secondThread.setName("Runnable");
        firstThread.start();
        secondThread.start();
        firstThread.join();
        secondThread.join();
        System.out.println("Returning to main thread");
        System.out.println("Value: " + counter.get());
    }
}
