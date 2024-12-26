package gr.aueb.cf.mutu.models_dev;

import gr.aueb.cf.mutu.dao.IPictureDao;
import gr.aueb.cf.mutu.dto.PictureDto;

import java.util.List;
import java.util.stream.Collectors;

public class PictureDao implements IPictureDao {
    @Override
    public List<PictureDto> getPicturesByUserId(long userId) {
        return Picture.getPicturesByUserId(userId).stream()
                .map(Picture::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public String getAvatarByUserId(long userId) {
        return Picture.getAvatarByUserId(userId);
    }

    @Override
    public PictureDto createPicture(String filename, byte[] imageData, int order, Long userId) {
        return null;
    }

    @Override
    public void deletePicture(long id) {

    }

    @Override
    public void reorderPictures(long id, long id1, Direction direction) {

    }
}
