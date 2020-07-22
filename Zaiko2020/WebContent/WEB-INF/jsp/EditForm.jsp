<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>書籍の編集</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body>
	<div id="main">
		<header>
			<h1>編集</h1>
		</header>
		<div class="content">
			<div class="edit-options">
				<form name="editOptions" action="/Zaiko2020/EditCheckController"
					method="post">
					<ul>
						<li>
							<label for="bookName">書籍名</label>
							<textarea id="bookName" rows="3" name="bookName"></textarea>
						</li>
						<li>
							<label for="author">著者</label>
							<textarea id="author" rows="3" name="author"></textarea>
						</li>
						<li>
							<label for="publisher">出版社</label>
							<textarea id="publisher" rows="3" name="publisher"></textarea>
						</li>
						<li>
							<label for="isbn">ISBN</label>
							<input type="text" id="isbn" name="isbn" pattern="^[0-9]+$" maxlength="13">
						</li>
						<li>
							<label for="date">発売日</label>
							<input type="date" id="date" name="date">
						</li>
						<li>
							<label for="stock">在庫数</label>
							<input type="text" id="stock" name="stock" pattern="^[0-9]{1,6}$" maxlength="6">
							<span>冊</span>
						</li>
						<li>
							<label for="price">価格</label>
							<input type="text"id="price" name="price" pattern="^[0-9]{1,6}$" maxlength="6">
							<span>冊</span>
						</li>
					</ul>
					<input type="submit" id="editButton" class="button" name="button" value="実行">
					<input type="button" id="cancelButton" class="button" name="button" value="キャンセル">
				</form>
			</div>
		</div>
	</div>
</body>
</html>