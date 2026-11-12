void main() {
    List<String> titles = List.of("Dune", "Emma", "Beloved",
            "Ulysses", "Middlemarch", "Hamlet");
    Comparator<String> byLength = new Comparator<String>() {
        @Override
        public int compare(String a, String b) {
            return Integer.compare(a.length(), b.length());
        }
    };
    List<String> sorted = new ArrayList<>(titles);
    sorted.sort(byLength);
    IO.println(sorted);
    sorted.sort((a, b) -> Integer.compare(b.length(), a.length()));
    IO.println(sorted);
    Predicate<String> isShort = t -> t.length() <= 4;
    Function<String, Integer> length = t -> t.length();
    Supplier<String> greeting = () -> "Welcome";
    BiFunction<Integer, Integer, Integer> fine = (days, rate) -> Math.min(days * rate, 500);
    IO.println(isShort.test("Dune") + " " + length.apply("Ulysses"));
    IO.println(greeting.get() + " " + fine.apply(30, 25));
    Consumer<String> shout = t -> IO.println(t.toUpperCase() + "!");
    shout.accept("dune");
    int limit = 5;
    Predicate<String> fits = t -> t.length() < limit;
    Function<String, Integer> parse = Integer::parseInt;
    String prefix = "Shelf: ";
    Function<String, String> label = prefix::concat;
    UnaryOperator<String> upper = String::toUpperCase;
    Supplier<List<String>> fresh = ArrayList::new;
    IO.println(parse.apply("42") + 1 + " " + label.apply("Dune"));
    IO.println(upper.apply("emma") + " " + fresh.get());
    Predicate<String> isLong = isShort.negate();
    Predicate<String> longM = isLong.and(t -> t.startsWith("M"));
    Function<String, String> trim = String::strip;
    Function<String, Integer> trimmedLength = trim.andThen(String::length);
    IO.println(longM.test("Middlemarch") + " " + longM.test("Hamlet"));
    IO.println(trimmedLength.apply("  Emma "));
    List<String> loud = titles.stream()
            .filter(t -> t.length() > 4)
            .map(String::toUpperCase)
            .sorted()
            .toList();
    IO.println(loud);
    String firstM = titles.stream()
            .peek(t -> IO.println("  looking at " + t))
            .filter(t -> t.startsWith("M"))
            .findFirst()
            .orElse("none");
    IO.println(firstM);
    var groups = titles.stream()
            .collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.toList()));
    IO.println(groups);
    IO.println(titles.stream().collect(Collectors.partitioningBy(t -> t.length() > 5)));
    String joined = titles.stream().collect(Collectors.joining(", ", "[", "]"));
    IO.println(joined + " " + titles.stream().filter(t -> t.contains("e")).count());
    IO.println(titles.stream().mapToInt(String::length).summaryStatistics());
    List<List<String>> shelves = List.of(List.of("Dune", "Emma"), List.of("Beloved", "Dune"));
    IO.println(shelves.stream().flatMap(List::stream).distinct().toList());
    Optional<String> z = titles.stream().filter(t -> t.startsWith("Z")).findFirst();
    IO.println(z + " " + z.map(String::length).orElse(0));
    titles.stream().filter(t -> t.startsWith("H")).findFirst()
            .ifPresentOrElse(t -> IO.println("found " + t), () -> IO.println("nothing"));
    IO.println(titles.stream().limit(3).map(String::length).reduce(0, Integer::sum));
    IO.println(titles.stream().gather(Gatherers.windowFixed(2)).toList());
    var once = titles.stream();
    IO.println(once.count());
}
