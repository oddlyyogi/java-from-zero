record Pair<A, B>(A first, B second) {
}

record Box<T>(T value) {
}

void main() {
    List<String> raw = new ArrayList<>();
    raw.add("Dune");
    IO.println(raw.get(0).length());
    Box<String> b = new Box<>("Dune");
    IO.println(b.value().length());
    Box<Integer> n = new Box<>(42);
    IO.println(n.value() + 1);
    IO.println(firstOr(List.of("Emma", "Dune"), "none"));
    IO.println(firstOr(List.<Integer>of(), 0));
    Pair<String, Integer> copies = new Pair<>("Dune", 3);
    IO.println(copies + " " + swap(copies));
    IO.println(max(List.of("Dune", "Ulysses", "Emma")) + " " + max(List.of(7, 21, 3)));
    IO.println(new ArrayList<String>().getClass() == new ArrayList<Integer>().getClass());
}

static <T> T firstOr(List<T> list, T fallback) {
    return list.isEmpty() ? fallback : list.getFirst();
}

static <A, B> Pair<B, A> swap(Pair<A, B> p) {
    return new Pair<>(p.second(), p.first());
}

static <T extends Comparable<? super T>> T max(List<T> items) {
    T best = items.getFirst();
    for (T item : items) {
        if (item.compareTo(best) > 0) best = item;
    }
    return best;
}
