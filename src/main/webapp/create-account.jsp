<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="head.jsp"/>
    <title>Create Account</title>
</head>

<body>
<div class="container">
    <div class="card shadow-lg my-3">
        <div class="card-header">
            <span><strong>Create Account</strong></span>
        </div>

        <div class="card-body">
            <!-- Check for error message -->
            <% String error = request.getParameter("error"); %>
            <% if ("emailExists".equals(error)) { %>
            <div class="alert alert-danger">
                This email is already in use. Please try a different one.
            </div>
            <% } %>

            <form method="POST" action="create-account" class="d-flex flex-column">
                <div class="mb-3">
                    <label class="form-label" for="email">Email</label>
                    <input class="form-control" type="email" id="email" name="email" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="password">Password</label>
                    <input class="form-control" type="password" id="password" name="password" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="name">Name</label>
                    <input class="form-control" type="text" id="name" name="name" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="birthday">Birthday</label>
                    <input class="form-control" type="date" id="birthday" name="birthday"/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="height">Height (cm)</label>
                    <input class="form-control" type="number" id="height" name="height"/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="weight">Weight (kg)</label>
                    <input class="form-control" type="number" id="weight" name="weight"/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="bio">Bio</label>
                    <textarea class="form-control"
                              id="bio"
                              name="bio"
                              rows="4"
                              placeholder="Tell us about yourself..."
                    ></textarea>
                </div>

                <button class="btn btn-outline-secondary align-self-end" type="submit">Create Account</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
