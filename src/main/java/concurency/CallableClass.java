package concurency;

import java.util.List;
import java.util.concurrent.Callable;

public class CallableClass implements Callable<Integer> {
    private int start;
    private int end;
    private final List<Integer> list;

    public CallableClass(int start, int end, List<Integer> list) {
        this.start = start;
        this.end = end;
        this.list = list;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (; start < end; start++) {
            sum += list.get(start);
        }
        return sum;
    }
}
