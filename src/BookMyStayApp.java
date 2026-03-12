import java.util.HashMap;
import java.util.Map;

// Custom Exception for Domain-Specific Errors
class BookingValidationException extends Exception {
    public BookingValidationException(String message) {
        super(message);
    }
}

class InventoryManager {
    private Map<String, Integer> availability = new HashMap<>();

    public void initializeRoomType(String type, int count) {
        availability.put(type, count);
    }

    // Guarding System State with Validation
    public void validateRequest(String roomType, int requestedRooms) throws BookingValidationException {
        if (!availability.containsKey(roomType)) {
            throw new BookingValidationException("Error: Room type '" + roomType + "' does not exist in our catalog.");
        }

        int currentStock = availability.get(roomType);
        if (currentStock < requestedRooms) {
            throw new BookingValidationException("Error: Insufficient inventory for " + roomType +
                    ". Requested: " + requestedRooms + ", Available: " + currentStock);
        }

        if (requestedRooms <= 0) {
            throw new BookingValidationException("Error: Stay duration or room count must be greater than zero.");
        }
    }

    public void updateInventory(String type, int change) {
        availability.put(type, availability.get(type) + change);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + availability);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("BookMyStay v9.0 - Validation & Error Handling\n");

        InventoryManager inventory = new InventoryManager();
        inventory.initializeRoomType("Suite", 1);
        inventory.initializeRoomType("Single", 5);

        // Test Scenario 1: Invalid Room Type
        processBooking(inventory, "Penthouse", 1);

        // Test Scenario 2: Insufficient Stock
        processBooking(inventory, "Suite", 2);

        // Test Scenario 3: Valid Booking
        processBooking(inventory, "Single", 1);

        System.out.println("\nFinal State Check:");
        inventory.displayInventory();
    }

    private static void processBooking(InventoryManager inventory, String type, int count) {
        System.out.println("Attempting to book " + count + " " + type + "...");
        try {
            // Fail-Fast: Validate before doing anything else
            inventory.validateRequest(type, count);

            // If we reach here, validation passed
            inventory.updateInventory(type, -count);
            System.out.println(">>> SUCCESS: Booking confirmed for " + type);

        } catch (BookingValidationException e) {
            // Graceful failure handling
            System.out.println(">>> FAILED: " + e.getMessage());
        }
        System.out.println("------------------------------------------------");
    }
}