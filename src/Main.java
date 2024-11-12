import java.util.concurrent.Semaphore;

class Philosopher extends Thread {
    private final int id;
    private final Semaphore leftFork;
    private final Semaphore rightFork;

    public Philosopher(int id, Semaphore leftFork, Semaphore rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void think(int i) {
        System.out.println("Philosopher " + id + " thinking " + i + " time");
        try {
            Thread.sleep(500); // Імітація часу мислення
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void eat(int i) {
        System.out.println("Philosopher " + id + " eating " + i + " time");
        try {
            Thread.sleep(500); // Імітація часу їжі
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            think(i);

            try {
                // Для філософа з ID 0 спершу беремо праву виделку, потім ліву
                if (id == 0) {
                    rightFork.acquire();
                    System.out.println("Philosopher " + id + " took right fork");
                    leftFork.acquire();
                    System.out.println("Philosopher " + id + " took left fork");
                } else {
                    leftFork.acquire();
                    System.out.println("Philosopher " + id + " took left fork");
                    rightFork.acquire();
                    System.out.println("Philosopher " + id + " took right fork");
                }

                eat(i);

                rightFork.release();
                System.out.println("Philosopher " + id + " put right fork");
                leftFork.release();
                System.out.println("Philosopher " + id + " put left fork");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

