package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IMessageDao;
import gr.aueb.cf.mutu.dto.MessageDto;

import java.util.List;

public class MessageDao implements IMessageDao {

    @Override
    public List<MessageDto> getNewMessagesByUserIds(long userId, long matchId, long since) {
        return null;
    }

    @Override
    public MessageDto createMessage(long userId, long matchId, String content, long timestamp) {
        return null;
    }

    @Override
    public List<MessageDto> getConversationByUserIds(long user1Id, long user2Id) {
        return null;
    }

}
