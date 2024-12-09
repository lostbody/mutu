package gr.aueb.cf.mutu.models_prod;

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
        userInterests.add(new UserInterest(5, 3, 2));
        userInterests.add(new UserInterest(6, 3, 1));
        userInterests.add(new UserInterest(7, 4, 1));
        userInterests.add(new UserInterest(8, 5, 5));
    }

    public static List<UserInterest> getUserInterestsByUserId(long userId) {
        return userInterests
                .stream()
                .filter(x -> x.userId == userId)
                .collect(Collectors.toList());
    }
    private long id;
    private long userId;
    private long interestId;

    public UserInterest(long id, long userId, long interestId) {
        this.id = id;
        this.userId = userId;
        this.interestId = interestId;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getInterestId() {
        return interestId;
    }

    public void setInterestId(long interestId) {
        this.interestId = interestId;
    }
}
