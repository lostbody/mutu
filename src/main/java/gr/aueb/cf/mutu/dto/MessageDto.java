package gr.aueb.cf.mutu.dto;

public class MessageDto {

    private final long id;
    private final long user1;
    private final long user2;
    private final String content;
    private final long timestamp;

    public MessageDto(long id, long user1, long user2, String content, long timestamp) {
        this.id = id;
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
}
