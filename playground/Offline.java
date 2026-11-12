import java.io.IOException;
import java.net.URI;
import shelf.PartnerClient;

void main() throws InterruptedException {
    PartnerClient partner = new PartnerClient(URI.create("http://localhost:8095"));
    try {
        partner.find("9780141439518");
    } catch (IOException e) {
        IO.println("partner offline: " + e.getClass().getSimpleName());
    }
}
