import java.io.*;
import java.util.HashMap;
import java.util.Map;

class InventoryManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> stock = new HashMap<>();

    public void setStock(String type, int count) { stock.put(type, count); }
    public Map<String, Integer> getStock() { return stock; }
}

public class BookMyStayApp {
    private static final String FILE_NAME = "system_state.ser";

    public static void main(String[] args) {
        System.out.println("BookMyStay v12.0 - Persistence & Recovery Module\n");

        InventoryManager inventory = loadState();

        if (inventory == null) {
            System.out.println("No saved state found. Initializing new system...");
            inventory = new InventoryManager();
            inventory.setStock("Suite", 5);
        } else {
            System.out.println("State recovered successfully: " + inventory.getStock());
        }

        // Simulate activity
        inventory.setStock("Suite", inventory.getStock().get("Suite") - 1);

        saveState(inventory);
        System.out.println("State saved. System terminating.");
    }

    private static void saveState(InventoryManager inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            System.out.println("Inventory data persisted to disk.");
        } catch (IOException e) {
            System.err.println("Failed to save state: " + e.getMessage());
        }
    }

    private static InventoryManager loadState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (InventoryManager) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Recovery failed, starting fresh: " + e.getMessage());
            return null;
        }
    }
}