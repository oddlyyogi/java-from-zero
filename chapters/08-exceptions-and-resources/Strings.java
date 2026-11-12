void main() {
    String title = "  The Left Hand of Darkness  ";
    String clean = title.strip();
    IO.println("[" + clean + "] " + clean.length());
    clean.toUpperCase();
    IO.println(clean);
    String upper = clean.toUpperCase();
    IO.println(upper);
    IO.println(clean.charAt(4) + " " + clean.indexOf("Hand") + " " + clean.substring(4, 8));
    IO.println(clean.contains("Dark") + " " + clean.startsWith("The") + " " + "   ".isBlank());
    String a = "Dune";
    String b = "Dune";
    String c = new String("Dune");
    IO.println((a == b) + " " + (a == c));
    IO.println(a.equals(c) + " " + "dune".equalsIgnoreCase(a));
    String csv = "9780441013593,Dune,Frank Herbert,1965";
    String[] parts = csv.split(",");
    IO.println(parts.length + " parts, author: " + parts[2]);
    IO.println(String.join(" | ", parts));
    IO.println("-".repeat(20));
    StringBuilder sb = new StringBuilder();
    for (String p : parts) {
        sb.append(p.toUpperCase()).append(';');
    }
    sb.setLength(sb.length() - 1);
    IO.println(sb);
    IO.println("%-12s|%6.2f|%03d".formatted("Fine", 1.5, 7));
    String block = """
        Riverside Library
          Opening hours: \
        9 to 5
        Ends with a space:\s
        """;
    IO.println(block.lines().count() + " lines");
    IO.print(block.replace(" ", "."));
    IO.println("apple".compareTo("banana") + " " + "Zebra".compareTo("apple"));
    IO.println("zebra".compareToIgnoreCase("apple"));
    IO.println(String.valueOf(42) + 1);
    IO.println(Integer.parseInt("42") + 1);
}
