package gr.aueb.cf.mutu.utils;

import gr.aueb.cf.mutu.dto.UserActionDto;
import gr.aueb.cf.mutu.models_prod.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HibernateSeed {
    public static void main(String[] args) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Create Interests
            Interest bridge = new Interest("bridge");
            Interest dnd = new Interest("dnd");
            Interest music = new Interest("music");
            Interest politics = new Interest("politics");
            Interest basketball = new Interest("basketball");
            Interest tennis = new Interest("tennis");
            Interest travel = new Interest("travel");
            Interest boardgames = new Interest("boardgames");
            Interest reading = new Interest("reading");
            Interest football = new Interest("football");

            session.persist(bridge);
            session.persist(dnd);
            session.persist(music);
            session.persist(politics);
            session.persist(basketball);
            session.persist(tennis);
            session.persist(travel);
            session.persist(boardgames);
            session.persist(reading);
            session.persist(football);

            // Create Users
            User userGina = new User();
            userGina.setEmail("gina@gina.com");
            userGina.setName("Gina");
            userGina.setBirthday(LocalDate.of(1987, 9, 1));
            userGina.setPassword("gina");
            userGina.setHeight(162);
            userGina.setWeight(58);
            userGina.setBio("Loremipsum");
            userGina.setInterests(Stream.of(bridge, dnd).collect(Collectors.toSet()));
            session.persist(userGina);

            User userDora = new User();
            userDora.setEmail("dora@dora.com");
            userDora.setName("Dora");
            userDora.setBirthday(LocalDate.of(1990, 8, 20));
            userDora.setPassword("dora");
            userDora.setHeight(170);
            userDora.setWeight(65);
            userDora.setBio("Loremipsum");
            userGina.setInterests(Stream.of(boardgames, dnd).collect(Collectors.toSet()));
            session.persist(userDora);

            User userRodia = new User();
            userRodia.setEmail("rodia@rodia.com");
            userRodia.setName("Rodia");
            userRodia.setBirthday(LocalDate.of(1990, 8, 20));
            userRodia.setPassword("rodia");
            userRodia.setHeight(170);
            userRodia.setWeight(65);
            userRodia.setBio("Loremipsum");
            session.persist(userRodia);

            User userAndreas = new User();
            userAndreas.setEmail("andreas@andreas.com");
            userAndreas.setName("Andreas");
            userAndreas.setBirthday(LocalDate.of(1990, 8, 20));
            userAndreas.setPassword("andreas");
            userAndreas.setHeight(170);
            userAndreas.setWeight(65);
            userAndreas.setBio("Loremipsum");
            session.persist(userAndreas);

            User userKostis = new User();
            userKostis.setEmail("kostis@kostis.com");
            userKostis.setName("Kostis");
            userKostis.setBirthday(LocalDate.of(1988, 8, 20));
            userKostis.setPassword("kostis");
            userKostis.setHeight(185);
            userKostis.setWeight(96);
            userKostis.setBio("Loremipsum");
            session.persist(userKostis);

            // Create Pictures
            session.persist(new Picture("pic1", 100, 100, "static/img/gina-profile-pic.jpg",  userGina));

            session.persist(new Picture("pic1", 100, 100, "static/img/rodia-profile-pic.jpg",  userRodia));
            session.persist(new Picture("pic2", 100, 100, "static/img/rodia-pic2.jpg",  userRodia));
            session.persist(new Picture("pic3", 100, 100, "static/img/rodia-pic3.jpg",  userRodia));
            session.persist(new Picture("pic4", 100, 100, "static/img/rodia-pic4.jpg",  userRodia));

            session.persist(new Picture("pic1", 100, 100, "static/img/dora-profile-pic.jpg",  userDora));
            session.persist(new Picture("pic2", 100, 100, "static/img/dora-pic2.jpg",  userDora));
            session.persist(new Picture("pic3", 100, 100, "static/img/dora-pic3.jpg",  userDora));
            session.persist(new Picture("pic4", 100, 100, "static/img/dora-pic4.jpg",  userDora));

            session.persist(new Picture("pic1", 100, 100, "static/img/andreas-profile-pic.jpg", userAndreas));
            session.persist(new Picture("pic2", 100, 100, "static/img/andreas-pic2.jpg", userAndreas));
            session.persist(new Picture("pic3", 100, 100, "static/img/andreas-pic3.jpg", userAndreas));
            session.persist(new Picture("pic4", 100, 100, "static/img/andreas-pic4.jpg", userAndreas));
            session.persist(new Picture("pic5", 100, 100, "static/img/andreas-pic5.jpg", userAndreas));

            // Create UserActions
            session.persist(new UserAction(userGina, userDora, UserActionDto.Action.SWIPE_RIGHT, UserActionDto.Action.SWIPE_RIGHT));
            session.persist(new UserAction(userGina, userAndreas, UserActionDto.Action.SWIPE_RIGHT, UserActionDto.Action.SWIPE_RIGHT));
            session.persist(new UserAction(userAndreas, userDora, UserActionDto.Action.SWIPE_RIGHT, UserActionDto.Action.SWIPE_RIGHT));
            session.persist(new UserAction(userAndreas, userRodia, UserActionDto.Action.SWIPE_RIGHT, UserActionDto.Action.SWIPE_RIGHT));
            session.persist(new UserAction(userAndreas, userKostis, UserActionDto.Action.SWIPE_RIGHT, UserActionDto.Action.SWIPE_RIGHT));

            // Create Messages
            session.persist(new Message(userGina, userAndreas, "Hi, how are you?", Timestamp.from(Instant.now())));

            session.persist(new Message(userGina, userDora, "Hello there!", Timestamp.from(Instant.now())));
            session.persist(new Message(userGina, userDora, "How are you?", Timestamp.from(Instant.now())));
            session.persist(new Message(userDora, userGina, "Fine, thank you!", Timestamp.from(Instant.now())));
            session.persist(new Message(userGina, userDora, "Are you free tonight?", Timestamp.from(Instant.now())));
            session.persist(new Message(userRodia, userAndreas, "Hello!", Timestamp.from(Instant.now())));
            session.persist(new Message(userAndreas, userRodia, "How are you?", Timestamp.from(Instant.now())));
            session.persist(new Message(userKostis, userAndreas, "Hi!", Timestamp.from(Instant.now())));
            session.persist(new Message(userDora, userAndreas, "How are you?", Timestamp.from(Instant.now())));

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        HibernateUtil.shutdown();
    }
}
