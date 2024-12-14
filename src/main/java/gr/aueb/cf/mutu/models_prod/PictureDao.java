package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IPictureDao;
import gr.aueb.cf.mutu.dto.PictureDto;
import gr.aueb.cf.mutu.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PictureDao implements IPictureDao {
    @Override
    public List<PictureDto> getPicturesByUserId(long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            return new ArrayList<>(user.getPictures()).stream()
                    .map(Picture::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public String getAvatarByUserId(long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            return user.getPictures().stream()
                    .findFirst()
                    .map(Picture::getImageData)
                    .orElse(null);
        }
    }
}
