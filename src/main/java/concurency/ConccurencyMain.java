package concurency;

public class ConccurencyMain {

    public static void main(String[] args) throws InterruptedException {
        ListService listService = new ListService(1_000_000);

        long startCallable = System.currentTimeMillis();
        long sumCallable = listService.calculateCallablesResult();
        long endCallable = System.currentTimeMillis();

        System.out.println("Callable " + sumCallable + " processed for "
                + (startCallable - endCallable));

        long startFork = System.currentTimeMillis();
        long sumFork = listService.calculateForkResult();
        long endFork = System.currentTimeMillis();

        System.out.println("Fork " + sumFork + " processed for " + (startFork - endFork));
    }
}
