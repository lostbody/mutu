package gr.aueb.cf.mutu.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static gr.aueb.cf.mutu.models.Interest.interests;

public class Message {
    public static List<Message> messages = new ArrayList<>();
    static {
        messages.add(new Message(1, 1, 2, "Hello there!", 12000));
        messages.add(new Message(2, 1, 2, "How are you?", 13000));
        messages.add(new Message(3, 2, 1, "Fine, thank you!", 14000));
        messages.add(new Message(4, 1, 2, "Are you free tonight?", 15000));
    }

    public static List<Message> getConversationByUserIds(int user1Id, int user2Id) {
        return messages
                .stream()
                .filter(x -> (x.user1 == user1Id && x.user2 == user2Id) || (x.user2 == user1Id && x.user1 == user2Id))
                .collect(Collectors.toList());
    }

    private int id;
    private int user1;
    private int user2;
    private String content;
    private long timestamp;

    public Message(int id, int user1, int user2, String content, long timestamp) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getUser1() {
        return user1;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
