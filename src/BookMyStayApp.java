import java.util.LinkedList;
import java.util.Queue;

class ReservationRequest {
    private String guestName;
    private String roomType;
    private int stayDuration;

    public ReservationRequest(String guestName, String roomType, int stayDuration) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.stayDuration = stayDuration;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public int getStayDuration() { return stayDuration; }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room: " + roomType + " | Duration: " + stayDuration + " nights";
    }
}

class BookingQueueManager {
    private Queue<ReservationRequest> requestQueue;

    public BookingQueueManager() {
        this.requestQueue = new LinkedList<>();
    }

    public void addRequest(ReservationRequest request) {
        requestQueue.add(request);
        System.out.println("Enqueued: " + request.getGuestName() + "'s request for " + request.getRoomType());
    }

    public void displayQueue() {
        System.out.println("\n--- Current Booking Request Queue (FIFO) ---");
        if (requestQueue.isEmpty()) {
            System.out.println("The queue is currently empty.");
        } else {
            int position = 1;
            for (ReservationRequest request : requestQueue) {
                System.out.println(position + ". " + request);
                position++;
            }
        }
        System.out.println("--------------------------------------------\n");
    }

    public ReservationRequest nextRequest() {
        return requestQueue.peek();
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("BookMyStay System v5.0 - Request Intake Module\n");

        BookingQueueManager queueManager = new BookingQueueManager();

        System.out.println("Receiving incoming booking requests...");
        queueManager.addRequest(new ReservationRequest("Alice", "Suite", 3));
        queueManager.addRequest(new ReservationRequest("Bob", "Single Room", 1));
        queueManager.addRequest(new ReservationRequest("Charlie", "Double Room", 2));
        queueManager.addRequest(new ReservationRequest("Diana", "Suite", 5));

        queueManager.displayQueue();

        ReservationRequest next = queueManager.nextRequest();
        if (next != null) {
            System.out.println("Ready to process next request: " + next.getGuestName());
        }

        System.out.println("Note: No inventory has been modified. Requests are waiting for allocation.");
    }
}