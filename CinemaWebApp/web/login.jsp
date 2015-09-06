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
        <c:if test='${requestScope.message != null}'>
            <div class="col-md-4 col-md-offset-4 col-xs-10 col-xs-offset-1" id="erroreCampi">
                <p style="color: #ff6666">${requestScope.message}</p>
            </div>
        </c:if>

        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4 col-xs-10 col-xs-offset-1">

                    <form class="form-signin" action="LoginServlet" method="POST">
                        <h2 class="form-signin-heading">Please sign in</h2>
                        <label for="inputEmail" class="sr-only">Email address</label>
                        <input type="email" name="inputEmail" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
                        <label for="inputPassword" class="sr-only">Password</label>
                        <input type="password" name="inputPassword" id="inputPassword" class="form-control" placeholder="Password" required>
                        <br>
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                        <br>
                        <div style="text-align: center"><a href="repairpwd.jsp">Password dimenticata?</a> - <a href="registrazione.jsp">Registrati</a></div>
                    </form>
                    
                </div>
            </div>
        </div> <!-- /container -->
        
        <%-- Include the footer --%>
        <%@ include file="include/footer.jsp" %>
    </body>
</html>
