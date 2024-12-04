package gr.aueb.cf.mutu.models_dev;

import gr.aueb.cf.mutu.dto.UserActionDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserAction {

    public static List<UserAction> userActions = new ArrayList<>();
    static {
        userActions.add(new UserAction(1, 2, UserActionDto.Action.SWIPE_RIGHT, UserActionDto.Action.SWIPE_RIGHT));
        userActions.add(new UserAction(1, 3, UserActionDto.Action.SWIPE_RIGHT, UserActionDto.Action.SWIPE_RIGHT));
        userActions.add(new UserAction(4, 5, UserActionDto.Action.SWIPE_RIGHT, UserActionDto.Action.SWIPE_RIGHT));
    }

    public static List<User> getMatchesByUserId(long userId) {
        return userActions
                .stream()
                .filter(x -> (x.user1 == userId || x.user2 == userId)
                        && x.user1_action == UserActionDto.Action.SWIPE_RIGHT
                        && x.user2_action == UserActionDto.Action.SWIPE_RIGHT)
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
    private UserActionDto.Action user1_action;
    private UserActionDto.Action user2_action;

    public UserAction(long user1, long user2, UserActionDto.Action user1_action, UserActionDto.Action user2_action) {
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

    public UserActionDto.Action getUser1_action() {
        return user1_action;
    }

    public void setUser1_action(UserActionDto.Action user1_action) {
        this.user1_action = user1_action;
    }

    public UserActionDto.Action getUser2_action() {
        return user2_action;
    }

    public void setUser2_action(UserActionDto.Action user2_action) {
        this.user2_action = user2_action;
    }

    public UserActionDto toDto() { return new UserActionDto(user1, user2, user1_action, user2_action); }
}
