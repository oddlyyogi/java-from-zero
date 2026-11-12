import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

String lookup(String isbn) throws InterruptedException {
    Thread.sleep(200);
    return "title " + isbn;
}

void main() throws Exception {
    List<String> isbns = List.of("1", "2", "3", "4", "5", "6", "7", "8");
    long start = System.nanoTime();
    for (String isbn : isbns) {
        lookup(isbn);
    }
    IO.println("one by one: " + (System.nanoTime() - start) / 1_000_000 + " ms");

    start = System.nanoTime();
    try (ExecutorService pool = Executors.newFixedThreadPool(4)) {
        Future<String> first = pool.submit(() -> lookup("1"));
        IO.println(first.get());
        List<Callable<String>> tasks = new ArrayList<>();
        for (String isbn : isbns) {
            tasks.add(() -> lookup(isbn));
        }
        List<Future<String>> results = pool.invokeAll(tasks);
        IO.println(results.size() + " results, last: " + results.getLast().get());
    }
    IO.println("four threads: " + (System.nanoTime() - start) / 1_000_000 + " ms");
}
