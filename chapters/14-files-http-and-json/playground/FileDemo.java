import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;

void main() throws IOException {
    Path dir = Path.of("data");
    Path log = dir.resolve("loans.log");
    IO.println(log.getFileName() + " in folder " + log.getParent());
    IO.println(log + " is absolute? " + log.isAbsolute());
    IO.println(log.toAbsolutePath());
    Files.createDirectories(dir);
    Files.writeString(log, "Dune,Ada Lovelace\n");
    try (BufferedWriter out = Files.newBufferedWriter(log, StandardOpenOption.APPEND)) {
        out.write("Emma,Sam Okafor");
        out.newLine();
    }
    Files.writeString(log, "Beloved,Ada Lovelace\n", StandardOpenOption.APPEND);

    IO.print(Files.readString(log));
    List<String> lines = Files.readAllLines(log);
    IO.println(lines.size() + " lines, first: " + lines.getFirst());
    try (Stream<String> stream = Files.lines(log)) {
        long ada = stream.filter(line -> line.endsWith("Ada Lovelace")).count();
        IO.println(ada + " loans for Ada");
    }
    Path backup = dir.resolve("loans.bak");
    Files.copy(log, backup, StandardCopyOption.REPLACE_EXISTING);
    try (Stream<Path> paths = Files.walk(dir)) {
        paths.sorted().forEach(path -> IO.println("found: " + path));
    }
    IO.println("backup deleted? " + Files.deleteIfExists(backup));
    try {
        Files.readString(dir.resolve("missing.txt"));
    } catch (NoSuchFileException e) {
        IO.println("no such file: " + e.getMessage());
    }
}
