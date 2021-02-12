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
<title>書籍の編集</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
<link href="https://unpkg.com/sanitize.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="styleEditForm.css">
<link href="styleCheck.css" rel="stylesheet">
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
</head>
<body>
	<div id="main">
		<header>
			<h1>編集</h1>
			<!-- 削除画面遷移ボタン -->
			<input type="submit" id="delete" class="button button-warning button-border" value="書籍の削除" form="deleteForm">
			<!-- 削除画面遷移ボタンここまで -->
		</header>
		<form id="deleteForm" action="/Zaiko2020/DeleteCheck" method="post"></form>
		<div class="content">
			<!-- エラーメッセージ表示 -->
			<div id="error">
				<span>${sessionScope.error}</span>
			</div>
			<!-- エラーメッセージ表示ここまで -->
			<!-- 編集内容入力フォーム -->
			<div class="edit-options">
				<form id="editForm" name="editOptions" action="/Zaiko2020/EditCheck" method="post">
					<ul>
						<li>
							<label>書籍名</label>
							<textarea id="bookName" rows="3" name="bookName" required>${book.name}</textarea>
						</li>
						<li>
							<label>著者</label>
							<textarea id="author" rows="3" name="author" required>${book.author}</textarea>
						</li>
						<li>
							<label>出版社</label>
							<textarea id="publisher" rows="3" name="publisher" required>${book.publisher}</textarea>
						</li>
						<li>
							<label>ISBN</label>
							<input type="tel" id="isbn" name="isbn" pattern="^[0-9]{13}$" maxlength="13" value="${book.isbn}" required>
						</li>
						<li>
							<label>発売日</label>
							<input type="date" id="date" name="date" value="${book.salesDate}" max="9999-12-31" required>
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
				<!-- 編集内容入力フォームここまで -->
				<!-- 確認画面遷移ボタン -->
				<form id="cancelForm" action="/Zaiko2020/inventoryList" method="post">
					<input type="hidden" name="form" value="4">
				</form>
				<!-- 確認画面遷移ボタン -->
			</div>
			<div id="buttons">
				<input type="submit" name="button" id="edit" class="button button-main button-border" value="実行" form="editForm">
				<input type="submit" name="button" id="cancel" class="button button-cancel button-border" value="キャンセル" form="cancelForm">
			</div>
		</div>
	</div>
</body>
</html>