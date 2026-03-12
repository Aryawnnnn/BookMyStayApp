import java.util.ArrayList;
import java.util.List;

class ConfirmedBooking {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double totalCost;

    public ConfirmedBooking(String reservationId, String guestName, String roomType, double totalCost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.totalCost = totalCost;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public double getTotalCost() { return totalCost; }

    @Override
    public String toString() {
        return String.format("ID: %s | Guest: %-8s | Room: %-12s | Total Paid: $%.2f",
                reservationId, guestName, roomType, totalCost);
    }
}

class BookingHistory {
    private List<ConfirmedBooking> historyRecords;

    public BookingHistory() {
        this.historyRecords = new ArrayList<>();
    }

    public void recordBooking(ConfirmedBooking booking) {
        historyRecords.add(booking);
    }

    public List<ConfirmedBooking> getRecords() {
        return new ArrayList<>(historyRecords); // Defensive copy
    }
}

class ReportService {
    public void generateSummaryReport(BookingHistory history) {
        List<ConfirmedBooking> records = history.getRecords();
        double totalRevenue = 0;

        System.out.println("\n--- Administrative Booking Report ---");
        if (records.isEmpty()) {
            System.out.println("No records found in history.");
        } else {
            for (ConfirmedBooking record : records) {
                System.out.println(record);
                totalRevenue += record.getTotalCost();
            }
            System.out.println("-------------------------------------");
            System.out.println("Total Bookings Processed: " + records.size());
            System.out.println("Total Revenue Generated:  $" + totalRevenue);
        }
        System.out.println("-------------------------------------\n");
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("BookMyStay v8.0 - Reporting & History Module\n");

        BookingHistory history = new BookingHistory();
        ReportService reportService = new ReportService();

        // Simulating the recording of confirmed transactions
        System.out.println("Archiving confirmed bookings...");
        history.recordBooking(new ConfirmedBooking("RES101", "Alice", "Suite", 340.0));
        history.recordBooking(new ConfirmedBooking("RES102", "Bob", "Single Room", 115.0));
        history.recordBooking(new ConfirmedBooking("RES103", "Charlie", "Double Room", 150.0));

        // Generate the report for the Admin
        reportService.generateSummaryReport(history);

        System.out.println("Audit trail complete. System terminating safely.");
    }
}