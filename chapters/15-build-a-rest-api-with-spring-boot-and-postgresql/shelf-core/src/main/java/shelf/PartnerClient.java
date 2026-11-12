package shelf;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import tools.jackson.databind.json.JsonMapper;

public class PartnerClient {
    record PartnerBook(String isbn, String title, String author,
                       @JsonProperty("published") int year) {
        Book toBook() {
            return new Book(isbn, title, author, year);
        }
    }

    record CopyRequest(String isbn, String library) {
    }

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(2))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();
    private final JsonMapper mapper = JsonMapper.builder().build();
    private final URI base;

    public PartnerClient(URI base) {
        this.base = base;
    }

    public Optional<Book> find(String isbn) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(base.resolve("/books/" + isbn))
                .timeout(Duration.ofSeconds(5))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response =
                http.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 404) {
            return Optional.empty();
        }
        if (response.statusCode() != 200) {
            throw new IOException("partner answered " + response.statusCode());
        }
        PartnerBook book = mapper.readValue(response.body(), PartnerBook.class);
        return Optional.of(book.toBook());
    }

    public int requestCopy(String isbn, String library)
            throws IOException, InterruptedException {
        String json = mapper.writeValueAsString(new CopyRequest(isbn, library));
        HttpRequest request = HttpRequest.newBuilder(base.resolve("/requests"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return http.send(request, HttpResponse.BodyHandlers.discarding()).statusCode();
    }

    public CompletableFuture<Integer> statusAsync(String isbn) {
        HttpRequest request = HttpRequest.newBuilder(base.resolve("/books/" + isbn)).build();
        return http.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                .thenApply(HttpResponse::statusCode);
    }
}
