void main() {
    LocalDate borrowed = LocalDate.of(2026, 10, 20);
    LocalDate due = borrowed.plusDays(21);
    IO.println(borrowed + " -> " + due + " " + due.getDayOfWeek());
    LocalDate today = LocalDate.of(2026, 11, 12);
    IO.println(today.isAfter(due) + " " + ChronoUnit.DAYS.between(due, today));
    Period member = Period.between(LocalDate.of(2020, 3, 15), today);
    IO.println(member + " " + member.getYears());
    IO.println(LocalDate.of(2026, 1, 31).plusMonths(1));
    DateTimeFormatter f = DateTimeFormatter.ofPattern("EEE d MMM uuuu", Locale.UK);
    IO.println(due.format(f));
    IO.println(LocalDate.parse("2026-11-12").plusWeeks(2));
    var noon = ZonedDateTime.of(2026, 3, 28, 12, 0, 0, 0, ZoneId.of("Europe/London"));
    IO.println(noon.plusDays(1));
    IO.println(noon.plus(Duration.ofHours(24)));
    LocalDateTime start = LocalDateTime.of(2026, 11, 12, 9, 30);
    IO.println(Duration.between(start, start.plusHours(36).plusMinutes(15)));
    IO.println(start.atZone(noon.getZone()).withZoneSameInstant(ZoneId.of("Asia/Tokyo")));
    Clock clock = Clock.fixed(Instant.parse("2026-11-12T10:00:00Z"), ZoneOffset.UTC);
    IO.println(LocalDate.now(clock) + " " + Instant.now(clock));
}
