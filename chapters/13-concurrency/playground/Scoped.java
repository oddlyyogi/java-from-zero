static final ScopedValue<String> LIBRARIAN = ScopedValue.newInstance();

void audit(String action) {
    String who = LIBRARIAN.isBound() ? LIBRARIAN.get() : "nobody";
    IO.println(who + ": " + action);
}

void checkout(String title) {
    audit("lent " + title);
}

void main() {
    ScopedValue.where(LIBRARIAN, "Grace").run(() -> {
        checkout("Dune");
        ScopedValue.where(LIBRARIAN, "Alan").run(() -> checkout("Emma"));
        checkout("Beloved");
    });
    checkout("Ulysses");
}
