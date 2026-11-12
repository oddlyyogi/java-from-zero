import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

void main() throws InterruptedException {
    Map<String, Integer> checkouts = new ConcurrentHashMap<>();
    List<String> titles = List.of("Dune", "Emma", "Dune", "Beloved");
    Runnable desk = () -> {
        for (int i = 0; i < 10_000; i++) {
            for (String title : titles) {
                checkouts.merge(title, 1, Integer::sum);
            }
        }
    };
    Thread front = Thread.ofPlatform().name("front-desk").start(desk);
    Thread back = Thread.ofVirtual().name("back-desk").start(desk);
    front.join();
    back.join();
    IO.println(new TreeMap<>(checkouts));
}
