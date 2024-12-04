package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IPictureDao;
import gr.aueb.cf.mutu.dto.PictureDto;

import java.util.List;

public class PictureDao implements IPictureDao {
    @Override
    public List<PictureDto> getPicturesByUserId(long userId) {
        return null;
    }

    @Override
    public String getAvatarByUserId(long userId) {
        return null;
    }
}
