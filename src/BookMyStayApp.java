import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.HashSet;
import java.util.Set;

class CancellationException extends Exception {
    public CancellationException(String message) {
        super(message);
    }
}

class InventoryManager {
    private Map<String, Integer> availability = new HashMap<>();
    private Map<String, Set<String>> activeAllocations = new HashMap<>();
    private Stack<String> releasedRoomIds = new Stack<>();

    public void initializeRoomType(String type, int count) {
        availability.put(type, count);
        activeAllocations.put(type, new HashSet<>());
    }

    public void confirmBooking(String type, String roomId) {
        activeAllocations.get(type).add(roomId);
        availability.put(type, availability.get(type) - 1);
    }

    public void cancelBooking(String type, String roomId) throws CancellationException {
        // Validation: Ensure the reservation exists
        if (!activeAllocations.containsKey(type) || !activeAllocations.get(type).contains(roomId)) {
            throw new CancellationException("Cancellation Failed: Room " + roomId + " is not currently booked under " + type);
        }

        // Controlled Mutation: Reversing the state
        activeAllocations.get(type).remove(roomId);
        availability.put(type, availability.get(type) + 1);

        // Tracking released IDs for potential reuse (LIFO)
        releasedRoomIds.push(roomId);

        System.out.println(">>> SUCCESS: Room " + roomId + " has been returned to " + type + " inventory.");
    }

    public void displayStatus() {
        System.out.println("\n--- Current Inventory Status ---");
        availability.forEach((type, count) -> {
            System.out.println(type + ": " + count + " available | Active IDs: " + activeAllocations.get(type));
        });
        if (!releasedRoomIds.isEmpty()) {
            System.out.println("Recently Released IDs (Stack): " + releasedRoomIds);
        }
        System.out.println("--------------------------------\n");
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("BookMyStay v10.0 - Booking Cancellation Module\n");

        InventoryManager inventory = new InventoryManager();
        inventory.initializeRoomType("Suite", 2);

        // Pre-populating some confirmed bookings
        inventory.confirmBooking("Suite", "S-101");
        inventory.confirmBooking("Suite", "S-102");

        System.out.println("Initial State:");
        inventory.displayStatus();

        // Scenario 1: Valid Cancellation
        try {
            System.out.println("Requesting cancellation for S-102...");
            inventory.cancelBooking("Suite", "S-102");
        } catch (CancellationException e) {
            System.out.println(e.getMessage());
        }

        // Scenario 2: Invalid Cancellation (Already cancelled or non-existent)
        try {
            System.out.println("\nRequesting cancellation for S-102 again...");
            inventory.cancelBooking("Suite", "S-102");
        } catch (CancellationException e) {
            System.out.println("EXPECTED ERROR: " + e.getMessage());
        }

        // Scenario 3: Invalid Room Type
        try {
            System.out.println("\nRequesting cancellation for Deluxe-999...");
            inventory.cancelBooking("Deluxe", "D-999");
        } catch (CancellationException e) {
            System.out.println("EXPECTED ERROR: " + e.getMessage());
        }

        inventory.displayStatus();
    }
}