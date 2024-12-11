package gr.aueb.cf.mutu.utils;

import gr.aueb.cf.mutu.models_prod.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HibernateSeed {
    public static void main(String[] args) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Create Users
            User userGina = new User();
            userGina.setEmail("gina@gina.com");
            userGina.setName("Gina");
            userGina.setBirthday(LocalDate.of(1987, 9, 1));
            userGina.setPassword("gina");
            userGina.setHeight(162);
            userGina.setWeight(58);
            userGina.setBio("Loremipsum");
            session.persist(userGina);

            User userDora = new User();
            userDora.setEmail("dora@dora.com");
            userDora.setName("Dora");
            userDora.setBirthday(LocalDate.of(1990, 8, 20));
            userDora.setPassword("dora");
            userDora.setHeight(170);
            userDora.setWeight(65);
            userDora.setBio("Loremipsum");
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

            // Create Message
            Message message = new Message();
            message.setSender(userGina);
            message.setReceiver(userAndreas);
            message.setContent("Hi, how are you?");
            message.setTimestamp(LocalDateTime.now());
            session.persist(message);

            // Create Pictures
            session.persist(new Picture("gina-profile-pic.jpg", 100, 100, "gina-profile-pic.jpg", userGina));
            session.persist(new Picture("dora-profile-pic.jpg", 100, 100, "dora-profile-pic.jpg", userDora));

            // Create Interests
            Interest bridge = new Interest("bridge");
            Interest dnd = new Interest("dnd");
            Interest gaming = new Interest("gaming");

            session.persist(bridge);
            session.persist(dnd);
            session.persist(gaming);

            // Adding Interests to Users
            userGina.getInterests().add(bridge);
            userGina.getInterests().add(dnd);

            userDora.getInterests().add(gaming);
            userDora.getInterests().add(bridge);

            session.update(userGina);
            session.update(userDora);

            System.out.println("Interests assigned.");

            // Create UserAction
            UserAction action1 = new UserAction(userGina, userDora, UserAction.Action.SWIPE_RIGHT, UserAction.Action.SWIPE_RIGHT);
            UserAction action2 = new UserAction(userDora, userGina, UserAction.Action.SWIPE_RIGHT, UserAction.Action.SWIPE_LEFT);
            session.persist(action1);
            session.persist(action2);

            System.out.println("User actions persisted successfully.");

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
