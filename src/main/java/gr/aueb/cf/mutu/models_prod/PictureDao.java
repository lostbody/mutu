package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IPictureDao;
import gr.aueb.cf.mutu.dto.PictureDto;
import gr.aueb.cf.mutu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
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
                    .map(x -> new String(x.getImageData(), StandardCharsets.UTF_8))
                    .orElse(null);
        }
    }

    @Override
    public PictureDto createPicture(String filename, int width, int height, byte[] imageData, Long userId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, userId);
            if (user == null) {
                throw new IllegalArgumentException("Invalid user");
            }

            Picture picture = new Picture(filename, width, height, imageData, user);
            session.persist(picture);

            transaction.commit();
            return picture.toDto();
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            throw e;
        }
    }
}
