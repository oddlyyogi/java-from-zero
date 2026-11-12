class Card {
    final String name;

    Card(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card)) return false;
        Card other = (Card) o;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

void main() {
    String[] fixed = {"Dune", "Emma"};
    IO.println(fixed.length + " " + Arrays.toString(fixed));
    List<String> titles = new ArrayList<>();
    titles.add("Dune");
    titles.add("Emma");
    titles.add("Beloved");
    titles.add(1, "Ulysses");
    IO.println(titles + " size " + titles.size() + ", get(1) " + titles.get(1));
    titles.remove("Emma");
    IO.println(titles + " " + titles.contains("Emma") + " " + titles.indexOf("Beloved"));
    IO.println(titles.getFirst() + " " + titles.getLast() + " " + titles.reversed());
    List<String> sorted = new ArrayList<>(titles);
    Collections.sort(sorted);
    IO.println(sorted);
    sorted.sort(Comparator.reverseOrder());
    IO.println(sorted);
    Set<String> hash = new HashSet<>(List.of("Dune", "Emma", "Beloved", "Ulysses"));
    Set<String> linked = new LinkedHashSet<>(List.of("Dune", "Emma", "Beloved", "Ulysses"));
    Set<String> tree = new TreeSet<>(List.of("Dune", "Emma", "Beloved", "Ulysses"));
    IO.println(hash.add("Dune") + " " + hash.size());
    IO.println(linked + " " + tree);
    Map<String, Integer> copies = new HashMap<>();
    copies.put("Dune", 2);
    copies.put("Emma", 1);
    IO.println(copies.put("Dune", 3) + " " + copies.get("Dune") + " " + copies.get("Ulysses"));
    IO.println(copies.getOrDefault("Ulysses", 0));
    copies.merge("Emma", 1, Integer::sum);
    copies.merge("Ulysses", 1, Integer::sum);
    copies.putIfAbsent("Dune", 99);
    Map<String, Integer> byTitle = new TreeMap<>(copies);
    for (Map.Entry<String, Integer> e : byTitle.entrySet()) {
        IO.println(e.getKey() + " x" + e.getValue());
    }
    Deque<String> waitlist = new ArrayDeque<>();
    waitlist.addLast("Ada");
    waitlist.addLast("Grace");
    waitlist.addFirst("Sam");
    IO.println(waitlist + " next: " + waitlist.peekFirst());
    IO.println("served: " + waitlist.pollFirst() + ", left: " + waitlist);
    List<String> shelf = new ArrayList<>(List.of("Ulysses", "Dune", "Emma"));
    Iterator<String> it = shelf.iterator();
    while (it.hasNext()) {
        if (it.next().startsWith("U")) it.remove();
    }
    IO.println(shelf);
    Set<Card> cards = new HashSet<>();
    cards.add(new Card("Ada"));
    cards.add(new Card("Ada"));
    IO.println(cards.size());
    Integer x = 127, y = 127;
    Integer p = 128, q = 128;
    IO.println((x == y) + " " + (p == q) + " " + p.equals(q));
    List<String> live = new ArrayList<>(List.of("x"));
    List<String> view = Collections.unmodifiableList(live);
    List<String> copy = List.copyOf(live);
    live.add("y");
    IO.println(view + " " + copy);
    SequencedMap<String, Integer> returns = new LinkedHashMap<>();
    returns.put("Emma", 1);
    returns.put("Dune", 2);
    returns.put("Beloved", 3);
    IO.println(returns.firstEntry() + " " + returns.lastEntry());
    IO.println(returns.pollFirstEntry() + " " + returns);
    IO.println(byTitle.keySet() + " " + byTitle.values() + " " + byTitle.containsKey("Emma"));
}
