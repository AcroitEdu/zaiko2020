<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
  <h1>在庫管理システム ログイン画面</h1>
  <div class="error">
    <span style="color: red;">${sessionScope.error}</span>
  </div>
  <form action="/Zaiko2020/login" method="post">
    <div class="form">
      <ul>
        <li><label>ID</label> <input type="text" name="id" required></li>
        <li><label>PASSWORD</label> <input type="password"
          name="password" required></li>
      </ul>
    </div>
    <p>
      <input type="submit" value="ログイン" class="btn">
    </p>
  </form>
</body>
</html>
