package gr.aueb.cf.mutu.models_dev;

import gr.aueb.cf.mutu.dao.IMessageDao;
import gr.aueb.cf.mutu.dto.MessageDto;

import java.util.List;
import java.util.stream.Collectors;

public class MessageDao implements IMessageDao {

    @Override
    public List<MessageDto> getNewMessagesByUserIds(long userId, long matchId, long since) {
        return Message.getNewMessagesByUserIds(userId, matchId, since).stream()
                .map(Message::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MessageDto createMessage(long userId, long matchId, String content, long timestamp) {
        Message messageSent = new Message(userId, matchId, content, timestamp);
        Message.messages.add(messageSent);
        return messageSent.toDto();
    }

}
