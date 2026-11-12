package shelf;

/// A request typed at the library desk.
///
/// Every kind of request is a **record**, and the list of kinds is closed,
/// so a `switch` over a `Command` can handle all of them.
public sealed interface Command {
    record Checkout(String isbn, int memberId) implements Command {
    }

    record Return(String isbn) implements Command {
    }

    record Renew(String isbn) implements Command {
    }

    record Search(String text) implements Command {
    }

    record Report(String kind) implements Command {
    }

    record Unknown(String line) implements Command {
    }

    /// Turns a line such as `checkout 9780441013593 2` into a command.
    static Command parse(String line) {
        String[] w = line.strip().split(" ");
        return switch (w[0]) {
            case "checkout" -> w.length == 3 ? new Checkout(w[1], id(w[2])) : new Unknown(line);
            case "return" -> w.length == 2 ? new Return(w[1]) : new Unknown(line);
            case "renew" -> w.length == 2 ? new Renew(w[1]) : new Unknown(line);
            case "search" -> new Search(line.strip().substring("search".length()).strip());
            case "report" -> w.length == 2 ? new Report(w[1]) : new Unknown(line);
            default -> new Unknown(line);
        };
    }

    private static int id(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException _) {
            return -1;
        }
    }
}
