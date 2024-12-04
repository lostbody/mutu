package gr.aueb.cf.mutu.models_dev;

import gr.aueb.cf.mutu.dao.IUserActionDao;
import gr.aueb.cf.mutu.dto.UserActionDto;
import gr.aueb.cf.mutu.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserActionDao implements IUserActionDao {
    @Override
    public UserActionDto getByUserIds(long user1Id, long user2Id) {
        UserAction userAction = UserAction.getByUserIds(user1Id, user2Id);
        return userAction == null ? null : userAction.toDto();
    }

    @Override
    public UserActionDto createUserAction(long user1Id, long user2Id, UserActionDto.Action user1Action, UserActionDto.Action user2Action) {
        UserAction userAction = new UserAction(user1Id, user2Id, user1Action, user2Action);
        UserAction.userActions.add(userAction);
        return userAction.toDto();
    }

    @Override
    public void updateUserAction(UserActionDto userActionDto) {
        UserAction userAction = UserAction.getByUserIds(userActionDto.getUser1(), userActionDto.getUser2());
        userAction.setUser1_action(userActionDto.getUser1_action());
        userAction.setUser2_action(userActionDto.getUser2_action());
    }

    @Override
    public List<UserDto> getMatchesByUserId(long userId) {
        return UserAction.getMatchesByUserId(userId).stream()
                .map(User::toDto)
                .collect(Collectors.toList());
    }
}
