package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IUserActionDao;
import gr.aueb.cf.mutu.dto.UserActionDto;
import gr.aueb.cf.mutu.dto.UserDto;

import java.util.List;

public class UserActionDao implements IUserActionDao {
    @Override
    public UserActionDto getByUserIds(long user1Id, long user2Id) {
        return null;
    }

    @Override
    public UserActionDto createUserAction(long user1Id, long user2Id, UserActionDto.Action user1Action, UserActionDto.Action user2Action) {
        return null;
    }

    @Override
    public void updateUserAction(UserActionDto userAction) {

    }

    @Override
    public List<UserDto> getMatchesByUserId(long userId) {
        return null;
    }
}
