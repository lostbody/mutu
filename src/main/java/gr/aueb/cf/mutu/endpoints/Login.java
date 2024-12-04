package gr.aueb.cf.mutu.endpoints;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.dto.UserDto;
import gr.aueb.cf.mutu.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //παίρνουμε τα πεδία της φόρμας που έχει βάλει ο UserDto και είναι πάνω στο request μέσω του getParameter
        String email = request.getParameter("email");
        String password = request.getParameter("pass");

        //τα τυπώνουμε στην κονσόλα του server
        System.out.println("email=" + email + " password=" + password);

        //φτιάχνουμε έναν UserDto user χρησιμοποιώντας το model (κλάση UserDto) και τη μέθοδο getUserByCredentials
        //που μας επιστρέφει τον user από τη λίστα users ο οποίος έχει το username και το password
        //που πήραμε από τη φόρμα.Αν δεν βρεθεί user με το αυτό το username και password θα δημιουργθεί ένας
        //null user.

        UserDto user = UserService.getImpl().getUserByCredentials(email, password);

        //Αν βρεθεί user στη list users, αν δηλαδή "user != null", δημιουργούμε ένα string με όνομα token.
        //Δίνουμε σε αυτό το string μια random ακέραιη τιμή από το 0 έως το 999.9999
        //Ομως, το κάνει συνέχεια μέσα σε ένα loop έλεγχοντας παράλληλα μέσω του while ότι αυτό το token δεν υπάρχει ήδη
        //μέσα στο Hashmap sessions. Στo hashmap sessions αποθηκεύουμε (user-token), δηλαδή όλους τους users
        //που έχουν κάνει login μαζί με ένα token που τους δώσαμε όταν έκαναν login.
        //To loop αυτο τρέχει λοιπόν κάνοντας generate token μέχρι να βρεί ένα που να είναι
        //unique.

        if (user != null) {

            Authentication.createUserSession(user, response);

            //λέμε στον browser να κάνει redirect στη σελίδα swipe-page, αφού είναι logged in και πήρε cookie.

            response.sendRedirect("swipe-page.jsp");

            //αν δεν βρούμε το χρήστη στη λίστα users που αποθηκεύουμε τους users με τα αντιστοιχα email και password
            //τότε τυπώνουμε στην κονσόλα του σέρβερ ότι login failed και λέμε στον browser να κάνει redirect
            //στo "login.jsp?error=1"
        } else {
            System.out.println("Login failed. Redirecting to login with error.");
            response.sendRedirect("login.jsp?error=1");
        }
    }

}