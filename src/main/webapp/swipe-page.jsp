<%@ page import="gr.aueb.cf.mutu.models.User" %>
<%@ page import="gr.aueb.cf.mutu.Authentication" %>
<%@ page import="gr.aueb.cf.mutu.models.Picture" %>
<%@ page import="gr.aueb.cf.mutu.models.Interest" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.Random" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
  User loggedUser = Authentication.getSessionUser(request);
  if (loggedUser == null) {
    response.sendRedirect("login.jsp");
    return;
  }

  List<User> otherUsers = User.users.stream()
          .filter(u -> u.getId() != loggedUser.getId())
          .collect(Collectors.toList());

  User other = otherUsers.get(new Random().nextInt(otherUsers.size()));
  LocalDate birthday = other.getBirthday();
  Date today = new Date();
  System.out.println(today.getYear() + " " + birthday.getYear());
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

      .avatar {
        width: 150px; /* Set the desired fixed width */
        height: 200px; /* Set the desired fixed height */
        object-fit: cover; /* Ensures the image fits without distortion */
        display: block; /* Ensures proper alignment */
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
            class="avatar"
            src="<%= Picture.getAvatarByUserId(other.getId()) %>"
            alt="<%= other.getName() %>"
            title="<%= other.getName() %>"
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
        <form method="POST" action="swipe">
          <input name="other-user-id" type="hidden" value="<%= other.getId() %>" />
          <input name="swipe-direction" type="hidden" value="SWIPE-LEFT" />
          <button type="submit"><span>No</span></button>
        </form>
        <form method="POST" action="swipe">
          <input name="other-user-id" type="hidden" value="<%= other.getId() %>" />
          <input name="swipe-direction" type="hidden" value="SWIPE-RIGHT" />
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
