package gr.aueb.cf.mutu.models_dev;

import gr.aueb.cf.mutu.dao.IUserActionDao;
import gr.aueb.cf.mutu.dao.IUserInterestDao;
import gr.aueb.cf.mutu.dto.UserActionDto;
import gr.aueb.cf.mutu.dto.UserDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserInterestDao implements IUserInterestDao {

    @Override
    public void setUserInterests(long userId, Set<Long> interestIds) {
        UserInterest.userInterests.removeIf(x -> x.getUserId() == userId);
        for (long interestId : interestIds) {
            UserInterest.userInterests.add(new UserInterest(userId, interestId));
        }
    }
}
