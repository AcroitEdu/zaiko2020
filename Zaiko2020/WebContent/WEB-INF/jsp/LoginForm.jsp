<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
  <h1>在庫管理システム ログイン画面</h1>
  <div class="error">
    <span>${sessionScope.error}</span>
  </div>
  <form action="/Zaiko2020/login" method="post">
    <div class="form">
      <ul>
        <li><label>ID</label> <input type="text" name="id" required pattern="^[0-9A-Za-z]+$"></li>
        <li><label>PASSWORD</label> <input type="password"
          name="password" required  pattern="^[0-9A-Za-z]+$"></li>
      </ul>
    </div>
    <p>
      <input type="submit" value="ログイン" class="btn">
    </p>
  </form>
</body>
</html>
