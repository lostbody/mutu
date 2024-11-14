package gr.aueb.cf.mutu.endpoints;

import gr.aueb.cf.mutu.Authentication;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/logout")
public class Logout extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (!cookie.getName().equals("loginToken")) {
                    continue;
                }
                token = cookie.getValue();
                break;
            }
        }

        Authentication.sessions.remove(token);

        response.sendRedirect("login.jsp");
    }

}
