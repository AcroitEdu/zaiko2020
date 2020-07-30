<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <meta http-equiv='X-UA-Compatible' content='ie=edge'>
<title>書籍情報変更の確認</title>
	<link href="https://unpkg.com/sanitize.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="js/dialog/dialog-polyfill.css" />
    <link href="styleBookInOut.css" rel="stylesheet">
    <link href="styleCheck.css" rel="stylesheet">

</head>
<body>
	<div id="main">
		<header>
			<span id="complete">以下の内容で書籍情報の変更を行います。</span>
		</header>
		<div id="error">
			<span>${sessionScope.error}</span>
		</div>
		<div id="details">
			<jsp:include page="part/BookDetails.jsp">
				<jsp:param name="caption" value=" " />
				<jsp:param name="book" value="${book}" />
			</jsp:include>
		</div>
		<div id="buttons">
			<form id="editForm" action="/Zaiko2020/EditProcess" method="post">
				<input type="hidden" name="id" value="${book.id}">
				<input type="submit" name="button" id="edit" class="button button-main button-border" value="実行">
			</form>
			<form id="cancelForm" action="/Zaiko2020//Edit" method="post">
				<input type="submit" name="button" id="cancel" class="button button-cancel button-border" value="キャンセル">
			</form>
		</div>
    </div>
</body>
<script src="js/dialog/dialog-polyfill.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</html>