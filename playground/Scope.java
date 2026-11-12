import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

String title(String isbn) throws InterruptedException {
    try {
        Thread.sleep(300);
    } catch (InterruptedException e) {
        IO.println("  title lookup cancelled");
        throw e;
    }
    return "Dune";
}

String member(int id) throws InterruptedException {
    Thread.sleep(100);
    if (id == 0) {
        throw new IllegalArgumentException("no member 0");
    }
    return "Ada Lovelace";
}

String loanLabel(String isbn, int id) throws InterruptedException {
    try (var scope = StructuredTaskScope.open()) {
        Subtask<String> t = scope.fork(() -> title(isbn));
        Subtask<String> m = scope.fork(() -> member(id));
        scope.join();
        return m.get() + " has " + t.get();
    }
}

void main() throws InterruptedException {
    long start = System.nanoTime();
    IO.println(loanLabel("9780441013593", 1));
    IO.println("ok after " + (System.nanoTime() - start) / 1_000_000 + " ms");
    start = System.nanoTime();
    try {
        IO.println(loanLabel("9780441013593", 0));
    } catch (StructuredTaskScope.FailedException e) {
        IO.println("failed: " + e.getCause().getMessage());
    }
    IO.println("failed after " + (System.nanoTime() - start) / 1_000_000 + " ms");
}
