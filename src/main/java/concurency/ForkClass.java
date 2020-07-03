package concurency;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkClass extends RecursiveTask<Long> {
    private static final int THRESHOLD = 200_000;
    private final List<Integer> list;

    public ForkClass(List<Integer> list) {
        this.list = list;
    }

    @Override
    protected Long compute() {
        if (list.size() > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubTasks())
                    .stream()
                    .mapToLong(ForkJoinTask::join)
                    .sum();
        }

        return processing(list);
    }

    private List<ForkClass> createSubTasks() {
        return List.of(
                new ForkClass(
                        list.subList(0, list.size() / 2)),
                new ForkClass(
                        list.subList(list.size() / 2, list.size())));
    }

    private long processing(List<Integer> list) {
        return list.stream().mapToLong(value -> value).sum();
    }
}
