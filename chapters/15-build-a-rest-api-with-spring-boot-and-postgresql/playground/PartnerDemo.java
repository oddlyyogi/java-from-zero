import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import shelf.*;

void main() throws Exception {
    PartnerClient partner = new PartnerClient(URI.create("http://localhost:8095"));
    IO.println(partner.find("9780141439518"));
    IO.println(partner.find("0000000000000"));
    IO.println("request: " + partner.requestCopy("9780141439518", "Riverside Library"));
    var isbns = List.of("9780141439518", "1", "2");
    var futures = isbns.stream().map(partner::statusAsync).toList();
    IO.println(futures.stream().map(f -> f.join()).toList());
    List<Book> books = new ArrayList<>();
    books.add(new Book("9780441013593", "Dune", "Frank Herbert", 1965));
    partner.find("9780141439518").ifPresent(books::add);
    Path file = Path.of("exports/catalogue.json");
    CatalogFiles.export(books, file);
    IO.println(CatalogFiles.load(file).size() + " books loaded back");
}
