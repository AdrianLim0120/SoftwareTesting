package my.edu.utar;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import java.util.*;

@RunWith(JUnitParamsRunner.class)
public class UserTest {

	User user = new User();
	
	@Before
	public void setUp() {
	    user = new User("DefaultName", "Non-member", false); // Example initialization
	}

	@Test
    public void testGetName() {
        User user1 = new User("John","VIP",true);
        assertEquals("Name should be " + "John", "John", user1.getName());
    }

    @Test
    public void testGetMemberType() {
    	User user2 = new User("John","VIP",true);
        assertEquals("Member type should be " + "VIP", "VIP", user2.getMemberType());
    }

    @Test
    public void testGetExclReward() {
    	 User user3 = new User("John","VIP",true);
        assertEquals("Exclusive reward should be " + true, true, user3.getExclReward());
    }

    // Parameterized tests for setters	
    private Object[] getParamTestCharge() {
		return new Object[] {
				new Object[]{"John","VIP",true},
                new Object[]{"Ryan","Member",true},
                new Object[]{"KeeMX","Non-member",false},
		};
	}
	
    @Test
    @Parameters(method="getParamTestCharge")
    public void testSetName(String name, String memberType, boolean exclReward) {
        user.setName(name);
        assertEquals("Name should be " + name, name, user.getName());
    }

    @Test
    @Parameters(method="getParamTestCharge")
    public void testSetMemberType(String name, String memberType, boolean exclReward) {
        user.setMemberType(memberType);
        assertEquals("Member type should be " + memberType, memberType, user.getMemberType());
    }

    @Test
    @Parameters(method="getParamTestCharge")
    public void testSetExclReward(String name, String memberType, boolean exclReward) {
        user.setExclReward(exclReward);
        assertEquals("Exclusive reward should be " + exclReward, exclReward, user.getExclReward());
    }

    // Illegal testing

    private Object[] getParamTestCharge1() {
		return new Object[] {
				new Object[]{null,"VIP",true},
		        new Object[]{"","VIP",false},
		        new Object[]{123,"VIP",true}, 
		        new Object[]{123.00,"VIP",false},
		};
	}
    
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method="getParamTestCharge1")
    public void illegalTestSetName(String name, String memberType, boolean exclReward) {
        user.setName(name);
    }
	
	private Object[] getParamTestCharge2() {
		return new Object[] {
		        new Object[]{"John",null,true},
		        new Object[]{"John","",false},
		        new Object[]{"John",123,true}, 
		        new Object[]{"John",123.00,false},
		        new Object[]{"John","Ehllo",false},
		};
	}

    @Test(expected = IllegalArgumentException.class)
    @Parameters(method="getParamTestCharge2")
    public void illegalTestSetMemberType(String name, String memberType, boolean exclReward) {
        user.setMemberType(memberType);
    }
    
    private Object[] getParamTestCharge3() {
		return new Object[] {
		        new Object[]{"John","VIP",null},
		        new Object[]{"John","VIP",1233},
		        new Object[]{"John","VIP",123.02},
		};
	}
    
    @Test(expected = IllegalArgumentException.class)
    @Parameters(method="getParamTestCharge3")
    public void illegalTestSetExclReward(String name, String memberType, boolean exclReward) {
        user.setExclReward(exclReward);
    }
}