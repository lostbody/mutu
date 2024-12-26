package gr.aueb.cf.mutu.endpoints;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.dao.IPictureDao;
import gr.aueb.cf.mutu.dto.PictureDto;
import gr.aueb.cf.mutu.dto.UserDto;
import gr.aueb.cf.mutu.service.PictureService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Base64;

@WebServlet("/pictures")
public class Pictures extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDto loggedUser = Authentication.getSessionUser(request);
        if (loggedUser == null) {
            response.setStatus(401);
            return;
        }

        String filename = request.getParameter("filename");
        String imageData = request.getParameter("imageData");

        PictureDto pictureCreated = PictureService.getImpl().createPicture(filename, imageData.getBytes(), 0, loggedUser.getId());

        response.setContentType("application/json");
        response.getWriter().write(pictureCreated.toJson());
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDto loggedUser = Authentication.getSessionUser(request);
        if (loggedUser == null) {
            response.setStatus(401);
            return;
        }

        String idStr = request.getParameter("id");
        String directionStr = request.getParameter("direction");

        long id;
        try {
            id = Long.parseLong(idStr);
        }
        catch (NumberFormatException e) {
            response.setStatus(400);
            return;
        }

        IPictureDao.Direction direction = directionStr.equals("left") ? IPictureDao.Direction.LEFT : IPictureDao.Direction.RIGHT;

        PictureService.getImpl().reorderPictures(loggedUser.getId(), id, direction);
    }



    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        UserDto loggedUser = Authentication.getSessionUser(request);
        if (loggedUser == null) {
            response.setStatus(401);
            return;
        }

        String idStr = request.getParameter("id");

        long id = -1;
        try {
            id = Long.parseLong(idStr);
        }
        catch (NumberFormatException e) {
            response.setStatus(400);
            return;
        }

        PictureService.getImpl().deletePicture(id);
    }
}
