package my.edu.utar;

import org.junit.*;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.mockito.Mockito.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class BookingTest {

    User userMock = mock(User.class);
    WaitingList waitingListMock = mock(WaitingList.class);
    Room roomMock = mock(Room.class);
    Printer printerMock = mock(Printer.class);
    Booking booking = new Booking(roomMock, waitingListMock, printerMock);    

    @Test
    @Parameters({"John,VIP,true,1","Jonathon,Member,false,1","KeeMX,Non-member,false,1"})
    public void testSetBooking(String name, String memberType, boolean exclReward,int numberOfRooms) {

        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);

        when(roomMock.checkRoom("VIP")).thenReturn(5); // Example value for VIP room availability
        when(roomMock.checkRoom("Deluxe")).thenReturn(5); // Example value for Deluxe room availability
        when(roomMock.checkRoom("Standard")).thenReturn(5); // Example value for Standard room availability
        
        booking.setBooking(userMock, numberOfRooms);

        // Verify the appropriate method is called based on memberType
        if (memberType.equals("VIP")){ 
        	verify(roomMock, times(1)).checkRoom("VIP");
        } else if (memberType.equals("Member")) {
        	verify(roomMock, times(1)).checkRoom("Deluxe");
        } else {
        	verify(roomMock, times(1)).checkRoom("Standard");
        }
    }
    
    //VIP userMock
    private Object[] getParamTestCharge() {
		return new Object[] {
				new Object[]{"John","VIP",true,1},
                new Object[]{"John","VIP",true,2},
                new Object[]{"John","VIP",true,3}
		};
	}
    
    @Test
    @Parameters(method="getParamTestCharge")
    // Test booking VIP room for a VIP userMock
    public void testSetBookingVipRoomForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(4).thenReturn(3).thenReturn(2).thenReturn(1).thenReturn(0);
        
        booking.bookVipMember(userMock, numberOfRooms);
        
        verify(roomMock,times(numberOfRooms)).bookRoom(userMock, "VIP");
        verify(printerMock,times(numberOfRooms)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
    }

    @Test
    @Parameters(method="getParamTestCharge")
    public void testSetBookingDeluxeRoomForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
 
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);    	
        when(roomMock.checkRoom("VIP")).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(3).thenReturn(2).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Standard")).thenReturn(0); 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, times(numberOfRooms)).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(numberOfRooms)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
    }

    @Test
    @Parameters(method="getParamTestCharge")
    public void testSetBookingStandardRoomForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
           
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(0); 
        when(roomMock.checkRoom("Standard")).thenReturn(3).thenReturn(2).thenReturn(1).thenReturn(0); 
        
        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, times(numberOfRooms)).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, times(numberOfRooms)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
    }
    
    @Test
    @Parameters(method="getParamTestCharge")
    public void testSetBookingWaitingListForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(0); 
        when(roomMock.checkRoom("Standard")).thenReturn(0); 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Standard"));
        
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to VIP List");
    }

    private Object[] getParamTestCharge0() {
		return new Object[] {
                new Object[]{"John","VIP",true,2},
		};
	}

    @Test
    @Parameters(method="getParamTestCharge0")
    public void testSetBooking1VipAnd1DeluxeRoomForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(1).thenReturn(0);  

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Deluxe"));

        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
    }
    
    @Test
    @Parameters(method="getParamTestCharge0")
    public void testSetBooking1DeluxeAnd1StandardRoomForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(1).thenReturn(0);  
        when(roomMock.checkRoom("Standard")).thenReturn(1).thenReturn(0); 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
    }

    @Test
    @Parameters(method="getParamTestCharge0")
    public void testSetBooking1VipAnd1StandardRoomForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(0);
        when(roomMock.checkRoom("Standard")).thenReturn(1).thenReturn(0);  

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
    }
    
    @Test
    @Parameters(method="getParamTestCharge0")
    public void testSetBooking1VipRoomAnd1WaitingListForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(0); 
        when(roomMock.checkRoom("Standard")).thenReturn(0); 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Standard"));
        
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to VIP List");
    }

    @Test
    @Parameters(method="getParamTestCharge0")
    public void testSetBooking1DeluxeAnd1WaitingListForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(1).thenReturn(0); ; 
        when(roomMock.checkRoom("Standard")).thenReturn(0); ; 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to VIP List");
    }

    @Test
    @Parameters(method="getParamTestCharge0")
    public void testSetBooking1StandardAnd1WaitingListForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(0); 
        when(roomMock.checkRoom("Standard")).thenReturn(1).thenReturn(0); ; 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to VIP List");
    }

    private Object[] getParamTestCharge1() {
		return new Object[] {
				new Object[]{"John","VIP",true,3}
		};
	}

    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBooking2VipAnd1DeluxeRoomForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {

        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(2).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(1).thenReturn(0);  

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, times(2)).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Deluxe"));

        verify(printerMock, times(2)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
    }

    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBooking2VipAnd1StandardRoomForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(2).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(0);
        when(roomMock.checkRoom("Standard")).thenReturn(1).thenReturn(0);  

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, times(2)).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, times(2)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
    }
    
    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBooking2VipRoomAnd1WaitingListForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(2).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(0); 
        when(roomMock.checkRoom("Standard")).thenReturn(0); 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, times(2)).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, times(2)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to VIP List");
    }

    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBooking1VipAnd2DeluxeRoomForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
        
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(2).thenReturn(1).thenReturn(0);  

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, times(2)).bookRoom(eq(userMock), eq("Deluxe"));

        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(2)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
    }

    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBooking2DeluxeAnd1StandardRoomForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0);
        when(roomMock.checkRoom("Deluxe")).thenReturn(2).thenReturn(1).thenReturn(0);  
        when(roomMock.checkRoom("Standard")).thenReturn(1).thenReturn(0); 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, times(2)).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, times(2)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
    }

    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBooking2DeluxeAnd1WaitingListForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(2).thenReturn(1).thenReturn(0); ; 
        when(roomMock.checkRoom("Standard")).thenReturn(0); ; 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, times(2)).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(2)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to VIP List");
    }

    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBooking1VipAnd2StandardRoomForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(0);
        when(roomMock.checkRoom("Standard")).thenReturn(2).thenReturn(1).thenReturn(0);  

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, times(2)).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(2)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
    }

    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBooking1DeluxeAnd2StandardRoomForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0);
        when(roomMock.checkRoom("Deluxe")).thenReturn(1).thenReturn(0);  
        when(roomMock.checkRoom("Standard")).thenReturn(2).thenReturn(1).thenReturn(0);

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, times(2)).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, times(2)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
    }

    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBooking2StandardAnd1WaitingListForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(0); 
        when(roomMock.checkRoom("Standard")).thenReturn(2).thenReturn(1).thenReturn(0); ; 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, times(2)).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, times(2)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to VIP List");
    }

    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBooking1VipRoomAnd2WaitingListForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(0); 
        when(roomMock.checkRoom("Standard")).thenReturn(0); 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to VIP List");
    }

    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBooking1DeluxeAnd2WaitingListForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(1).thenReturn(0); ; 
        when(roomMock.checkRoom("Standard")).thenReturn(0); ; 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to VIP List");
    }

    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBooking1StandardAnd2WaitingListForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {

        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(0); 
        when(roomMock.checkRoom("Standard")).thenReturn(1).thenReturn(0); ; 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to VIP List");
    }

    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBookingVipDeluxeAndStandardRoomForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {

        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Standard")).thenReturn(1).thenReturn(0); 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
    }
    
    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBooking1Vip1DeluxeAnd1WaitingListForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(1).thenReturn(0); ; 
        when(roomMock.checkRoom("Standard")).thenReturn(0); ; 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to VIP List");
    }

    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBooking1Vip1StandardAnd1WaitingListForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(0); ; 
        when(roomMock.checkRoom("Standard")).thenReturn(1).thenReturn(0); ; 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to VIP List");
    }

    @Test
    @Parameters(method="getParamTestCharge1")
    public void testSetBooking1Deluxe1StandardAnd1WaitingListForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
    	
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(1).thenReturn(0); ; 
        when(roomMock.checkRoom("Standard")).thenReturn(1).thenReturn(0); ; 

        booking.bookVipMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to VIP List");
    }
    
    @Test(expected = IllegalArgumentException.class)
    @Parameters({
    	"John,VIP,true,4",
    	"Erica,VIP,false,0",
    	"Alice,VIP,true,two"
    })
    public void illegalTestSetBookingForVipUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
    	booking.bookVipMember(userMock, numberOfRooms);
    }
    
    //Member userMock
    private Object[] getParamTestCharge2() {
        return new Object[] {
                new Object[]{"Bob","Member",false,1},
        };
    }

    @Test
    @Parameters(method = "getParamTestCharge2")
    public void testSetBooking1DeluxeRoomForMemberUserWithoutExclReward(String name, String memberType, boolean exclReward,int numberOfRooms) {
        
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0);
        when(roomMock.checkRoom("Deluxe")).thenReturn(1).thenReturn(0);
        
        booking.bookNormalMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(userMock, "VIP");
        verify(roomMock, times(1)).bookRoom(userMock, "Deluxe");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
    }

    @Test
    @Parameters(method = "getParamTestCharge2")
    public void testSetBooking1StandardRoomForMemberUserWithoutExclReward(String name, String memberType, boolean exclReward,int numberOfRooms) {
        
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0);
        when(roomMock.checkRoom("Deluxe")).thenReturn(0);
        when(roomMock.checkRoom("Standard")).thenReturn(1).thenReturn(0);
        
        booking.bookNormalMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(userMock, "VIP");
        verify(roomMock, never()).bookRoom(userMock, "Deluxe");
        verify(roomMock, times(1)).bookRoom(userMock, "Standard");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
    }

    private Object[] getParamTestCharge3() {
        return new Object[] {
                new Object[]{"Bob","Member",false,2},
                new Object[]{"Bob","Member",true,2}
        };
    }
    @Test
    @Parameters(method = "getParamTestCharge3")
    public void testSetBooking2DeluxeRoomForMemberUserWithAndWithoutExclReward(String name, String memberType, boolean exclReward,int numberOfRooms) {
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0);
        when(roomMock.checkRoom("Deluxe")).thenReturn(2).thenReturn(1).thenReturn(0);
        when(roomMock.checkRoom("Standard")).thenReturn(0);
        
        booking.bookNormalMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(userMock, "VIP");
        verify(roomMock, times(2)).bookRoom(userMock, "Deluxe");
        verify(roomMock, never()).bookRoom(userMock, "Standard");

        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(2)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        if(userMock.getExclReward()) {
        	assertTrue(userMock.getExclReward());
        } else {
        	assertFalse(userMock.getExclReward());
        }
    }    

    @Test
    @Parameters(method = "getParamTestCharge3")
    public void testSetBookingDeluxeAndStandardRoomForMemberUserWithAndWithoutExclReward(String name, String memberType, boolean exclReward,int numberOfRooms) {
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0);
        when(roomMock.checkRoom("Deluxe")).thenReturn(1).thenReturn(0);
        when(roomMock.checkRoom("Standard")).thenReturn(1).thenReturn(0);
        
        booking.bookNormalMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(userMock, "VIP");
        verify(roomMock, times(1)).bookRoom(userMock, "Deluxe");
        verify(roomMock, times(1)).bookRoom(userMock, "Standard");

        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        if(userMock.getExclReward()) {
        	assertTrue(userMock.getExclReward());
        } else {
        	assertFalse(userMock.getExclReward());
        }
    }    

    @Test
    @Parameters(method = "getParamTestCharge3")
    public void testSetBooking2StandardRoomForMemberUserWithandWithoutExclReward(String name, String memberType, boolean exclReward,int numberOfRooms) {
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0);
        when(roomMock.checkRoom("Deluxe")).thenReturn(0);
        when(roomMock.checkRoom("Standard")).thenReturn(2).thenReturn(1).thenReturn(0);
        
        booking.bookNormalMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(userMock, "VIP");
        verify(roomMock, never()).bookRoom(userMock, "Deluxe");
        verify(roomMock, times(2)).bookRoom(userMock, "Standard");

        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, times(2)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        if(userMock.getExclReward()) {
        	assertTrue(userMock.getExclReward());
        } else {
        	assertFalse(userMock.getExclReward());
        }
    }   

    @Test
    @Parameters(method = "getParamTestCharge3")
    public void testSetBooking1DeluxeAnd1WaitingListForMemberUserWithandWithoutExclReward(String name, String memberType, boolean exclReward,int numberOfRooms) {
        
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Standard")).thenReturn(0); 

        booking.bookNormalMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Standard"));

        // Ensure correct booking information is printed
       verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
       verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
       verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
       verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to Member List");
    }

    @Test
    @Parameters(method = "getParamTestCharge3")
    public void testSetBooking1StandardAnd1WaitingListForMemberUserWithandWithoutExclReward(String name, String memberType, boolean exclReward,int numberOfRooms) {
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(0); 
        when(roomMock.checkRoom("Standard")).thenReturn(1).thenReturn(0); 

        booking.bookNormalMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Standard"));

        // Ensure correct booking information is printed
       verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
       verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
       verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
       verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to Member List");
    }

    @Test
    @Parameters({
        "Bob,Member,false,1",
        "Bob,Member,false,2",
        "Bob,Member,true,1",
        "Bob,Member,true,2",})
    public void testSetBookingWaitingListForMemberUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(0); 
        when(roomMock.checkRoom("Standard")).thenReturn(0); 

        booking.bookNormalMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Deluxe"));	
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Standard"));

        // Ensure correct booking information is printed
       verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
       verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
       verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
       verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to Member List");
    }

    @Test
    @Parameters({"Ryan,Member,true,1"})
    public void testSetBooking1VIPRoomForMemberUserWithExclReward(String name, String memberType, boolean exclReward,int numberOfRooms) {
        
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(2).thenReturn(1).thenReturn(0);
        
        booking.bookNormalMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(userMock, "VIP");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
    }

    private Object[] getParamTestCharge4() {
		return new Object[] {
                new Object[]{"Ryan","Member",true,2},
		};
	}   

    @Test
    @Parameters(method="getParamTestCharge4")
    public void testSetBookingVIPAndStandardRoomForMemberUserWithExclReward(String name, String memberType, boolean exclReward,int numberOfRooms) {
        
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(1).thenReturn(0);
        when(roomMock.checkRoom("Deluxe")).thenReturn(0);
        when(roomMock.checkRoom("Standard")).thenReturn(1).thenReturn(0);
        
        booking.bookNormalMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(userMock, "VIP");
        verify(roomMock, never()).bookRoom(userMock, "Deluxe");
        verify(roomMock, times(1)).bookRoom(userMock, "Standard");

        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
    }    

    @Test
    @Parameters(method="getParamTestCharge4")
    public void testSetBookingVIPAndDeluxeRoomForMemberUserWithExclReward(String name, String memberType, boolean exclReward,int numberOfRooms) {
        
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(1).thenReturn(0);
        when(roomMock.checkRoom("Deluxe")).thenReturn(1).thenReturn(0);
        
        booking.bookNormalMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(userMock, "VIP");
        verify(roomMock, times(1)).bookRoom(userMock, "Deluxe");

        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
    } 

    @Test
    @Parameters({"Bob,Member,true,2"})
    public void testSetBooking1VipAnd1WaitingListForMemberUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
       
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("VIP")).thenReturn(1).thenReturn(0); 
        when(roomMock.checkRoom("Deluxe")).thenReturn(0); 
        when(roomMock.checkRoom("Standard")).thenReturn(0); 

        booking.bookNormalMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("VIP"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Deluxe"));
        verify(roomMock, never()).bookRoom(eq(userMock), eq("Standard"));

        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "VIP");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Deluxe");
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to Member List");
    }


    @Test(expected = IllegalArgumentException.class)
    @Parameters({
    	"Bob,Member,false,3",
    	"Ryan,Member,true,0",
    	"Alice,Member,true,two"
    })
    public void illegalTestSetBookingForMemberUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
    	booking.bookNormalMember(userMock, numberOfRooms);
    }
    
    //Non-member userMock
    private Object[] getParamTestCharge5() {
        return new Object[] {
                new Object[]{"KeeMX","Non-member",false,1},
        };
    }

    @Test
    @Parameters(method="getParamTestCharge5")
    public void testSetBookingStandardRoomForNonMemberUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("Standard")).thenReturn(1);
        
        booking.bookNonMember(userMock, numberOfRooms);

        verify(roomMock, times(1)).bookRoom(eq(userMock), eq("Standard"));
        verify(printerMock, times(1)).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
    }

    @Test
    @Parameters(method="getParamTestCharge5")
    public void testSetBookingWaitingListForNonMemberUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.checkRoom("Standard")).thenReturn(0);
        
        booking.bookNonMember(userMock, numberOfRooms);

        verify(roomMock, never()).bookRoom(eq(userMock), eq("Standard"));
        verify(printerMock, never()).printInfo(userMock.getName(), userMock.getMemberType(), "Standard");
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Added to Non-member List");
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
    	"KeeMX,Non-member,false,3",
    	"Chris,Non-member,false,0",
    	"Alice,Non-member,true,two"
    	
    })
    public void illegalTestSetBookingForNonMemberUser(String name, String memberType, boolean exclReward,int numberOfRooms) {
        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
    	booking.bookNormalMember(userMock, numberOfRooms);
    }
    
    //Test cancelBooking method

    private Object[] getParamTestCharge6() {
		return new Object[] {
                new Object[]{"John","VIP",true,"VIP","Deluxe"},
                new Object[]{"Bob","Member",false,"Deluxe","Deluxe"},
                new Object[]{"KeeMX","Non-member",false,"Standard","Standard"},
		};
	}

    @Test
    @Parameters(method="getParamTestCharge6")
    public void testCancelBookingWithBookedRooms(String name, String memberType, boolean exclReward,String roomType1,String roomType2) {

        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        List<String> rooms = Arrays.asList(roomType1, roomType2);
        when(roomMock.getBookedRoomsByUser(userMock)).thenReturn(rooms);

        booking.cancelBooking(userMock);

        verify(roomMock).releaseRoom(userMock);
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Booking cancelled");
    }

    @Test
    @Parameters(method="getParamTestCharge6")
    public void testCancelBookingWithUserOnWaitingListOnly(String name, String memberType, boolean exclReward,String roomType1,String roomType2) {

        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        ArrayList<String> users = new ArrayList<>();
        users.add(userMock.getName());
        users.add(userMock.getMemberType());
        users.add(String.valueOf(userMock.getExclReward()));
        users.add(roomType1);
        when(roomMock.getBookedRoomsByUser(userMock)).thenReturn(users);

        booking.cancelBooking(userMock);
        
        verify(printerMock).printInfo(userMock.getName(), userMock.getMemberType(), "Booking cancelled");
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters(method="getParamTestCharge6")
    public void illegalTestCancelBookingWithNoBookingsOrWaitingList(String name, String memberType, boolean exclReward,String roomType1,String roomType2) {

        when(userMock.getName()).thenReturn(name);
        when(userMock.getMemberType()).thenReturn(memberType);
        when(userMock.getExclReward()).thenReturn(exclReward);
        when(roomMock.getBookedRoomsByUser(userMock)).thenReturn(new ArrayList<>());

        booking.cancelBooking(userMock);
    }
}

