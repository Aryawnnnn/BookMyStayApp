import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

class ReservationRequest {
    private String guestName;
    private String roomType;

    public ReservationRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class InventoryManager {
    private Map<String, Integer> availability = new HashMap<>();
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public void initializeRoomType(String type, int count) {
        availability.put(type, count);
        allocatedRooms.put(type, new HashSet<>());
    }

    public int getCount(String type) {
        return availability.getOrDefault(type, 0);
    }

    public boolean allocateRoom(String type, String roomId) {
        if (availability.getOrDefault(type, 0) > 0) {
            // Uniqueness check via Set
            if (allocatedRooms.get(type).add(roomId)) {
                availability.put(type, availability.get(type) - 1);
                return true;
            }
        }
        return false;
    }

    public void displayStatus() {
        System.out.println("\n--- Final System State ---");
        availability.forEach((type, count) -> {
            System.out.println(type + " -> Available: " + count + " | Assigned: " + allocatedRooms.get(type));
        });
        System.out.println("--------------------------");
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("BookMyStay v6.0 - Room Allocation Service\n");

        InventoryManager inventory = new InventoryManager();
        inventory.initializeRoomType("Suite", 2);
        inventory.initializeRoomType("Single", 5);

        Queue<ReservationRequest> queue = new LinkedList<>();
        queue.add(new ReservationRequest("Alice", "Suite"));
        queue.add(new ReservationRequest("Bob", "Suite"));
        queue.add(new ReservationRequest("Charlie", "Suite")); // Should fail (only 2 suites)

        int roomCounter = 101;

        while (!queue.isEmpty()) {
            ReservationRequest request = queue.poll();
            String generatedId = request.getRoomType().substring(0, 1) + roomCounter++;

            System.out.print("Processing " + request.getGuestName() + "... ");

            if (inventory.allocateRoom(request.getRoomType(), generatedId)) {
                System.out.println("SUCCESS. Assigned Room: " + generatedId);
            } else {
                System.out.println("FAILED. No availability for " + request.getRoomType());
            }
        }

        inventory.displayStatus();
    }
}