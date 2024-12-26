package gr.aueb.cf.mutu.dao;

import gr.aueb.cf.mutu.dto.PictureDto;

import java.util.List;

public interface IPictureDao {
    enum Direction { LEFT, RIGHT }

    List<PictureDto> getPicturesByUserId(long userId);
    String getAvatarByUserId(long userId);


    PictureDto createPicture(String filename, byte[] imageData, int order, Long userId);

    void deletePicture(long id);

    void reorderPictures(long id, long id1, Direction direction);
}
