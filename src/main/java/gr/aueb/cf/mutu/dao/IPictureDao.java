package gr.aueb.cf.mutu.dao;

import gr.aueb.cf.mutu.dto.PictureDto;

import java.util.List;

public interface IPictureDao {
    List<PictureDto> getPicturesByUserId(long userId);
    String getAvatarByUserId(long userId);


}
