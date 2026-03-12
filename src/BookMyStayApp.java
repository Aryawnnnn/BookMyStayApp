import java.util.HashMap;
import java.util.Map;

abstract class Room {
    private String type;
    private double price;
    private int capacity;

    public Room(String type, double price, int capacity) {
        this.type = type;
        this.price = price;
        this.capacity = capacity;
    }

    public String getType() { return type; }
    public double getPrice() { return price; }
    public int getCapacity() { return capacity; }

    public abstract void displayFeatures();

    public void displayBasicInfo() {
        System.out.println("Type: " + type + " | Price: $" + price + " | Capacity: " + capacity);
    }
}

class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 100.0, 1); }
    @Override
    public void displayFeatures() { System.out.println("Features: Twin bed, Work desk."); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 150.0, 2); }
    @Override
    public void displayFeatures() { System.out.println("Features: Queen bed, Mini-fridge."); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite", 300.0, 4); }
    @Override
    public void displayFeatures() { System.out.println("Features: King bed, Kitchenette."); }
}

class InventoryManager {
    private Map<String, Integer> roomInventory;

    public InventoryManager() {
        this.roomInventory = new HashMap<>();
    }

    public void initializeRoom(String type, int count) {
        roomInventory.put(type, count);
    }

    public int getAvailability(String type) {
        return roomInventory.getOrDefault(type, 0);
    }

    public void updateAvailability(String type, int change) {
        if (roomInventory.containsKey(type)) {
            int current = roomInventory.get(type);
            roomInventory.put(type, current + change);
        }
    }

    public void displayFullInventory() {
        System.out.println("\n--- Current Inventory State ---");
        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
        System.out.println("-------------------------------\n");
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Initializing BookMyStay System v3.0...");

        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();

        InventoryManager inventory = new InventoryManager();
        inventory.initializeRoom(single.getType(), 10);
        inventory.initializeRoom(dbl.getType(), 5);
        inventory.initializeRoom(suite.getType(), 2);

        single.displayBasicInfo();
        System.out.println("Initial Stock: " + inventory.getAvailability(single.getType()));

        System.out.println("\nSimulating a booking for a Suite...");
        inventory.updateAvailability(suite.getType(), -1);

        inventory.displayFullInventory();
    }
}