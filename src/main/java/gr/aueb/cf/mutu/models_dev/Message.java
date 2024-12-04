package gr.aueb.cf.mutu.models_dev;

import gr.aueb.cf.mutu.dto.MessageDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Message {
    public static List<Message> messages = new ArrayList<>();
    static {
        messages.add(new Message(1, 1, 2, "Hello there!", 12000));
        messages.add(new Message(2, 1, 2, "How are you?", 13000));
        messages.add(new Message(3, 2, 1, "Fine, thank you!", 14000));
        messages.add(new Message(4, 1, 2, "Are you free tonight?", 15000));
        messages.add(new Message(5, 3, 4, "Hello!", 15000));
        messages.add(new Message(6, 4, 3, "How are you?", 16000));
        messages.add(new Message(7, 5, 4, "Hi!", 16000));
        messages.add(new Message(8, 2, 4, "How are you?", 16000));
    }

    public static List<Message> getConversationByUserIds(int user1Id, int user2Id) {
        return messages
                .stream()
                .filter(x -> (x.user1 == user1Id && x.user2 == user2Id) || (x.user2 == user1Id && x.user1 == user2Id))
                .collect(Collectors.toList());
    }

    public static List<Message> getNewMessagesByUserIds(long user1Id, long user2Id, long since) {
        return messages
                .stream()
                .filter(x -> (x.user1 == user1Id && x.user2 == user2Id) || (x.user2 == user1Id && x.user1 == user2Id))
                .filter(x -> (x.timestamp > since))
                .collect(Collectors.toList());
    }

    private long id;
    private long user1;
    private long user2;
    private String content;
    private long timestamp;

    public Message(long id, long user1, long user2, String content, long timestamp) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Message(long user1, long user2, String content, long timestamp) {
        this.id = messages.size() + 1;
        this.user1 = user1;
        this.user2 = user2;
        this.content = content;
        this.timestamp = timestamp;
    }

    public long getUser1() {
        return user1;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", user1=" + user1 +
                ", user2=" + user2 +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public String toJson() {
        return "{" +
                "\"sender\":" + user1 + "," +
                "\"content\":" + "\"" + content + "\"" + "," +
                "\"timestamp\":" + timestamp +
                "}";
    }

    public MessageDto toDto() {
        return new MessageDto(id, user1, user2, content, timestamp);
    }
}
