import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

String slowTitle(String isbn) {
    try {
        Thread.sleep(isbn.equals("slow") ? 2000 : 200);
    } catch (InterruptedException e) {
        throw new IllegalStateException(e);
    }
    if (isbn.isBlank()) {
        throw new IllegalArgumentException("blank ISBN");
    }
    return isbn.equals("9780441013593") ? "Dune" : "Emma";
}

void main() {
    var dune = CompletableFuture.supplyAsync(() -> slowTitle("9780441013593"));
    var emma = CompletableFuture.supplyAsync(() -> slowTitle("9780141439587"));
    CompletableFuture<String> loud = dune.thenApply(String::toUpperCase);
    CompletableFuture<String> both = dune.thenCombine(emma, (a, b) -> a + " and " + b);
    IO.println(loud.join());
    IO.println(both.join());

    var broken = CompletableFuture.supplyAsync(() -> slowTitle(" "))
            .exceptionally(e -> "unknown (" + e.getCause().getMessage() + ")");
    IO.println(broken.join());

    var late = CompletableFuture.supplyAsync(() -> slowTitle("slow"))
            .orTimeout(500, TimeUnit.MILLISECONDS)
            .exceptionally(e -> "gave up: " + e.getClass().getSimpleName());
    IO.println(late.join());

    List<CompletableFuture<String>> all = List.of(dune, emma, broken);
    CompletableFuture.allOf(all.toArray(CompletableFuture[]::new)).join();
    IO.println(all.stream().map(CompletableFuture::join).toList());
}
