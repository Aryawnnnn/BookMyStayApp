import java.util.LinkedList;
import java.util.Queue;

class Inventory {
    private int suiteCount;

    public Inventory(int count) {
        this.suiteCount = count;
    }

    // Critical Section: Only one thread can execute this method at a time
    public synchronized boolean bookSuite(String guestName) {
        if (suiteCount > 0) {
            System.out.println(guestName + " is attempting to book... (Available: " + suiteCount + ")");
            try { Thread.sleep(100); } catch (InterruptedException e) {} // Simulate processing delay
            suiteCount--;
            System.out.println(">>> SUCCESS: " + guestName + " confirmed. (Remaining: " + suiteCount + ")");
            return true;
        } else {
            System.out.println(">>> FAILED: " + guestName + " could not book. (No suites left)");
            return false;
        }
    }

    public int getSuiteCount() { return suiteCount; }
}

class BookingTask implements Runnable {
    private Inventory inventory;
    private String guestName;

    public BookingTask(Inventory inventory, String guestName) {
        this.inventory = inventory;
        this.guestName = guestName;
    }

    @Override
    public void run() {
        inventory.bookSuite(guestName);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("BookMyStay v11.0 - Concurrent Booking Simulation\n");

        Inventory sharedInventory = new Inventory(2); // Only 2 suites available

        // Creating multiple threads to simulate concurrent guests
        Thread guest1 = new Thread(new BookingTask(sharedInventory, "Guest-Alice"));
        Thread guest2 = new Thread(new BookingTask(sharedInventory, "Guest-Bob"));
        Thread guest3 = new Thread(new BookingTask(sharedInventory, "Guest-Charlie"));

        System.out.println("Starting concurrent bookings...");
        guest1.start();
        guest2.start();
        guest3.start();

        guest1.join();
        guest2.join();
        guest3.join();

        System.out.println("\nFinal Inventory Count: " + sharedInventory.getSuiteCount());
    }
}