<%@ page import="gr.aueb.cf.mutu.Authentication" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="gr.aueb.cf.mutu.dto.UserDto" %>
<%@ page import="gr.aueb.cf.mutu.service.UserService" %>
<%@ page import="gr.aueb.cf.mutu.dto.PictureDto" %>
<%@ page import="gr.aueb.cf.mutu.service.PictureService" %>
<%@ page import="gr.aueb.cf.mutu.dto.InterestDto" %>
<%@ page import="gr.aueb.cf.mutu.service.InterestService" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    UserDto loggedUser = Authentication.getSessionUser(request);
    if (loggedUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    UserDto other = UserService.getImpl().getPotentialMatch(loggedUser.getId());

    LocalDate birthday = other.getBirthday();
    Date today = new Date();
    int age = today.getYear() - birthday.getYear();

    List<PictureDto> pictures = PictureService.getImpl().getPicturesByUserId(other.getId());
    List<InterestDto> interests = InterestService.getImpl().getInterestsByUserId(other.getId());
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="head.jsp"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css" integrity="sha512-5Hs3dF2AEPkpNAR7UiOHba+lRSJNeM2ECkwxUIxC1Q/FLycGTbNapWXB4tP889k5T5Ju8fs4b1P5z/iB4nMfSQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <title>swipe-page</title>
    <style>

    </style>
</head>
<body>
<div class="container">
    <!-- Navbar -->
    <jsp:include page="navbar.jsp"/>

    <!-- Profile Card -->
    <div class="card text-start my-3 shadow-lg">
        <div class="card-body">

            <h2 class="card-title"><%= other.getName() %>, <%= age %></h2>

            <div class="pictures position-relative">
                <button id="button-left"
                        class="btn btn-secondary position-absolute start-0 top-50 translate-middle-y">
                    <i class="fa-solid fa-angle-left fs-4"></i>

                </button>
                <div class="image-container">
                    <% for (PictureDto picture : pictures) { %>
                    <img class="avatar d-none w-100 h-100" src="<%= picture.getBlob() %>" alt="<%= other.getName() %>"/>
                    <% } %>
                </div>
                <button id="button-right"
                        class="btn btn-secondary position-absolute end-0 top-50 translate-middle-y">
                    <i class="fa-solid fa-angle-right fs-4"></i>
                </button>
            </div>
            <div class="mt-3 text-center">
                <span id="pic-number">1</span>/<%= pictures.size() %>
            </div>

            <div class="d-flex justify-content-between">
                <form method="POST" action="swipe">
                    <input name="other-user-id" type="hidden" value="<%= other.getId() %>"/>
                    <input name="swipe-direction" type="hidden" value="SWIPE-LEFT"/>
                    <button class="btn btn-danger w-100" type="submit">No</button>
                </form>
                <form method="POST" action="swipe">
                    <input name="other-user-id" type="hidden" value="<%= other.getId() %>"/>
                    <input name="swipe-direction" type="hidden" value="SWIPE-RIGHT"/>
                    <button class="btn btn-success w-100" type="submit">Yes</button>
                </form>
            </div>

            <p class="card-text mt-3"><%= other.getBio() %></p>

            <div class="mt-3">
                <span><strong>Height:</strong> <%= other.getHeight() %> cm</span>
            </div>

            <div class="mt-3">
                <span><strong>Weight:</strong> <%= other.getWeight() %> kg</span>
            </div>

            <div class="d-flex mt-2">
                <div class="p-1 ps-0"><strong>Interests:</strong></div>
                <div class="d-flex flex-wrap gap-2">
                    <% for (InterestDto interest : interests) { %>
                    <span class="p-1 border rounded"><%= interest.getName() %></span>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>

    const images$ = Array.from(document.querySelectorAll(".avatar"))
    const buttonRight$ = document.getElementById("button-right")
    const buttonLeft$ = document.getElementById("button-left")
    const picNumber$ = document.getElementById("pic-number")
    const imageContainer$ = document.querySelector(".image-container")

    let imgIndex = 0

    const update = () => {
        for (let i = 0; i < images$.length; i++) {
            if (i === imgIndex) {
                images$[i].classList.remove("d-none")
            } else {
                images$[i].classList.add("d-none")
            }
            picNumber$.textContent = imgIndex + 1
        }
    }

    update()

    const handleRightButton = () => {
        imgIndex = imgIndex < images$.length - 1 ? imgIndex + 1 : 0
        update()
    }
    const handleLeftButton = () => {
        imgIndex = imgIndex > 0 ? imgIndex - 1 : images$.length - 1
        update()
    }

    buttonRight$.addEventListener("click", handleRightButton)
    buttonLeft$.addEventListener("click", handleLeftButton)
    imageContainer$.addEventListener("click", handleRightButton)

</script>
</html>
