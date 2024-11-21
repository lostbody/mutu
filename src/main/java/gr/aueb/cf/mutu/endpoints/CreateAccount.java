package gr.aueb.cf.mutu.endpoints;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.models.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static gr.aueb.cf.mutu.models.User.users;

@WebServlet("/create-account")
public class CreateAccount extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //παίρνουμε τα πεδία της φόρμας που έχει βάλει ο User και είναι πάνω στο request μέσω του getParameter
        String email = request.getParameter("email");

        //checking if the email given already exists

        boolean emailExists = users.stream().anyMatch(u -> u.getEmail().equals(email));

        //redirect with an error if the email already exists

//        if (emailExists) {
//            response.sendRedirect("create-account.jsp?error=emailExists");
//            return;
//        }

        if (emailExists) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Email already exists\"}");
            return;
        }

        //continuing with getting the rest of the parameters if emailsExists=false
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String birthdayStr = request.getParameter("birthday");

        //τα μετατρέπω στον κατάλληλο τυπο ώστε να δημιουργησω ένα instance της κλασης User κ να τα βάλω
        //
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday = LocalDate.parse(birthdayStr, formatter);  // Convert to LocalDate


        //δημιουργώ το instance και τα περναω μέσα και ξανατυπωνω στην κονσολα για να βεβαιωθω
        User user = new User(email, password, name, birthday, null, null, null);

        users.add(user);

        Authentication.createUserSession(user, response);

        // Return a success response
        response.setStatus(HttpServletResponse.SC_OK); // 200 OK
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"Account created successfully\", \"redirect\": \"swipe-page.jsp\"}");

    }
}
