package gr.aueb.cf.mutu.models_prod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Picture {

    public static List<Picture> pictures = new ArrayList<>();
    static {
        pictures.add(new Picture(1, "pic1", 100, 100, "static/img/gina-profile-pic.jpg",  1));
        pictures.add(new Picture(2, "pic1", 100, 100, "static/img/rodia-profile-pic.jpg",  2));
        pictures.add(new Picture(3, "pic1", 100, 100, "blobpic",  2));
        pictures.add(new Picture(4, "pic1", 100, 100, "static/img/dora-profile-pic.jpg",  3));
        pictures.add(new Picture(5, "pic1", 100, 100, "static/img/dora-profile-pic2.jpg",  3));
        pictures.add(new Picture(6, "pic1", 100, 100, "static/img/dora-profile-pic3.jpg",  3));
        pictures.add(new Picture(7, "pic1", 100, 100, "static/img/andreas-profile-pic.jpg", 5));
    }

    public static List<Picture> getPicturesByUserId(int userId) {
        return pictures
                .stream()
                .filter(p -> p.userId == userId)
                .collect(Collectors.toList());
    }

    public static String getAvatarByUserId(int userId) {
        return pictures
                .stream()
                .filter(p -> p.userId == userId)
                .map(x -> x.blob)
                .findFirst()
                .orElse("static/img/no-profile-pic.jpg");
    }

    private int id;
    private String filename;
    private int width;
    private int height;
    private String blob;
    private int userId;

    public Picture(int id, String filename, int width, int height, String blob, int userId) {
        this.id = id;
        this.filename = filename;
        this.width = width;
        this.height = height;
        this.blob = blob;
        this.userId = userId;
    }

    public static List<Picture> getPictures() {
        return pictures;
    }

    public static void setPictures(List<Picture> pictures) {
        Picture.pictures = pictures;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getBlob() {
        return blob;
    }

    public void setBlob(String blob) {
        this.blob = blob;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
