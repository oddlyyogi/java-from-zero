class Door implements AutoCloseable {
    private final String name;

    Door(String name) {
        this.name = name;
        IO.println("open " + name);
    }

    void use(boolean fail) {
        IO.println("use " + name);
        if (fail) throw new IllegalStateException("jammed: " + name);
    }

    @Override
    public void close() {
        IO.println("close " + name);
        if (name.equals("back")) throw new IllegalStateException("stuck: " + name);
    }
}


void main() {
    try {
        IO.println("before");
        checkOut("x");
        IO.println("never printed");
    } catch (IllegalArgumentException e) {
        IO.println("caught: " + e.getMessage());
    } finally {
        IO.println("finally runs");
    }
    IO.println("after");
    try {
        IO.println(Files.readString(Path.of("missing.txt")));
    } catch (IOException e) {
        IO.println("could not read: " + e);
    }
    try {
        IO.println(readNotes());
    } catch (IOException e) {
        IO.println("no notes: " + e.getMessage());
    }
    try {
        down(1);
    } catch (StackOverflowError e) {
        IO.println("caught " + e);
    }
    try {
        Objects.requireNonNull(null, "member must not be null");
    } catch (NullPointerException | IllegalStateException e) {
        IO.println(e.getClass().getSimpleName() + ": " + e.getMessage());
    }
    try (Door front = new Door("front"); Door side = new Door("side")) {
        side.use(false);
    }
    try (Door back = new Door("back")) {
        back.use(true);
    } catch (IllegalStateException e) {
        IO.println("door: " + e.getMessage());
        for (Throwable s : e.getSuppressed()) {
            IO.println("  suppressed: " + s.getMessage());
        }
    }
    IO.println(risky());
    try {
        IO.println("leaving with code 3");
        System.exit(3);
    } finally {
        IO.println("finally after exit?");
    }
}

static void findBook(String isbn) {
    throw new IllegalArgumentException("no book with ISBN " + isbn);
}

static void checkOut(String isbn) {
    findBook(isbn);
}

static String readNotes() throws IOException {
    return Files.readString(Path.of("notes.txt"));
}

static int down(int n) {
    return down(n + 1);
}

@SuppressWarnings("finally")
static int risky() {
    try {
        return 1;
    } finally {
        return 2;
    }
}
