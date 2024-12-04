package gr.aueb.cf.mutu.dao;

import gr.aueb.cf.mutu.dto.MessageDto;

import java.util.List;

public interface IMessageDao {

    List<MessageDto> getNewMessagesByUserIds (long userId, long matchId, long since);
    MessageDto createMessage (long userId, long matchId, String content, long timestamp);

}
