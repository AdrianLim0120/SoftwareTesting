package my.edu.utar;

import java.util.*;

public class Room {
    private int vip;
    private int deluxe;
    private int standard;
    private Map<User, List<String>> userBookings;
    
    public Room() {
    }

    public Room(int vip, int deluxe, int standard) {
        this.vip = vip;
        this.deluxe = deluxe;
        this.standard = standard;
        this.userBookings = new HashMap<>();
    }

    public int checkRoom(String roomType) {
        switch (roomType) {
            case "VIP":
                return vip;
            case "Deluxe":
                return deluxe;
            case "Standard":
                return standard;
            default:
                return 0;
        }
    }

    public void bookRoom(User user, String roomType) {
        switch (roomType) {
            case "VIP":
                if (vip > 0) {
                    vip--;
                    addUserBooking(user, roomType);
                }
                break;
            case "Deluxe":
                if (deluxe > 0) {
                    deluxe--;
                    addUserBooking(user, roomType);
                }
                break;
            case "Standard":
                if (standard > 0) {
                    standard--;
                    addUserBooking(user, roomType);
                }
                break;
        }
    }

    public void releaseRoom(User user) {
        // Release all rooms booked by a user
        List<String> bookedRooms = getBookedRoomsByUser(user);
        if (bookedRooms != null) {
            for (String roomType : bookedRooms) {
                switch (roomType) {
                    case "VIP":
                        vip++;
                        break;
                    case "Deluxe":
                        deluxe++;
                        break;
                    case "Standard":
                        standard++;
                        break;
                }
            }
            userBookings.remove(user); // Remove the user from the booking map
        }
    }

    public void addUserBooking(User user, String roomType) {
        List<String> bookings = userBookings.containsKey(user) ? userBookings.get(user) : new ArrayList<>();
        bookings.add(roomType);
        userBookings.put(user, bookings);
    }

    public List<String> getBookedRoomsByUser(User user) {
        return userBookings.containsKey(user) ? userBookings.get(user) : new ArrayList<>();
    } 
}
