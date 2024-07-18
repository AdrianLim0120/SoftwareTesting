package my.edu.utar;

import java.util.*;

public class WaitingList {
    private ArrayList<String> vipList = new ArrayList<>();
    private ArrayList<String> memberList = new ArrayList<>();
    private ArrayList<String> nonMemberList = new ArrayList<>();

    public WaitingList() {
    }

    public boolean addWaiting(User user, String listType) {
        switch (listType) {
            case "VIP":
                vipList.add(user.getName());
                return true;
            case "Member":
                memberList.add(user.getName());
                return true;
            case "Non-member":
                nonMemberList.add(user.getName());
                return true;
        }
        return false;
    }

    public boolean removeWaiting(User user, String listType) {
        switch (listType) {
            case "VIP":
                if (!vipList.contains(user.getName())) {
                    throw new IllegalArgumentException("User not found in the VIP waiting list");
                }else {
                    vipList.remove(user.getName());
                }
                return true;
            case "Member":
                if(!memberList.contains(user.getName())) {
                    throw new IllegalArgumentException("User not found in the Member waiting list");
                }else {
                    memberList.remove(user.getName());
                }
                return true;
            case "Non-member":
                if(!nonMemberList.contains(user.getName())) {
                    throw new IllegalArgumentException("User not found in the Non-member waiting list");
                }else {
                    nonMemberList.remove(user.getName());
                }
                return true;
            default:
                return false;
        }
    }

    // This method is to retrieve the waiting list, assuming it's for display or other purposes
    public ArrayList<String> getWaitingList(String listType) {
        switch (listType) {
            case "VIP":
                return vipList;
            case "Member":
                return memberList;
            case "Non-member":
                return nonMemberList;
            default:
                return null;
        }
    }
}
