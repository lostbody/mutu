package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.models_dev.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserAction {
    public enum Action {
        SWIPE_LEFT,
        SWIPE_RIGHT
    }

    public static List<UserAction> userActions = new ArrayList<>();
    static {
        userActions.add(new UserAction(1, 2, Action.SWIPE_RIGHT, Action.SWIPE_RIGHT));
        userActions.add(new UserAction(1, 3, Action.SWIPE_RIGHT, Action.SWIPE_RIGHT));
        userActions.add(new UserAction(4, 5, Action.SWIPE_RIGHT, Action.SWIPE_RIGHT));
    }

    public static List<User> getMatchesByUserId(long userId) {
        return userActions
                .stream()
                .filter(x -> (x.user1 == userId || x.user2 == userId)
                        && x.user1_action == Action.SWIPE_RIGHT
                        && x.user2_action == Action.SWIPE_RIGHT)
                .map(x -> {
                    long matchId = x.user1 == userId ? x.user2 : x.user1;
                    return User.getById(matchId);
                })
                .collect(Collectors.toList());
    }

    public static UserAction getByUserIds(long user1Id, long user2Id) {
        return userActions
                .stream()
                .filter(x -> (x.user1 == user1Id && x.user2 == user2Id) || (x.user1 == user2Id && x.user2 == user1Id))
                .findFirst()
                .orElse(null);
    }

    private final long user1;
    private final long user2;
    private Action user1_action;
    private Action user2_action;

    public UserAction(long user1, long user2, Action user1_action, Action user2_action) {
        this.user1 = user1;
        this.user2 = user2;
        this.user1_action = user1_action;
        this.user2_action = user2_action;
    }

    public long getUser1() {
        return user1;
    }

    public long getUser2() {
        return user2;
    }

    public Action getUser1_action() {
        return user1_action;
    }

    public void setUser1_action(Action user1_action) {
        this.user1_action = user1_action;
    }

    public Action getUser2_action() {
        return user2_action;
    }

    public void setUser2_action(Action user2_action) {
        this.user2_action = user2_action;
    }
}
