package gr.aueb.cf.mutu.endpoints;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.dto.UserDto;
import gr.aueb.cf.mutu.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/create-account")
public class CreateAccount extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String email = request.getParameter("email");

        // Check if the email already exists
        UserDto existingUser = UserService.getImpl().getByEmail(email);
        if (existingUser != null) {
            // Redirect to the form with an error message
            response.sendRedirect("create-account.jsp?error=emailExists");
            return;
        }

        // Get the rest of the parameters
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String birthdayStr = request.getParameter("birthday");

        // Convert the birthday string to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday = LocalDate.parse(birthdayStr, formatter);

        // Create the user
        UserDto user = UserService.getImpl().createUser(email, password, name, birthday, null, null, null);

        // Authenticate and start session
        Authentication.createUserSession(user, response);

        // Redirect to swipe-page.jsp
        response.sendRedirect("swipe-page.jsp");
    }
}
