package gr.aueb.cf.mutu.dto;

public class UserInterestDto {
    private final long id;
    private final long userId;
    private final long interestId;

    public UserInterestDto(long id, long userId, long interestId) {
        this.id = id;
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
