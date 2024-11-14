<%@ page import="gr.aueb.cf.mutu.models.User" %>
<%@ page import="gr.aueb.cf.mutu.Authentication" %>
<%@ page import="gr.aueb.cf.mutu.models.Picture" %>
<%@ page import="gr.aueb.cf.mutu.models.Interest" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
  User loggedUser = Authentication.getSessionUser(request);
  if (loggedUser == null) {
    response.sendRedirect("login.jsp");
    return;
  }

  User other = User.getUserRandom();
  LocalDate birthday = other.getBirthday();
  Date today = new Date();
  System.out.println(String.valueOf(today.getYear()) + " " + String.valueOf(birthday.getYear()));
  int age = today.getYear() - birthday.getYear();

  List<Picture> pictures = Picture.getPicturesByUserId(other.getId());
  List<Interest> interests = Interest.getInterestsByUserId(other.getId());
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>swipe-page</title>
    <style>
      .container {
        width: 400px;
        margin: 0 auto;

        display: flex;
        flex-direction: column;
      }

      .centered {
        align-self: center;
      }

      .pictures {
        display: flex;
        align-items: center;
      }

      .interests {
        display: flex;
      }

      .interests .tags {
        display: flex;
        gap: 3px;
        flex-wrap: wrap;
      }

      .interests .tag {
        border: 1px solid black;
        border-radius: 5px;
        padding: 2px;
      }
    </style>
  </head>
  <body>
    <div class="container">

      <jsp:include page="navbar.jsp" />

      <div>
        <span><%= other.getName() %>,</span>
        <span><%= age %></span>
      </div>

      <div class="pictures centered">
        <div>
          <button>
            <span>←</span>
          </button>
        </div>
        <div class="image-container">
          <img
            style="width: 200px; height: 200px"
            src="static/img/andreas-profile.jpg"
            alt="andreas-profile-pic"
            title="andreas-profile-pic"
          />
        </div>
        <div>
          <button>
            <span>→</span>
          </button>
        </div>
      </div>

      <div class="centered">
        <span>1/5</span>
      </div>

      <div class="centered">
        <form method="POST" action="swipe-left">
          <button type="submit"><span>No</span></button>
        </form>
        <form method="POST" action="swipe-right">
          <button type="submit"><span>Yes</span></button>
        </form>
      </div>

      <div>
        <p>
          <%= other.getBio() %>
        </p>
      </div>

      <div><span>Height:</span><span><%= other.getHeight() %></span></div>
      <div><span>Weight:</span><span><%= other.getWeight() %></span></div>

      <div class="interests">
        <div>
          <span>Interests:</span>
        </div>
        <div class="tags">
          <% for (Interest interest : interests) { %>
            <div class="tag"><span><%= interest.getName() %></span></div>
          <% } %>
        </div>
      </div>
    </div>
  </body>
</html>
