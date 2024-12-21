package gr.aueb.cf.mutu.dao;

import gr.aueb.cf.mutu.dto.PictureDto;

import java.util.List;

public interface IPictureDao {
    List<PictureDto> getPicturesByUserId(long userId);
    String getAvatarByUserId(long userId);


    PictureDto createPicture(String filename, int width, int height, byte[] imageData, Long userId);
}
