import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AddOnService {
    private String name;
    private double price;

    public AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}

class AddOnManager {
    private Map<String, List<AddOnService>> reservationAddOns;

    public AddOnManager() {
        this.reservationAddOns = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        reservationAddOns.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        System.out.println("Service Added: " + service.getName() + " to Reservation " + reservationId);
    }

    public double calculateTotalAddOnCost(String reservationId) {
        List<AddOnService> services = reservationAddOns.get(reservationId);
        if (services == null) return 0.0;

        double total = 0;
        for (AddOnService s : services) {
            total += s.getPrice();
        }
        return total;
    }

    public void displayAddOns(String reservationId) {
        List<AddOnService> services = reservationAddOns.get(reservationId);
        if (services != null && !services.isEmpty()) {
            System.out.println("Add-ons for " + reservationId + ": " + services);
        } else {
            System.out.println("No add-ons for " + reservationId);
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("BookMyStay v7.0 - Add-On Services Module\n");

        AddOnManager addOnManager = new AddOnManager();

        AddOnService wifi = new AddOnService("Premium Wi-Fi", 15.0);
        AddOnService breakfast = new AddOnService("Buffet Breakfast", 25.0);
        AddOnService spa = new AddOnService("Spa Treatment", 80.0);

        String resId1 = "S101";
        String resId2 = "D202";

        System.out.println("--- Processing Add-Ons ---");
        addOnManager.addService(resId1, wifi);
        addOnManager.addService(resId1, breakfast);
        addOnManager.addService(resId2, spa);

        System.out.println("\n--- Summary ---");
        printReservationSummary(addOnManager, resId1);
        printReservationSummary(addOnManager, resId2);
    }

    private static void printReservationSummary(AddOnManager manager, String resId) {
        manager.displayAddOns(resId);
        double extra = manager.calculateTotalAddOnCost(resId);
        System.out.println("Total Extra Charges for " + resId + ": $" + extra);
        System.out.println("------------------------------------");
    }
}