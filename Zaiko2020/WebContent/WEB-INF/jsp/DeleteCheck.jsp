<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jp.co.acroit.zaiko2020.book.Book"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	//書籍名や在庫数等の、本の情報を表示する。
//ArrivalForm.jspやShippingForm.jsp、Result.jspからincludeして使う。
//caption変数の値をcaption要素として表示する。
//日付フォーマットの作成
DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYY'年'MM'月'dd'日'");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name='viewport' content='width=device-width, initial-scale=1.0'>
<meta http-equiv='X-UA-Compatible' content='ie=edge'>
<title>書籍削除確認</title>
<link href="https://unpkg.com/sanitize.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="js/dialog/dialog-polyfill.css" />
<link href="styleBookInOut.css" rel="stylesheet">
<link href="styleCheck.css" rel="stylesheet">
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
</head>
<body>
	<div id="main">
		<header>
			<span id="complete">以下の書籍を削除いたします。本当によろしいですか？</span>
		</header>
		<!-- エラーメッセージ表示 -->
		<div id="error">
			<span>${sessionScope.error}</span>
		</div>
		<!-- エラーメッセージ表示ここまで -->

		<!-- 書籍情報表 -->
		<div id="details">
			<!-- PC表示 -->
			<table id="pcDisplay">
				<colgroup>
					<col class="table-header">
					<col class="table-content">
				</colgroup>
				<tr>
					<th scope="row">書籍名</th>
					<td>${book.name}</td>
				</tr>
				<tr>
					<th scope="row">著者</th>
					<td>${book.author}</td>
				</tr>
				<tr>
					<th scope="row">出版社</th>
					<td>${book.publisher}</td>
				</tr>
				<tr>
					<th scope="row">発売日</th>
					<td><%=((Book) session.getAttribute("book")).getSalesDate().format(dateFormat)%></td>
				</tr>
				<tr>
					<th scope="row">ISBN</th>
					<td>${book.isbn}</td>
				</tr>
				<tr>
					<th scope="row">価格</th>
					<td>${book.price} 円</td>
				</tr>
				<tr>
					<th scope="row">在庫数</th>
					<td>${book.stock} 冊</td>
				</tr>
			</table>
			<!-- PC表示ここまで -->

			<!-- スマホ表示 -->
			<table id="sumahoDisplay">
				<colgroup>
					<col class="table-header">
					<col class="table-content">
					<col class="table-header">
					<col class="table-content">
				</colgroup>
				<tr>
					<th>書籍名</th>
					<td colspan="3">${book.name}</td>
				</tr>
				<tr>
					<th>著者</th>
					<td colspan="3">${book.author}</td>
				</tr>
				<tr>
					<th>出版社</th>
					<td colspan="3">${book.publisher}</td>
				</tr>
				<tr>
					<th>発売日</th>
					<td colspan="3"><%=((Book) session.getAttribute("book")).getSalesDate().format(dateFormat)%></td>
				</tr>
				<tr>
					<th>ISBN</th>
					<td colspan="3">${book.isbn}</td>
				</tr>
				<tr>
					<th>価格</th>
					<td>${book.price} 円</td>
					<th>在庫数</th>
					<td>${book.stock} 冊</td>
				</tr>
			</table>
			<!-- スマホ表示ここまで -->
		</div>
		<!-- 書籍情報表示ここまで -->
		<div id="buttons">
			<input type="hidden" name="id" value="${book.id}" form="deleteForm">
			<input type="submit" name="button" id="delete"
				class="button button-warning button-border" value="実行"
				form="deleteForm"> <input type="submit" name="button"
				id="cancel" class="button button-cancel button-border" value="キャンセル"
				form="cancelForm">
		</div>
		<form id="deleteForm" action="/Zaiko2020/DeleteProcess" method="post"></form>
		<form id="cancelForm" action="/Zaiko2020//Edit" method="post"></form>
	</div>
</body>
<script src="js/dialog/dialog-polyfill.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</html>