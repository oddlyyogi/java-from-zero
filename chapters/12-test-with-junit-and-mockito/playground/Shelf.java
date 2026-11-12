void main(String[] args) {
    IO.println("Welcome to Shelf, the library app");
    if (args.length == 0) {
        IO.println("Usage: java Shelf.java list | due DAYS | find TITLE | fine DAYS [TYPE]");
        return;
    }
    String command = args[0];
    switch (command) {
        case "list" -> {
            List<String> books = Catalog.titles();
            for (String title : books) {
                IO.println("- " + title);
            }
            IO.println(books.size() + " books on the shelf");
        }
        case "find" -> {
            String wanted = args[1];
            String[][] shelves = { {"Dune", "Emma"}, {"Beloved", "Ulysses", "Middlemarch"} };
            boolean found = false;
            search:
            for (int s = 0; s < shelves.length; s++) {
                for (String title : shelves[s]) {
                    if (title.equalsIgnoreCase(wanted)) {
                        IO.println(title + " is on shelf " + (s + 1));
                        found = true;
                        break search;
                    }
                }
            }
            if (!found) IO.println(wanted + " is not on any shelf");
        }
        case "due" -> {
            int days = Integer.parseInt(args[1]);
            IO.println("Due in " + days / 7 + " weeks and " + days % 7 + " days");
            for (int day = 7; day <= days; day += 7) {
                IO.println("Reminder on day " + day);
            }
        }
        case "fine" -> {
            int daysLate = Integer.parseInt(args[1]);
            String type = args.length > 2 ? args[2] : "adult";
            int centsPerDay = switch (type) {
                case "child", "student" -> 10;
                case "senior" -> 0;
                case "adult" -> 25;
                default -> {
                    IO.println("Unknown member type, using the adult rate");
                    yield 25;
                }
            };
            int fine = Math.min(daysLate * centsPerDay, 500);
            IO.println("Fine for " + type + ": " + fine + " cents");
        }
        default -> IO.println("Unknown command: " + command);
    }
}
