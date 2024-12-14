package gr.aueb.cf.mutu.models_dev;

import gr.aueb.cf.mutu.dto.PictureDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Picture {

    public static List<Picture> pictures = new ArrayList<>();
    static {
        pictures.add(new Picture(1, "pic1", 100, 100, "static/img/gina-profile-pic.jpg",  1));

        pictures.add(new Picture(2, "pic1", 100, 100, "static/img/rodia-profile-pic.jpg",  2));
        pictures.add(new Picture(3, "pic2", 100, 100, "static/img/rodia-pic2.jpg",  2));
        pictures.add(new Picture(4, "pic3", 100, 100, "static/img/rodia-pic3.jpg",  2));
        pictures.add(new Picture(5, "pic4", 100, 100, "static/img/rodia-pic4.jpg",  2));


        pictures.add(new Picture(6, "pic1", 100, 100, "static/img/dora-profile-pic.jpg",  3));
        pictures.add(new Picture(7, "pic2", 100, 100, "static/img/dora-pic2.jpg",  3));
        pictures.add(new Picture(8, "pic3", 100, 100, "static/img/dora-pic3.jpg",  3));
        pictures.add(new Picture(9, "pic4", 100, 100, "static/img/dora-pic4.jpg",  3));

        pictures.add(new Picture(10, "pic1", 100, 100, "static/img/andreas-profile-pic.jpg", 5));
        pictures.add(new Picture(11, "pic2", 100, 100, "static/img/andreas-pic2.jpg", 5));
        pictures.add(new Picture(12, "pic3", 100, 100, "static/img/andreas-pic3.jpg", 5));
        pictures.add(new Picture(13, "pic4", 100, 100, "static/img/andreas-pic4.jpg", 5));
        pictures.add(new Picture(14, "pic5", 100, 100, "static/img/andreas-pic5.jpg", 5));
    }

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
    private final int width;
    private final int height;
    private final String blob;
    private final long userId;

    public Picture(long id, String filename, int width, int height, String blob, long userId) {
        this.id = id;
        this.filename = filename;
        this.width = width;
        this.height = height;
        this.blob = blob;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getBlob() {
        return blob;
    }

    public long getUserId() {
        return userId;
    }

    PictureDto toDto() { return new PictureDto(id, filename, width, height, blob, userId); }

}
