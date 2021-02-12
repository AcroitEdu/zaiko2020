<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jp.co.acroit.zaiko2020.book.Book"%>
<%@ page import="java.util.List"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%
	//表示する本の取得
Book items = (Book) session.getAttribute("book");
//日付フォーマットの作成
DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYY'年<br/>'MM'月'dd'日'");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name='viewport' content='width=device-width, initial-scale=1.0'>
<title>書籍の追加</title>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap"
	rel="stylesheet">
<link href="https://unpkg.com/sanitize.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="styleAddForm.css">
<link href="styleCheck.css" rel="stylesheet">
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
</head>
<body>
	<div id="main">
		<header>
			<ul class="boxed-tabs">
				<li id="inventoryListButton" class="tab">
					<form id="inventoryListForm" action="/Zaiko2020/inventoryList"
						method="post">
						<input type="hidden" name="form" value="3"> <span>在庫一覧</span>
					</form>
				</li>
				<li class="tab-current tab">追加</li>
				<li id="restorationButton" class="tab">
					<form id="restorationForm" action="/Zaiko2020/Restoration"
						method="post">
						<input type="hidden" name="form" value="復元"> <span>復元</span>
					</form>
				</li>
				<li id="restorationButton" class="tab">
					<form id="restorationForm" action="/Zaiko2020/Restoration"
						method="post">
						<input type="hidden" name="form" value="復元"> <span>履歴</span>
					</form>
				</li>
				<li id="logoutButton" class="tab-logout tab">
					<form id="logoutForm" action="/Zaiko2020/logout" method="post">
						<span>ログアウト</span>
					</form>
				</li>
			</ul>
		</header>

		<div class="content">
			<!-- エラーメッセージ表示 -->
			<div id="error">
				<span>${sessionScope.error}</span>
			</div>
			<!-- エラーメッセージ表示ここまで -->
			<div class="add-options">
				<!-- 書籍情報入力フォーム -->
				<form id="addOptions" name="addOptions" action="/Zaiko2020/AddCheck"
					method="post">
					<ul>
						<li><label>書籍名</label> <textarea id="bookName"
								rows="3" name="bookName" required>${book.name}</textarea></li>
						<li><label>著者</label> <textarea id="author"
								rows="3" name="author" required>${book.author}</textarea></li>
						<li><label>出版社</label> <textarea
								id="publisher" rows="3" name="publisher" required>${book.publisher}</textarea>
						</li>
						<li>
							<label>ISBN</label>
							<input type="tel" id="isbn" name="isbn" pattern="^[0-9]{13}$" maxlength="13" value="${book.isbn}" required>
							<span id="caution">※既存の番号を入力しないようお願いいたします。</span>
						</li>
						<li>
							<label>発売日</label> <input type="date" id="date" name="date" value="${book.salesDate}" max="9999-12-31" required>
						</li>
						<li>
							<label>価格</label>
							<div class="units">
								<input type="tel" id="price" name="price" pattern="^[0-9]{1,6}$" maxlength="6" value="${book.price}" required>
								<span>円</span>
							</div>
						</li>
						<li>
							<label>在庫数</label>
							<div class="units">
								<input type="tel" id="stock" name="stock" pattern="^[0-9]{1,6}$" maxlength="6" value="${book.stock}" required>
								<span>冊</span>
							</div>
						</li>
					</ul>
				</form>
				<!-- 書籍情報入力フォーム -->
			</div>
			<!-- 実行ボタン表示 -->
			<div id="buttons">
				<input type="submit" id="addButton" class="button button-main button-border" name="button" value="実行" form="addOptions">
			</div>
			<!-- 実行ボタン表示ここまで -->
		</div>
	</div>
</body>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="js/TabTransition.js"></script>
</html>