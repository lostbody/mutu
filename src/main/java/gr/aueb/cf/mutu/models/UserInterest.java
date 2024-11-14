package gr.aueb.cf.mutu.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserInterest {

    public static List<UserInterest> userInterests = new ArrayList<>();
    static {
        userInterests.add(new UserInterest(1, 1, 1));
        userInterests.add(new UserInterest(2, 1, 2));
        userInterests.add(new UserInterest(3, 1, 3));
        userInterests.add(new UserInterest(4, 2, 1));
        userInterests.add(new UserInterest(5, 2, 2));
    }

    public static List<UserInterest> getUserInterestsByUserId(int userId) {
        return userInterests
                .stream()
                .filter(x -> x.userId == userId)
                .collect(Collectors.toList());
    }
    private int id;
    private int userId;
    private int interestId;

    public UserInterest(int id, int userId, int interestId) {
        this.id = id;
        this.userId = userId;
        this.interestId = interestId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getInterestId() {
        return interestId;
    }

    public void setInterestId(int interestId) {
        this.interestId = interestId;
    }
}
