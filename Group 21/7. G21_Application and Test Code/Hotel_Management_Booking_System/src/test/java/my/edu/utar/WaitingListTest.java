package my.edu.utar;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import java.io.*;
import java.util.*;

@RunWith(JUnitParamsRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WaitingListTest {
    
	WaitingList wl = new WaitingList();
    private static final String TEST_FILE_PATH = "waitingListUsers.txt";
    
    @BeforeClass
    public static void setUpBeforeClass() throws IOException {
        // Clear the test file before any test methods are executed
        clearTestFile(TEST_FILE_PATH);
    }
    
    private static void clearTestFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.print("");
        }
    }

    @Before
    public void setUp() throws IOException {
        loadUsersIntoWaitingList(TEST_FILE_PATH);
    }

    private void loadUsersIntoWaitingList(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String listType = parts[1].trim();
                    wl.addWaiting(new User(name, listType, false), listType);
                }
            }
        }
    }

    private void writeUsersToFile(List<String> users, String listType) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH,true))) {
            for (String user : users) {
                writer.write(user + "," + listType);
                writer.newLine();
            }
        }
    }
    
    private List<String> removeUserFromFile(String user, String listType) throws IOException {
        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (!(parts[0].trim().equals(user) && parts[1].trim().equals(listType))) {
                    updatedLines.add(line);
                }
            }
        }
        return updatedLines;
    }

    private Object[] getParamTestCharge0() {
		return new Object[] {
                new Object[]{"Erica","David","VIP"},
                new Object[]{"Jonathon","Bob","Member"},
                new Object[]{"KeeMX","Chris","Non-member"}
		};
	}

    @Test
    @Parameters(method = "getParamTestCharge0")
    public void testAddWaiting(String user1, String user2, String listType) throws IOException {
        // Add users to the waiting list
        wl.addWaiting(new User(user1, listType, false), listType);
        wl.addWaiting(new User(user2, listType, false), listType);

        // Create the expected list
        List<String> expectedList = new ArrayList<>(Arrays.asList(user1, user2));

        // Write the entire list to file
        writeUsersToFile(expectedList, listType);

        // Check if users are added correctly
        assertEquals(listType + " list should have correct users", expectedList, wl.getWaitingList(listType));
    }

    @Test
    @Parameters(method = "getParamTestCharge0")
    public void testGetWaitingList(String user1, String user2, String listType) throws IOException {
        // Check if the correct list is retrieved
        List<String> expectedList = new ArrayList<>(Arrays.asList(user1, user2));

        assertEquals(listType + " list should have correct users", expectedList, wl.getWaitingList(listType));
    }
    
    @Test
    @Parameters({
        "Erica, VIP",
        "Jonathon, Member",
        "KeeMX, Non-member"
    })
    public void testRemoveWaiting(String user, String listType) throws IOException {
        // Remove the specified user from the waiting list
        wl.removeWaiting(new User(user, listType, false), listType);

        // Read the lines from the file and remove the line containing the removed user
        List<String> lines = removeUserFromFile(user, listType);

        // Write the updated lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
        
        // Create the expected list using the updated lines
        List<String> expectedList = createExpectedList(lines, listType);

        // Check if the user is removed correctly
        assertEquals(listType + " list should not contain removed user", expectedList, wl.getWaitingList(listType));
    }
    
    private List<String> createExpectedList(List<String> lines, String listType) {
        List<String> expectedList = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[1].trim().equals(listType)) {
                expectedList.add(parts[0].trim());
            }
        }
        return expectedList;
    }  

    //Not in WaitingList
    @Test(expected = IllegalArgumentException.class)
    @Parameters({
        "Erica, VIP",
        "Jonathon, Member",
        "KeeMX, Non-member"
    })
    public void illegalTestRemoveWaiting(String user, String listType) {
        // Remove the specified user from the waiting list
        wl.removeWaiting(new User(user, listType, false), listType);
    }
}