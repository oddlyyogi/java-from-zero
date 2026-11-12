import java.util.concurrent.atomic.AtomicInteger;

class Counter {
    private final AtomicInteger count = new AtomicInteger();

    void increment() {
        count.incrementAndGet();
    }

    int get() {
        return count.get();
    }
}

void main() throws InterruptedException {
    Counter counter = new Counter();
    Runnable work = () -> {
        for (int i = 0; i < 100_000; i++) {
            counter.increment();
        }
    };
    Thread a = new Thread(work);
    Thread b = new Thread(work);
    a.start();
    b.start();
    a.join();
    b.join();
    IO.println("count = " + counter.get());
}
