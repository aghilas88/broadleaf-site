<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Login</title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/static/app/resources/images/favicon.png" />
</head>
<body class="claro">
<div>
    <form action="${pageContext.request.contextPath}/login_admin_post" method="post" class="form-horizontal">
        <div class="control-group">
            <label class="control-label" for="username">Username</label>
            <div class="controls">
                <input type="text" id="username" name="j_username" autofocus="autofocus" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="password">Password</label>
            <div class="controls">
                <input type="password" id="password" name="j_password" />
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn" >Sign In</button>
            </div>
        </div>
    </form>
    <div th:fragment="messages">
    </div>
    <div>
        <a href="${pageContext.request.contextPath}/forgotUsername">Forgot Username</a> |
        <a href="${pageContext.request.contextPath}/resetPassword">Forgot Password</a> |
        <a href="${pageContext.request.contextPath}/resetPassword">Change Password</a>
    </div>
</div> 

</body>
</html>