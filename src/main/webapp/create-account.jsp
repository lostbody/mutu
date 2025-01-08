<%@ page import="gr.aueb.cf.mutu.dto.InterestDto" %>
<%@ page import="java.util.List" %>
<%@ page import="gr.aueb.cf.mutu.service.InterestService" %>
<%
    List<InterestDto> interests = InterestService.getImpl().getAll();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="head.jsp"/>
    <style>
        .selected {
            color: white;
            background-color: #0056b3;
            border-color: white;
        }
    </style>
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
                    <input class="form-control" type="date" id="birthday" name="birthday" required/>
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
                    <label class="form-label">Interests:</label>
                    <div class="d-flex flex-wrap gap-1">
                        <% for (InterestDto interest : interests) { %>
                        <div class="interest p-1 border rounded"
                             data-id="<%= interest.getId() %>"><%= interest.getName() %>
                        </div>
                        <% } %>
                    </div>
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
<script>
    const interests = Array.from(document.querySelectorAll(".interest"));
    const form = document.querySelector("form");

    // Add click handler for interests
    interests.forEach(interest => {
        interest.addEventListener("click", () => {
            interest.classList.toggle("selected");
        });
    });

    // Add form submit handler
    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const formData = new FormData(form);
        // Add selected interests to form data
        const selectedInterests = interests
            .filter(interest => interest.classList.contains("selected"))
            .map(interest => interest.dataset.id)
            .join(",");

        formData.append("interests", selectedInterests);

        // Submit form data
        fetch("create-account", {
            method: "POST",
            body: new URLSearchParams(formData)
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = "swipe-page.jsp";
                } else if (response.status === 400) {
                    alert("Please check your input values");
                }
            })
            .catch(error => console.error('Error:', error));
    });
</script>
</html>
