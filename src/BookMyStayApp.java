import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

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
    private Map<String, Integer> roomInventory = new HashMap<>();

    public void initializeRoom(String type, int count) {
        roomInventory.put(type, count);
    }

    public int getAvailability(String type) {
        return roomInventory.getOrDefault(type, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return new HashMap<>(roomInventory);
    }
}

class RoomSearchService {
    public void searchAvailableRooms(List<Room> roomCatalog, InventoryManager inventory) {
        System.out.println("--- Available Accommodations ---");
        boolean found = false;

        for (Room room : roomCatalog) {
            int count = inventory.getAvailability(room.getType());

            if (count > 0) {
                room.displayBasicInfo();
                room.displayFeatures();
                System.out.println("Rooms Remaining: " + count);
                System.out.println("--------------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms are currently available.");
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("BookMyStay System v4.0 - Search Module\n");

        InventoryManager inventory = new InventoryManager();
        List<Room> roomCatalog = new ArrayList<>();

        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();

        roomCatalog.add(single);
        roomCatalog.add(dbl);
        roomCatalog.add(suite);

        inventory.initializeRoom(single.getType(), 5);
        inventory.initializeRoom(dbl.getType(), 0); // Out of stock
        inventory.initializeRoom(suite.getType(), 2);

        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(roomCatalog, inventory);
    }
}