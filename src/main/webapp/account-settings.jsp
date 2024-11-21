<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="gr.aueb.cf.mutu.models.User" %>
<%@ page import="gr.aueb.cf.mutu.Authentication" %>
<%
    User loggedUser = Authentication.getSessionUser(request);
    if (loggedUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    boolean updateSuccess = request.getParameter("updateSuccess") != null;
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Account Settings</title>
    <link
            href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap"
            rel="stylesheet"
    />
    <style>
        body {
            font-family: "Roboto", "Helvetica", "Arial", sans-serif;
            margin: 20px;
        }

        form {
            max-width: 400px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: orange;
        }

        label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"],
        input[type="number"],
        input[type="date"],
        textarea {
            width: 400px;
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        textarea {
            resize: vertical;
        }

        button {
            padding: 10px 15px;
            background-color: #4caf50;
            border: none;
            color: white;
            font-size: 16px;
            cursor: pointer;
            border-radius: 3px;
        }

        button:hover {
            background-color: #45a049;
        }

    </style>
</head>

<body>

<jsp:include page="navbar.jsp"/>

<h2>Account Settings</h2>

<% if (updateSuccess) { %>
<span style="color: green;">Account updated successfully!</span>
<% } %>

<label for="password">Password:</label>
<input type="password" id="password" name="password" value="<%=loggedUser.getPassword()%> " required/>

<label for="height">Height (cm):</label>
<input type="number" id="height" name="height" value="<%=loggedUser.getHeight()%>"/>

<label for="weight">Weight (kg):</label>
<input type="number" id="weight" name="weight" value="<%=loggedUser.getWeight()%>"/>

<label for="bio">Bio:</label>
<textarea
        id="bio"
        name="bio"
        rows="4"
        placeholder="Tell us about yourself..."><%=loggedUser.getBio() %></textarea>
</body>

<script>

    const password$ = document.getElementById("password")
    const height$ = document.getElementById("height")
    const weight$ = document.getElementById("weight")
    const bio$ = document.getElementById("bio")

    const elements = [password$, height$, weight$, bio$]

    function handleInput() {
        const params = {
            password: password$.value,
            height: height$.value,
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