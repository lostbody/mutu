<%@ page import="gr.aueb.cf.mutu.models_dev.User" %>
<%@ page import="gr.aueb.cf.mutu.Authentication" %>
<%  //ψάχνει να βρει αν υπάρχει user στο hashmap sessions. Δηλαδή αν έχει ξαναμπει αυτός ο user και εχει token

    User user = Authentication.getSessionUser(request);
    //αν βρεθεί, αν δεν ειναι null δηλαδη, τοτε λεμε στον browser να μας κάνει redirect στο swipe-page.
    if (user != null) {
        response.sendRedirect("swipe-page.jsp");
    //Αν δεν βρεθεί τότε στο login για να ξανακανει login
    } else {
        response.sendRedirect("login.jsp");
    }
%>
