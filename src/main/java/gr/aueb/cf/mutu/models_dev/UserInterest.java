package gr.aueb.cf.mutu.models_dev;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserInterest {

    public static List<UserInterest> userInterests = new ArrayList<>();
    static {
        userInterests.add(new UserInterest(1, 1));
        userInterests.add(new UserInterest(1, 2));
        userInterests.add(new UserInterest(1, 3));
        userInterests.add(new UserInterest(2, 1));
        userInterests.add(new UserInterest(3, 2));
        userInterests.add(new UserInterest(3, 1));
        userInterests.add(new UserInterest(4, 1));
        userInterests.add(new UserInterest(5, 5));
    }

    public static List<UserInterest> getUserInterestsByUserId(long userId) {
        return userInterests
                .stream()
                .filter(x -> x.userId == userId)
                .collect(Collectors.toList());
    }
    private final long id;
    private final long userId;
    private final long interestId;

    public UserInterest(long userId, long interestId) {
        this.id = userInterests.size() + 1;
        this.userId = userId;
        this.interestId = interestId;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getInterestId() {
        return interestId;
    }
}
