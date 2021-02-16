<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jp.co.acroit.zaiko2020.book.Book"%>
<%@ page import="java.util.List"%>
<%@ page import="java.time.format.DateTimeFormatter"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name='viewport' content='width=device-width, initial-scale=1.0'>
<meta http-equiv='X-UA-Compatible' content='ie=edge'>
<title>復元書籍の確認</title>
<link href="https://unpkg.com/sanitize.css" rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="js/dialog/dialog-polyfill.css" />
<link href="styleBookInOut.css" rel="stylesheet">
<link href="styleCheck.css" rel="stylesheet">
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
</head>
<body>
	<div id="main">
		<header>
			<span id="complete">以下のn件の書籍を復元します。</span>
		</header>

		<!--エラーメッセージ表示 -->
		<div id="error">
			<span>${sessionScope.error}</span>
		</div>
		<!--エラーメッセージここまで-->

		<!--復元書一覧表示-->
		<article id="pcLists">
			<section class="pageMover">
				<%@ include file="part/PageMoverRestration.jsp"%>
			</section>
			<section class="list">
				<table>
					<thead>
						<tr>
							<th class="headerBookName">書籍名</th>
							<th class="headerAuther">著 者</th>
							<th class="headerPublisher">出版社</th>
							<th class="headerSaleDate">発売日</th>
							<th class="headerIsbn">ISBN</th>
							<th class="headerPrice">価 格</th>
							<th class="headerStock">在庫数</th>
						</tr>
					</thead>
					<tbody>
						<%
							if (bookLists != null) {
								for(Book list : bookLists){
								%>
									<tr>
									</tr>
								<%
								}
							}
						%>
					</tbody>
				</table>
			</section>
			<section class="pageMover">
				<%@ include file="part/PageMoverRestration.jsp"%>
			</section>
		</article>
		<!--書籍情報表示ここまで-->

		<!--ボタン表示エリア-->
		<div id="buttons">

			<!--実行ボタン-->
			<input type="submit" name="button" id="add"
				class="button button-main button-border" value="実行"
				form="restrationForm">

			<!--キャンセルボタン-->
			<input type="submit" name="button" id="cancel"
				class="button button-cancel button-border" value="キャンセル"
				form="cancelForm">
		</div>
		<!--ボタン表示エリアここまで-->
		<form id="addForm" action="/Zaiko2020/RestrationProcess" method="post"></form>
		<form id="cancelForm" action="/Zaiko2020//RestorationForm"
			method="post"></form>
	</div>
</body>
<script src="js/dialog/dialog-polyfill.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</html>