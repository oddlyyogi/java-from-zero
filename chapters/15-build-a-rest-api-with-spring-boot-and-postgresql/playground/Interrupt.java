void main() throws InterruptedException {
    Thread worker = Thread.ofPlatform().name("reminder-worker").start(() -> {
        IO.println(Thread.currentThread().getName() + " started");
        try {
            Thread.sleep(10_000);
            IO.println("finished");
        } catch (InterruptedException e) {
            IO.println(Thread.currentThread().getName() + " was interrupted");
        }
    });
    Thread.sleep(200);
    worker.interrupt();
    worker.join();
    IO.println("alive? " + worker.isAlive());
}
