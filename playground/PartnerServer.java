import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;

static final Map<String, String> BOOKS = Map.of("9780141439518", """
        {"isbn": "9780141439518", "title": "Pride and Prejudice",
         "author": "Jane Austen", "published": 1813, "copies": 3}""");

void reply(HttpExchange exchange, int status, String body) throws IOException {
    byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
    exchange.getResponseHeaders().set("Content-Type", "application/json");
    exchange.sendResponseHeaders(status, bytes.length);
    try (var out = exchange.getResponseBody()) {
        out.write(bytes);
    }
}

void main() throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8095), 0);
    server.createContext("/books/", exchange -> {
        String isbn = exchange.getRequestURI().getPath().substring("/books/".length());
        String book = BOOKS.get(isbn);
        if (book == null) {
            reply(exchange, 404, "{\"error\": \"not found\"}");
        } else {
            reply(exchange, 200, book);
        }
    });
    server.createContext("/requests", exchange -> {
        byte[] raw = exchange.getRequestBody().readAllBytes();
        String body = new String(raw, StandardCharsets.UTF_8);
        IO.println("copy request: " + body);
        reply(exchange, 201, body);
    });
    server.start();
    IO.println("partner library on http://localhost:8095");
}
