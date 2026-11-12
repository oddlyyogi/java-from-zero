package shelf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

public final class CatalogFiles {
    private static final JsonMapper MAPPER = JsonMapper.builder()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .build();

    private CatalogFiles() {
    }

    public static void export(List<Book> books, Path file) throws IOException {
        Files.createDirectories(file.toAbsolutePath().getParent());
        Files.writeString(file, MAPPER.writeValueAsString(books));
    }

    public static List<Book> parse(String json) {
        return MAPPER.readValue(json, new TypeReference<List<Book>>() {});
    }

    public static List<Book> load(Path file) throws IOException {
        return parse(Files.readString(file));
    }
}
