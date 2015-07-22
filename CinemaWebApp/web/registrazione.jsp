<%-- 
    Document   : home
    Created on : 24-giu-2015, 10.29.59
    Author     : Patrik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="page" value="login"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <%@ include file="include/header.jsp" %>


    </head>
    <body>
        <c:if test='${requestScope.errorMessage != null}'>
            <div class="col-md-4 col-md-offset-4 col-xs-10 col-xs-offset-1" id="erroreCampi">
                <p style="color: #ff6666">${requestScope.errorMessage}</p>
            </div>
        </c:if>
        
        <!-- Script per controllo corrispondenza password -->
        <script type="text/javascript">
            function checkPwd() {
                //Store the password field objects into variables ...
                var pass1 = document.getElementById('inputPassword');
                var pass2 = document.getElementById('repeatPassword');
                //Store the Confimation Message Object ...
                var message = document.getElementById('confirmMessage');
                //Set the colors we will be using ...
                var goodColor = "#66cc66";
                var badColor = "#ff6666";
                //Compare the values in the password field 
                //and the confirmation field
                if (pass1.value == pass2.value) {
                    //The passwords match. 
                    //Set the color to the good color and inform
                    //the user that they have entered the correct password 
                    pass2.style.backgroundColor = goodColor;
                    message.style.color = goodColor;
                    message.innerHTML = "Passwords Match!"
                } else {
                    //The passwords do not match.
                    //Set the color to the bad color and
                    //notify the user.
                    pass2.style.backgroundColor = badColor;
                    message.style.color = badColor;
                    message.innerHTML = "Passwords Do Not Match!"
                }
            }
        </script>

        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4 col-xs-10 col-xs-offset-1">

                    <form class="form-signin" method="POST" action="RegistrazioneServlet" >
                        <h2 class="form-signin-heading">Registrazione</h2>
                        <label for="inputEmail" class="sr-only">Email address</label>
                        <input type="email" name="inputEmail" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
                        <label for="inputPassword" class="sr-only">Password</label>
                        <input type="password" name="inputPassword" id="inputPassword" class="form-control" placeholder="Password" required>
                        <label for="repeatPassword" class="sr-only">Repeat password</label>
                        <input type="password" name="repeatPassword" id="repeatPassword" class="form-control" placeholder="Repeat password" onkeyup="checkPwd()" required>
                        <span id="confirmMessage" class="confirmMessage"></span>
                        <br>
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Registrati</button>
                    </form>

                </div>
            </div>
        </div> <!-- /container -->

    </body>
</html>
