package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dto.PictureDto;
import jakarta.persistence.*;
@Entity
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private String imageData;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Picture() {}

    // Parameterized constructor
    public Picture(String filename, int width, int height, String imageData, User user) {
        this.filename = filename;
        this.width = width;
        this.height = height;
        this.imageData = imageData;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    PictureDto toDto() { return new PictureDto(id, filename, width, height, imageData, user.getId()); }
}
