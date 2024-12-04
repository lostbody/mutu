package gr.aueb.cf.mutu.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PictureDto {
    private final long id;
    private final String filename;
    private final int width;
    private final int height;
    private final String blob;
    private final long userId;

    public PictureDto(long id, String filename, int width, int height, String blob, long userId) {
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
}
