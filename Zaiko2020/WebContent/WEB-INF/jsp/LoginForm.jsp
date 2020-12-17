<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name='viewport' content='width=device-width, initial-scale=1.0'>
<title>ログイン画面</title>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="style.css">
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
</head>
<body>
  <div class="main">
    <h1>在庫管理システム ログイン画面</h1>
	<div class="error">
		<span>${sessionScope.error}</span>
	</div>
    <form action="/Zaiko2020/login" method="post">
      <div class="form">
        <p>
          <label for="idForm">ID</label>
        </p>
        <input type="text" name="id" required pattern="^[0-9A-Za-z]+$" id="idForm">
        <p>
          <label for="passForm">PASSWORD</label>
        </p>
        <input type="password" name="password" required pattern="^[0-9A-Za-z]+$" id="passForm">
      </div>
      <p>
        <input type="submit" value="ログイン" class="btn">
      </p>
    </form>
  </div>
</body>
</html>