package gr.aueb.cf.mutu.endpoints;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.dto.UserDto;
import gr.aueb.cf.mutu.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/account-settings")
public class AccountSettings extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserDto loggedUser = Authentication.getSessionUser(request);
        if (loggedUser == null) {
            // If no user is logged in, redirect to the login page
            response.sendRedirect("login.jsp");
            return;
        }

        // Retrieve updated information from the form
        String password = request.getParameter("password");
        String heightStr = request.getParameter("height");
        String weightStr = request.getParameter("weight");
        String bio = request.getParameter("bio");

        // Convert and update user fields
        int height;
        try {
            height = Integer.parseInt(heightStr);
        }
        catch (NumberFormatException e) {
            response.setStatus(400);
            return;
        }

        int weight;

        try {
            weight = Integer.parseInt(weightStr);
        }
        catch (NumberFormatException e) {
            response.setStatus(400);
            return;
        }

        // Update the logged user's information with the values we get from the form
        // which are retrieved from the request.getParameter... (see above)
        loggedUser.setPassword(password);
        loggedUser.setHeight(height);
        loggedUser.setWeight(weight);
        loggedUser.setBio(bio);

        UserService.getImpl().updateUser(loggedUser);
    }

}


