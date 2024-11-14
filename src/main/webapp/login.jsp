<%
    boolean shouldShowError = request.getParameter("error") != null;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Mutu</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          integrity="sha256-PI8n5gCcz9cQqQXm3PEtDuPG8qx9oFsFctPg0S5zb8g=" crossorigin="anonymous">
    <style>
        .container {
            width: 400px;
            margin: 0 auto;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
    </style>
</head>
<body>
<div class="container">
    <div>
        <img src="static/img/mutu_logo.jpg" alt="mutu-logo" title="mutu-logo"/>
    </div>

    <div>
        <% if (shouldShowError) { %>
            <div>
                <span>Login Error</span>
            </div>
        <% } %>
        <form method="POST" action="login">
            <div>
                <label for="email"><span>email</span></label>
                <input type="email" name="email" id="email"/>
            </div>
            <div>
                <label for="password"><span>password</span></label>
                <input type="password" name="pass" id="password"/>
            </div>
            <div>
                <button type="submit"><span>login</span></button>
            </div>
        </form>
    </div>

    <div>
        <span>Don't have an account?</span>
        <a href="create-account.jsp">Create an account</a>
    </div>
</div>
</body>
</html>
