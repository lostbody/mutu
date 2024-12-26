package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IPictureDao;
import gr.aueb.cf.mutu.dto.PictureDto;
import gr.aueb.cf.mutu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public PictureDto createPicture(String filename, byte[] imageData, int order, Long userId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, userId);
            if (user == null) {
                throw new IllegalArgumentException("Invalid user");
            }

            Picture picture = new Picture(filename, imageData, order, user);
            session.persist(picture);

            transaction.commit();
            return picture.toDto();
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            throw e;
        }
    }

    @Override
    public void deletePicture(long pictureId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Picture picture = session.get(Picture.class, pictureId);
            if (picture == null) {
                throw new IllegalArgumentException("Invalid picture");
            }

            session.remove(picture);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            throw e;
        }

    }

    @Override
    public void reorderPictures(long userId, long pictureId, Direction direction) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, userId);
            if (user == null) {
                throw new IllegalArgumentException("Invalid user");
            }

            List<Picture> pictures = user.getPictures();

            int pictureIndex = IntStream.range(0, pictures.size())
                    .filter(i -> pictures.get(i).getId() == pictureId)
                    .findFirst()
                    .orElse(-1);
            if (pictureIndex == -1) {
                throw new IllegalArgumentException("Invalid picture");
            }

            int otherIndex = direction == Direction.LEFT ? pictureIndex - 1 : pictureIndex + 1;
            if (otherIndex != -1 && otherIndex != pictures.size()) {
                Collections.swap(pictures, pictureIndex, otherIndex);
                for (int i = 0; i < pictures.size(); i++) {
                    Picture picture = pictures.get(i);
                    picture.setSeq(pictures.size() - i);
                    session.persist(picture);
                }
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            throw e;
        }
    }
}
