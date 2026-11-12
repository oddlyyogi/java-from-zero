sealed interface Shape permits Circle, Square {
}

record Circle(double r) implements Shape {
}

record Square(double side) implements Shape {
}

void main() {
    Object o = 42;
    if (o instanceof Integer i && i > 40) {
        IO.println("big number " + i);
    }
    if (!(o instanceof String s)) {
        IO.println("not a string");
    } else {
        IO.println(s.length());
    }
    IO.println(area(new Circle(1)) + " " + area(new Square(3)));
    for (Object v : java.util.Arrays.asList(null, 150, 7, "  ", "Dune", 2.5)) {
        IO.println(describe(v));
    }
    IO.println(perimeter(new Square(3)));
    try {
        Integer.parseInt("x");
    } catch (NumberFormatException _) {
        IO.println("not a number");
    }
    int count = 0;
    for (var _ : List.of("a", "b", "c")) {
        count++;
    }
    IO.println(count);
    new TreeMap<>(Map.of("a", 1, "b", 2)).forEach((_, v) -> IO.println("value " + v));
    if (new Square(2) instanceof Square(_)) {
        IO.println("a square of some size");
    }
}

static double area(Shape shape) {
    return switch (shape) {
        case Circle c -> Math.PI * c.r() * c.r();
        case Square s -> s.side() * s.side();
    };
}

static String describe(Object value) {
    return switch (value) {
        case null -> "nothing at all";
        case Integer i when i > 100 -> "a big number";
        case Integer i -> "the number " + i;
        case String s when s.isBlank() -> "an empty string";
        case String s -> "the text " + s;
        default -> "something else: " + value.getClass().getSimpleName();
    };
}

static double perimeter(Shape shape) {
    return switch (shape) {
        case Circle(double r) -> 2 * Math.PI * r;
        case Square(var side) -> 4 * side;
    };
}
