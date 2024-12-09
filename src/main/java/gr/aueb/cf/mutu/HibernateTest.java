package gr.aueb.cf.mutu;

import gr.aueb.cf.mutu.models_prod.Message;
import gr.aueb.cf.mutu.models_prod.User;
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

            // Step 1: Create Users
            User sender = new User();
            sender.setEmail("sender@example.com");
            sender.setName("Sender User");
            sender.setBirthday(LocalDate.of(1985, 5, 15));
            sender.setPassword("securepassword");
            sender.setHeight(180);
            sender.setBio("This is the sender.");
            session.save(sender);

            User receiver = new User();
            receiver.setEmail("receiver@example.com");
            receiver.setName("Receiver User");
            receiver.setBirthday(LocalDate.of(1990, 8, 20));
            receiver.setPassword("securepassword");
            receiver.setHeight(170);
            receiver.setBio("This is the receiver.");
            session.save(receiver);

            System.out.println("Users saved successfully!");

            // Step 2: Create and Save a Message
            Message message = new Message();
            message.setSender(sender);
            message.setReceiver(receiver);
            message.setContent("Hello, how are you?");
            message.setTimestamp(LocalDateTime.now());
            session.save(message);

            System.out.println("Message saved successfully!");

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
