<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jp.co.acroit.zaiko2020.book.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
//表示する本の取得
Book items = (Book)session.getAttribute("book");
//日付フォーマットの作成
DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYY'年<br/>'MM'月'dd'日'");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>書籍の追加</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body>
	<div id="main">
		<header>
			<ul class="boxed-tabs">
				<li class="tab">在庫一覧</li>
				<li class="tab-current tab">追加</li>
				<li class="tab">復元</li>
				<li class="tab-logout tab">
					<form id="logoutForm" action="/Zaiko2020/logout" method="post">
						<span id="logoutButton">ログアウト</span>
					</form>
				</li>
			</ul>
		</header>
		<div class="content">
			<div class="add-options">
				<form name="addOptions" action="/Zaiko2020/AddCheck" method="post">
					<ul>
						<li>
							<label for="bookName">書籍名</label>
							<textarea id="bookName" rows="3" name="bookName">${book.name}</textarea>
						</li>
						<li>
							<label for="author">著者</label>
							<textarea id="author" rows ="3" name="author">${book.author}</textarea>
						</li>
						<li>
							<label for="publisher">出版社</label>
							<textarea id="publisher" rows ="3" name="publisher">${book.publisher}</textarea>

						</li>
						<li>
                            <label for="isbn">ISBN</label>
                            <input type="text" id="isbn" name="isbn" pattern="^[0-9]+$" maxlength="13" value="${book.isbn}">
                        </li>
                        <li>
                            <label for="date">発売日</label>
                            <input type="date" id="date" name="date" value="${book.salesDate}">
                        </li>
                        <li>
                            <label for="stock">在庫数</label>
                                <input type="text" id="stock" name="stock" pattern="^[0-9]{1,6}$" maxlength="6" value="${book.stock}">
                                <span>冊</span>
                        </li>
                        <li>
                            <label for="price">価格</label>
                                <input type="text" id="price" name="price" pattern="^[0-9]{1,6}$" maxlength="6" value="${book.price}">
                                <span>冊</span>
                        </li>
					</ul>
					<input type="submit" id="addButton" class="button" name="button" value="実行">
				</form>
			</div>
		</div>
	</div>
</body>
</html>