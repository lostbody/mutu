<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="gr.aueb.cf.mutu.Authentication" %>
<%@ page import="gr.aueb.cf.mutu.dto.UserDto" %>
<%
    UserDto loggedUser = Authentication.getSessionUser(request);
    if (loggedUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    boolean updateSuccess = request.getParameter("updateSuccess") != null;
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="head.jsp"/>
    <title>Account Settings</title>

    <style>

    </style>
</head>

<body>


<div class="container">

    <jsp:include page="navbar.jsp"/>
    <div class="mt-3">
        <span><strong>Account Settings</strong></span>
    </div>

    <% if (updateSuccess) { %>
    <span style="color: green;">Account updated successfully!</span>
    <% } %>

    <div class="mb-3 mt-3">
        <label class="form-label" for="password">Password:</label>
        <input class="form-control" type="password" id="password" name="password" value="<%=loggedUser.getPassword()%> " required/>
    </div>

    <div class="mb-3">
        <label class="form-label" for="weight">Weight (kg):</label>
        <input class="form-control" type="number" id="weight" name="weight" value="<%=loggedUser.getWeight()%>"/>
    </div>

    <div class="mb-3">
        <label class="form-label" for="bio">Bio:</label>
        <textarea class="form-control"
                id="bio"
                name="bio"
                rows="4"
                placeholder="Tell us about yourself..."><%=loggedUser.getBio() %></textarea>
    </div>
</div>
</body>

<script>

    const password$ = document.getElementById("password")
    const weight$ = document.getElementById("weight")
    const bio$ = document.getElementById("bio")

    const elements = [password$, weight$, bio$]

    function handleInput() {
        const params = {
            password: password$.value,
            weight: weight$.value,
            bio: bio$.value,
        }
        const formBody = new URLSearchParams(params).toString()

        fetch("account-settings", {
            method: "POST",
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formBody,
        })
    }

    for (const element$ of elements) {
        element$.addEventListener("input", handleInput)
    }

</script>
</html>