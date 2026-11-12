void main() {
    IO.println("Welcome to Shelf, the library app");
    List<String> books = Catalog.titles();
    for (String title : books) {
        IO.println("- " + title);
    }
    IO.println(books.size() + " books on the shelf");
    String name = IO.readln("Your name: ");
    IO.println("Happy reading, " + name + "!");
}
