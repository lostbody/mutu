package gr.aueb.cf.mutu;

import gr.aueb.cf.mutu.models.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.security.SecureRandom;
import java.util.HashMap;

public class Authentication {

    static SecureRandom rand = new SecureRandom();

    public static HashMap<String, User> sessions = new HashMap<>();

    //Στo hashmap sessions αποθηκεύουμε (user-token), δηλαδή όλους τους users
    //που έχουν κάνει login μαζί με ένα token που τους δώσαμε όταν έκαναν login.

    public static User getSessionUser(HttpServletRequest request) {
        User loggedUser = null;

        //παίρνει τα cookies (header) του request που έρχεται από τον browser και τα βάζει
        //σε ένα table Cookies. Μετά διατρέχει το table Cookies για να βρει αν υπάρχει κάποιο cookie
        //με name="loginToken". Αν το βρει τότε παίρνει το value του και το αποθηκεύει σε ένα
        //string που το ονομάζει token.

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (!cookie.getName().equals("loginToken")) { continue; }
                String token = cookie.getValue();

                loggedUser = Authentication.sessions.get(token);
                break;
            }
        }

        if (loggedUser == null) {
            System.out.println("User not found in sessions.");
        } else {
            System.out.println("Found user: " + loggedUser.getEmail());
        }

        return loggedUser;
    }

    public static void createUserSession(User user, HttpServletResponse response) {
        String token;
        do {
            token = String.format("%06d", rand.nextInt(1_000_000));
        } while (Authentication.sessions.containsKey(token));

        //όταν δημιουργηθεί ένα unique random token το αποθηκεύουμε στο hashmap session
        //μαζί με τον user.

        Authentication.sessions.put(token, user);

        //δημιουργούμε ένα Cookie cookie με name = "loginToken" και value = token (το random token που φτιάξαμε
        //απο πάνω). Κάνουμε αυτο το cookie "HttpOnly" ώστε μόνο o browser να μπορεί να το διαβάσει για λόγους
        //ασφάλειας. Του δίνουμε expiration μετά από 3 ημέρες.
        //Τέλος, κάνουμε add αυτό το cookie στο response που θα επιστρέψουμε στον browser.

        Cookie cookie = new Cookie("loginToken", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3 * 24 * 60 * 60); // 3 days
        response.addCookie(cookie);
    }

}
