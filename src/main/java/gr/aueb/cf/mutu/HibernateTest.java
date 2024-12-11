package gr.aueb.cf.mutu;

import gr.aueb.cf.mutu.models_prod.*;
import gr.aueb.cf.mutu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HibernateTest {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Create Users
            User user1 = new User();
            user1.setEmail("gina@gina.com");
            user1.setName("Gina");
            user1.setBirthday(LocalDate.of(1987, 9, 1));
            user1.setPassword("gina");
            user1.setHeight(162);
            user1.setWeight(58);
            user1.setBio("Loremipsum");
            session.save(user1);
            System.out.println("User " + user1.getName() + " saved successfully.");


            User user2 = new User();
            user2.setEmail("dora@dora.com");
            user2.setName("Dora");
            user2.setBirthday(LocalDate.of(1990, 8, 20));
            user2.setPassword("dora");
            user2.setHeight(170);
            user2.setWeight(65);
            user2.setBio("Loremipsum");
            session.save(user2);

            System.out.println("User " + user2.getName() + " saved successfully.");

            // Create Message
            Message message = new Message();
            message.setSender(user1);
            message.setReceiver(user2);
            message.setContent("Hi, how are you?");
            message.setTimestamp(LocalDateTime.now());
            session.save(message);

            System.out.println("Message saved successfully.");

            // Create Pictures
            Picture picture1 = new Picture("gina-profile-pic.jpg", 100, 100, "base64data_gina", user1);
            Picture picture2 = new Picture("dora-profile-pic.jpg", 100, 100, "base64data_dora", user2);
            session.save(picture1);
            session.save(picture2);

            System.out.println("Pictures saved successfully.");

            // Create Interests
            Interest bridge = new Interest("bridge");
            Interest dnd = new Interest("dnd");
            Interest gaming = new Interest("gaming");

            session.save(bridge);
            session.save(dnd);
            session.save(gaming);

            // Adding Interests to Users
            user1.getInterests().add(bridge);
            user1.getInterests().add(dnd);

            user2.getInterests().add(gaming);
            user2.getInterests().add(bridge);

            session.update(user1);
            session.update(user2);

            System.out.println("Interests assigned.");

            // Create UserAction
            UserAction action1 = new UserAction(user1, user2, UserAction.Action.SWIPE_RIGHT, UserAction.Action.SWIPE_RIGHT);
            UserAction action2 = new UserAction(user2, user1, UserAction.Action.SWIPE_RIGHT, UserAction.Action.SWIPE_LEFT);
            session.save(action1);
            session.save(action2);

            System.out.println("User actions saved successfully.");

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            HibernateUtil.shutdown();
        }
    }
}
