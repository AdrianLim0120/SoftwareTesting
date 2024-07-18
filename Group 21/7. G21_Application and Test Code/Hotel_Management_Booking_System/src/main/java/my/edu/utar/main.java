package my.edu.utar;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        // Create room setup
        Room room = new Room(1, 1, 0); // 2 VIP, 5 Deluxe, 8 Standard rooms available

        // Create waiting lists
        WaitingList waitingList = new WaitingList();

        // Create a printer instance
        Printer printer = new Printer();

        // Create the booking system
        Booking booking = new Booking(room, waitingList, printer);

        // Create some users
        User vipUser = new User("Alice", "VIP", false);
        User memberUser = new User("Bob", "Member", true); // This member has an exclusive reward
        User nonMemberUser = new User("Charlie", "Non-member", false);

        // Simulate bookings
        System.out.println("Initial bookings:");
        booking.setBooking(vipUser, 3); // VIP user books 3 VIP room
        booking.setBooking(memberUser, 2); // Member tries to book 2 rooms, has exclusive reward
        booking.setBooking(nonMemberUser, 1); // Non-member books 1 standard room

        // Display current status of the waiting lists
        System.out.println("Waiting Lists after bookings:");
        System.out.println("VIP List: " + waitingList.getWaitingList("VIP"));
        System.out.println("Member List: " + waitingList.getWaitingList("Member"));
        System.out.println("Non-member List: " + waitingList.getWaitingList("Non-member"));

        // Cancel a booking
        System.out.println("\nCancelling a booking for Alice:");
        booking.cancelBooking(vipUser);

        // Check rooms and lists after cancellation
        System.out.println("\nRoom availability after cancellation:");
        System.out.println("VIP Rooms: " + room.checkRoom("VIP"));
        System.out.println("Deluxe Rooms: " + room.checkRoom("Deluxe"));
        System.out.println("Standard Rooms: " + room.checkRoom("Standard"));

        System.out.println("\nWaiting Lists after cancellation:");
        System.out.println("VIP List: " + waitingList.getWaitingList("VIP"));
        System.out.println("Member List: " + waitingList.getWaitingList("Member"));
        System.out.println("Non-member List: " + waitingList.getWaitingList("Non-member"));
    }
}

