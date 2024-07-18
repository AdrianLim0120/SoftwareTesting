package my.edu.utar;

import java.util.*;

public class Booking {
    private Room room;
    private WaitingList waitingList;
    private Printer printer;
    
    public Booking(Room room, WaitingList waitingList, Printer printer) {
        this.room = room;
        this.waitingList = waitingList;
        this.printer = printer;
    }

    public void setBooking(User user, int numOfRooms) {
        if (user.getMemberType().equals("VIP")) {
            bookVipMember(user, numOfRooms);
        } else if (user.getMemberType().equals("Member")) {
            bookNormalMember(user, numOfRooms);
        } else {
            bookNonMember(user,numOfRooms);
        }
    }

    public void bookVipMember(User user, int numOfRooms) {
        if (numOfRooms > 3 || numOfRooms <= 0 ) {
        	throw new IllegalArgumentException("VIP members can book up to 3 rooms only and negative value is invalid");
        }
    
        for (int i = 0; i < numOfRooms; i++) {
            boolean roomBooked = false;  // Flag to check if the room has been booked in this iteration
    
            if (room.checkRoom("VIP") > 0) {
                room.bookRoom(user, "VIP");
                printer.printInfo(user.getName(), user.getMemberType(), "VIP");
                roomBooked = true;
                
            } else if (room.checkRoom("Deluxe") > 0) {
                room.bookRoom(user, "Deluxe");
                printer.printInfo(user.getName(), user.getMemberType(), "Deluxe");
                roomBooked = true;
                
            } else if (room.checkRoom("Standard") > 0) {
                room.bookRoom(user, "Standard");
                printer.printInfo(user.getName(), user.getMemberType(), "Standard");
                roomBooked = true;
            }
    
            if (!roomBooked) {
                // All room types are fully booked, add to VIP waiting list
                waitingList.addWaiting(user, "VIP");
                printer.printInfo(user.getName(), user.getMemberType(), "Added to VIP List");
                break;  // Exit the loop as we have added the user to the waiting list
            }
        }
    }

    public void bookNormalMember(User user, int numOfRooms) {
        // Normal members can book up to 2 rooms
        boolean exclusiveRewardRedeemed = false;
        if (numOfRooms > 2 || numOfRooms <= 0) {
        	throw new IllegalArgumentException("Normal members can book up to 2 rooms only and negative value is invalid");
        } else {
            for (int i = 0; i < numOfRooms; i++) {
            	boolean roomBooked = false;
            	if (user.getExclReward() && !exclusiveRewardRedeemed && room.checkRoom("VIP") > 0) {
                    room.bookRoom(user, "VIP");
                    printer.printInfo(user.getName(), user.getMemberType(), "VIP");
                    user.setExclReward(false); // Exclusive reward is now redeemed
                    exclusiveRewardRedeemed = true;
                    roomBooked = true;
                    
                } else if (room.checkRoom("Deluxe") > 0) {
                    room.bookRoom(user, "Deluxe");
                    printer.printInfo(user.getName(), user.getMemberType(), "Deluxe");
                    roomBooked = true;
                    
                } else if (room.checkRoom("Standard") > 0) {
                    room.bookRoom(user, "Standard");
                    printer.printInfo(user.getName(), user.getMemberType(), "Standard");
                    roomBooked = true;
                }

                if (!roomBooked) {
                    waitingList.addWaiting(user, "Member");
                    printer.printInfo(user.getName(), user.getMemberType(), "Added to Member List");
                    break; // Exit the loop as we have added the user to the waiting list
                }
            }
        }
    }

    public void bookNonMember(User user, int numOfRooms) {
    	if (numOfRooms <= 0 || numOfRooms > 1) {
        	throw new IllegalArgumentException("Non-members can book up to 1 rooms only and negative value is invalid");
        } else {
        	 for (int i = 1; i <= 1 ; i++) {
	        // Non-members can book only one Standard room
		        if (room.checkRoom("Standard") > 0) {
		            room.bookRoom(user, "Standard");
		            printer.printInfo(user.getName(), "Non-member", "Standard");
		        } else {
		            // Standard room is fully booked, add to normal waiting list
		            waitingList.addWaiting(user, "Non-member");
		            printer.printInfo(user.getName(), "Non-member", "Added to Non-member List");
		        }
        	 }
        }
    }

    public void cancelBooking(User user) {
        // Retrieve the list of rooms booked by the user
        List<String> bookedRooms = room.getBookedRoomsByUser(user);
        List<String> waitingListRooms = waitingList.getWaitingList(user.getMemberType());
        
        // if user booked the room and would like to cancel, then release the room 
        if (!bookedRooms.isEmpty()) {
            room.releaseRoom(user);
        } else if (waitingListRooms.contains(user.getName())){
            // The user was on the waiting list and not in actual booked rooms
            waitingList.removeWaiting(user, user.getMemberType());
        } else
            throw new IllegalArgumentException("User not found in the waiting list");

        // Inform the user of the cancellation
        printer.printInfo(user.getName(), user.getMemberType(), "Booking cancelled");
    }
}
