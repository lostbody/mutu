package gr.aueb.cf.mutu.dto;

public class UserActionDto {
    public enum Action {
        SWIPE_LEFT,
        SWIPE_RIGHT
    }

    private final long user1;
    private final long user2;
    private Action user1_action;
    private Action user2_action;

    public UserActionDto(long user1, long user2, Action user1_action, Action user2_action) {
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
