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
    public SingleRoom() {
        super("Single Room", 100.0, 1);
    }

    @Override
    public void displayFeatures() {
        System.out.println("Features: Twin bed, Work desk, High-speed Wi-Fi.");
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 150.0, 2);
    }

    @Override
    public void displayFeatures() {
        System.out.println("Features: Queen bed, Small lounge area, Mini-fridge.");
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite", 300.0, 4);
    }

    @Override
    public void displayFeatures() {
        System.out.println("Features: King bed, Separate living room, Kitchenette, Ocean view.");
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("--- Hotel Management System v2.0 ---");

        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 1;

        printRoomStatus(single, singleAvailable);
        printRoomStatus(dbl, doubleAvailable);
        printRoomStatus(suite, suiteAvailable);
    }

    private static void printRoomStatus(Room room, int count) {
        room.displayBasicInfo();
        room.displayFeatures();
        System.out.println("Current Availability: " + count);
        System.out.println("------------------------------------");
    }
}