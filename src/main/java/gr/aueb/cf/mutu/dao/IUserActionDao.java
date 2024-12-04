package gr.aueb.cf.mutu.dao;

import gr.aueb.cf.mutu.dto.UserActionDto;
import gr.aueb.cf.mutu.dto.UserDto;

import java.util.List;

public interface IUserActionDao {
    UserActionDto getByUserIds(long user1Id, long user2Id);

    UserActionDto createUserAction(long user1Id, long user2Id,
                                   UserActionDto.Action user1Action,
                                   UserActionDto.Action user2Action);

    void updateUserAction(UserActionDto userAction);

    List<UserDto> getMatchesByUserId(long userId);
}
