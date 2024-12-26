package gr.aueb.cf.mutu.dto;
public class PictureDto {
    private final long id;
    private final String filename;
    private final String blob;
    private int order;
    private final long userId;

    public PictureDto(long id, String filename, String blob, int order, long userId) {
        this.id = id;
        this.filename = filename;
        this.blob = blob;
        this.order = order;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getBlob() {
        return blob;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public long getUserId() {
        return userId;
    }

    public String toJson() {
        return "{" +
                "\"id\":" + id +
                "}";
    }
}
