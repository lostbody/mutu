<%
    boolean accountCreated = request.getParameter("accountCreated") != null;
    boolean emailExists = request.getParameter("error") != null && request.getParameter("error").equals("emailExists");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Create Account</title>
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
<h2>Create Account</h2>

<% if (accountCreated) { %>
<span>Account Successfully Created</span>
<% }
else {%>

    <% if (emailExists) { %>
        <span style="color: red">Email is already in use. Please try a different one.</span>
    <% } %>

    <form action="create-account" method="POST">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required/>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required/>

        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required/>

        <label for="birthday">Birthday:</label>
        <input type="date" id="birthday" name="birthday" />

        <label for="height">Height (cm):</label>
        <input type="number" id="height" name="height" />

        <label for="weight">Weight (kg):</label>
        <input type="number" id="weight" name="weight" />

        <label for="bio">Bio:</label>
        <textarea
                id="bio"
                name="bio"
                rows="4"
                placeholder="Tell us about yourself..."
        ></textarea>

        <button type="submit">Create Account</button>
    </form>
</body>
</html>
<% } %>
