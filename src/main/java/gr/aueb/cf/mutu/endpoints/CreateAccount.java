package gr.aueb.cf.mutu.endpoints;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.dto.UserDto;
import gr.aueb.cf.mutu.service.UserInterestService;
import gr.aueb.cf.mutu.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        String heightStr = request.getParameter("height");
        String weightStr = request.getParameter("weight");
        String bio = request.getParameter("bio");
        String interestsStr = request.getParameter("interests");

        Integer weight = null;
        Integer height = null;

        if (heightStr != null) {
            try {
                height = Integer.parseInt(heightStr);
            }
            catch (NumberFormatException e) {
                response.setStatus(400);
                return;
            }
        }


        if (weightStr != null) {
            try {
                weight = Integer.parseInt(weightStr);
            }
            catch (NumberFormatException e) {
                response.setStatus(400);
                return;
            }
        }

        // Convert the birthday string to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday = LocalDate.parse(birthdayStr, formatter);

        Set<Long> interestIds = new HashSet<>();
        if (interestsStr != null && !interestsStr.isEmpty()) {
            try {
                interestIds = Arrays.stream(interestsStr.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toSet());
            } catch (NumberFormatException e) {
                response.setStatus(400);
                return;
            }
        }

        // Create the user
        UserDto user = UserService.getImpl().createUser(email, Authentication.hashPassword(password), name, birthday, height, weight, bio);
        UserInterestService.getImpl().setUserInterests(user.getId(), interestIds);
        // Authenticate and start session
        Authentication.createUserSession(user, response);

        // Redirect to swipe-page.jsp
        response.sendRedirect("swipe-page.jsp");
    }
}
