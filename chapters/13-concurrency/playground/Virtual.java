import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

long run(ExecutorService executor) {
    long start = System.nanoTime();
    try (executor) {
        for (int i = 0; i < 10_000; i++) {
            executor.submit(() -> {
                Thread.sleep(Duration.ofMillis(100));
                return null;
            });
        }
    }
    return (System.nanoTime() - start) / 1_000_000;
}

void main() {
    IO.println("100 platform threads: " + run(Executors.newFixedThreadPool(100)) + " ms");
    IO.println("virtual threads: " + run(Executors.newVirtualThreadPerTaskExecutor()) + " ms");
}
