package my.edu.utar;

public class User {
    private String name;
    private String memberType; // "VIP", "normal", or "non-member"
    private Boolean exclReward;
    
    public User() {
    }

    public User(String name, String memberType, Boolean exclReward) {
        this.name = name;
        this.memberType = memberType;
        this.exclReward = exclReward;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getMemberType() {
        return memberType;
    }

    public Boolean getExclReward() {
        return exclReward;
    }

    public void setName(String name) {
        if (name == null || name.matches(".*\\d+.*")|| name.isEmpty()) {
            throw new IllegalArgumentException("Invalid Name: " + name);
        }
        this.name = name;
    }


    public void setMemberType(String memberType) {
        if (!isValidMemberType(memberType) || memberType == null || memberType.isEmpty()|| !(memberType instanceof String)) {
            throw new IllegalArgumentException("Invalid memberType: " + memberType);
        }
        this.memberType = memberType;
    }

    public void setExclReward(Boolean exclReward) {
    	if (exclReward == null) {
            throw new IllegalArgumentException("Invalid exclReward: " + exclReward);
    	}
        this.exclReward = exclReward;
    }
    
    // Helper method to validate memberType
    private boolean isValidMemberType(String memberType) {
        return memberType != null && (memberType.equals("VIP") || memberType.equals("Member") || memberType.equals("Non-member"));
    }
}
