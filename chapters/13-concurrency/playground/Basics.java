void main() {
    int books = 1200;
    long isbn = 9780441013593L;
    double price = 12.5;
    boolean available = true;
    char shelf = 'B';
    IO.println(books + " " + isbn + " " + price + " " + available + " " + shelf);
    IO.println(Integer.MAX_VALUE);
    IO.println(Integer.MAX_VALUE + 1);
    IO.println(Long.MAX_VALUE);
    IO.println(7 / 2);
    IO.println(7 % 2);
    IO.println(7 / 2.0);
    IO.println(0.1 + 0.2);
    IO.println(Math.max(3, 8) + " " + Math.round(2.5) + " " + Math.sqrt(16));
    IO.println((int) 3.99);
    IO.println((byte) 200);
    IO.println('A' + 1);
    IO.println((char) ('A' + 1));
    var title = "Dune";
    final int LOAN_DAYS = 14;
    int n = 5;
    IO.println(n++);
    IO.println(n);
    IO.println(++n);
    n += 10;
    IO.println(n);
    String note = null;
    IO.println(note != null && note.length() > 3);
    IO.println(n > 10 ? "big" : "small");
    int copies = 3;
    while (copies > 0) {
        IO.println("Lending copy " + copies);
        copies--;
    }
    do {
        IO.println("do-while runs at least once");
    } while (copies > 0);
    for (int i = 1; i <= 6; i++) {
        if (i % 2 == 1) continue;
        IO.println("even " + i);
    }
    String day = "SAT";
    switch (day) {
        case "SAT":
            IO.println("Open 10 to 4");
        case "SUN":
            IO.println("Closed");
            break;
        default:
            IO.println("Open 9 to 8");
    }
}
