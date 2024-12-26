package gr.aueb.cf.mutu.models_dev;

import gr.aueb.cf.mutu.dto.PictureDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Picture {

    public static List<Picture> pictures = new ArrayList<>();

    public static List<Picture> getPicturesByUserId(long userId) {
        return pictures
                .stream()
                .filter(p -> p.userId == userId)
                .collect(Collectors.toList());
    }

    public static String getAvatarByUserId(long userId) {
        return pictures
                .stream()
                .filter(p -> p.userId == userId)
                .findFirst()
                .map(x -> x.blob)
                .orElse(null);
    }

    private final long id;
    private final String filename;
    private final String blob;
    private int seq;
    private final long userId;

    public Picture(String filename, String blob, int seq, long userId) {
        this.id = pictures.size() + 1;
        this.filename = filename;
        this.blob = blob;
        this.seq = seq;
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

    public int getSeq() { return seq; }

    public void setSeq(int seq) { this.seq = seq; }

    public long getUserId() {
        return userId;
    }

    PictureDto toDto() { return new PictureDto(id, filename, blob, seq, userId); }

}
