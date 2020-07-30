<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jp.co.acroit.zaiko2020.book.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
int currentPage = 1;
if(session.getAttribute("page") != null){
    currentPage = (int)session.getAttribute("page");
}
int maxPage = 1;
if(session.getAttribute("maxPage") != null){
    maxPage = (int)session.getAttribute("maxPage");
}
int count = 1;
if(session.getAttribute("count") != null){
    count = (int)session.getAttribute("count");
}
//表示する本の取得
List<Book> items = (List<Book>)session.getAttribute("items");
//日付フォーマットの作成
DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYY'年<br/>'MM'月'dd'日'");
%>
<!DOCTYPE html>
<html lang='ja'>

<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <meta http-equiv='X-UA-Compatible' content='ie=edge'>
    <title>在庫一覧</title>
    <link href="https://unpkg.com/sanitize.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
    <link href="styleInventoryList.css" rel="stylesheet">
</head>

<body>
    <div id="main">
        <header>
            <ul class="boxed-tabs">
                <li class="tab-current tab">在庫一覧</li>
                <li id="addButton" class="tab">
                	<form id="addForm" class="button" name="button" action="/Zaiko2020/Add" method="post">
                        <span>追加</span>
                    </form>
                </li>
                <li id="restorationButton" class="tab">
                    <form id="restorationForm" action="/Zaiko2020/Restoration" method="get">
						<span>復元</span>
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
            <div class="search-options">
                <form name="searchOptions" action="/Zaiko2020/inventoryList" method="post">
                <ul id="flexFormWrappable">
                        <li>
                            <label for="bookName">書籍名</label>
                            <input type="text" id="bookName" class="flexFormItem" name="name"
                                value="${conditions.name}">
                        </li>
                        <li>
                            <label for="author">著者</label>
                            <input type="text" id="author" class="flexFormItem" name="author"
                                value="${conditions.author}">
                        </li>
                        <li>
                            <label for="publisher">出版社</label>
                            <input type="text" id="publisher" class="flexFormItem" name="publisher"
                                value="${conditions.publisher}">
                        </li>
                        <li>
                            <label for="isbn">ISBN</label>
                            <input type="text" id="isbn" class="flexFormItem" name="isbn" pattern="^[0-9]+$" maxlength="13"
                                value="${conditions.isbn}">
                        </li>
                        <li>
                            <label for="beforeAfter">発売日</label>
                            <div class="flexFormItem">
                                <input type="date" id="date" name="date" value="${conditions.salesDate}">
                                <select id="beforeAfter" name="beforeAfter" data-value="${conditions.salesDateFlag}">
                                    <option value="equals">に一致</option>
                                    <option value="before">以前</option>
                                    <option value="after">以降</option>
                                </select>
                            </div>
                        </li>
                        <li>
                            <label for="largeOrSmall">在庫数</label>
                            <div class="flexFormItem">
                                <input type="text" id="stock" name="stock" pattern="^[0-9]+$" value="${conditions.stock}">
                                <span>冊</span>
                                <select id="largeOrSmall" name="largeOrSmall" data-value="${conditions.stockFlag}">
                                    <option value="equals">に等しい</option>
                                    <option value="ltoe">以下</option>
                                    <option value="gtoe">以上</option>
                                </select>
                            </div>
                        </li>
                    </ul>
<!--                     <ul id="flexFormWrappable"> -->
<!--                         <li> -->
<!--                             <label for="bookName">書籍名</label> -->
<!--                             <input type="text" id="bookName" class="flexFormItem" name="name" -->
<%--                                 value="${conditions.name}"> --%>
<!--                         </li> -->
<!--                         <li> -->
<!--                             <label for="author">著者</label> -->
<!--                             <input type="text" id="author" class="flexFormItem" name="author" -->
<%--                                 value="${conditions.author}"> --%>
<!--                         </li> -->
<!--                         <li> -->
<!--                             <label for="publisher">出版社</label> -->
<!--                             <input type="text" id="publisher" class="flexFormItem" name="publisher" -->
<%--                                 value="${conditions.publisher}"> --%>
<!--                         </li> -->
<!--                         <li> -->
<!--                             <label for="isbn">ISBN</label> -->
<!--                             <input type="number" id="isbn" class="flexFormItem" name="isbn" min="0" max="9999999999999" -->
<%--                                 value="${conditions.isbn}"> --%>
<!--                         </li> -->
<!--                         <li> -->
<!--                             <label for="beforeAfter">発売日</label> -->
<!--                             <div class="flexFormItem"> -->
<%--                                 <input type="date" id="date" name="date" value="${conditions.salesDate}"> --%>
<%--                                 <select id="beforeAfter" name="beforeAfter" data-value="${conditions.salesDateFlag}"> --%>
<!--                                     <option value="equals">に一致</option> -->
<!--                                     <option value="before">以前</option> -->
<!--                                     <option value="after">以降</option> -->
<!--                                 </select> -->
<!--                             </div> -->
<!--                         </li> -->
<!--                         <li> -->
<!--                             <label for="largeOrSmall">在庫数</label> -->
<!--                             <div class="flexFormItem"> -->
<%--                                 <input type="number" id="stock" name="stock" min="0" value="${conditions.stock}"> --%>
<!--                                 <span>冊</span> -->
<%--                                 <select id="largeOrSmall" name="largeOrSmall" data-value="${conditions.stockFlag}"> --%>
<!--                                     <option value="equals">に等しい</option> -->
<!--                                     <option value="ltoe">以下</option> -->
<!--                                     <option value="gtoe">以上</option> -->
<!--                                 </select> -->
<!--                             </div> -->
<!--                         </li> -->
<!--                     </ul> -->
                    <input type="hidden" name="form" value="0">
                    <input type="submit" id="searchButton" class="button" value="検索">
                </form>
            </div>
            <div id="error">
                <span>${sessionScope.error}</span>
            </div>
            <div class="list">
                <div class="pages">
                    <%@ include file="part/PageMover.jsp" %>
                </div>
                <div id="list">
                    <form id="sortForm" action="/Zaiko2020/inventoryList" method="post">
                        <input type="hidden" id="sortIndex" name="index" value="${conditions.sort}">
                        <input type="hidden" id="sortDirection" name="direction" value="${conditions.lift}">
                        <input type="hidden" name="form" value="2">
                    </form>
                    <table id="listTable">
                        <thead>
                            <tr id="listHeaders">
                                <th id="headerControl" class="headerFixed">
                                    操作
                                </th>
                                <th id="headerName" class="headerGrow2">
                                    書籍名
                                </th>
                                <th id="headerAuthor" class="headerGrow2">
                                    著者
                                </th>
                                <th id="headerPublisher" class="headerGrow2">
                                    出版社
                                </th>
                                <th id="headerSalesDate" class="listHeaderSortable headerFixed" data-sort-index="0">
                                    発売日
                                    <span class="sortArrows">
                                        <span class="sortArrow" title="発売日 昇順でソートします" data-sort-direction="1">
                                            ↑
                                        </span>
                                        <span class="sortArrow" title="発売日 降順でソートします" data-sort-direction="-1">
                                            ↓
                                        </span>
                                    </span>
                                </th>
                                <th id="headerIsbn" class="listHeaderSortable headerFixed" data-sort-index="1">
                                    ISBN
                                    <span class="sortArrows">
                                        <span class="sortArrow" title="ISBN 昇順でソートします" data-sort-direction="1">
                                            ↑
                                        </span>
                                        <span class="sortArrow" title="ISBN 降順でソートします" data-sort-direction="-1">
                                            ↓
                                        </span>
                                    </span>
                                </th>
                                <th id="headerPrice" class="headerGrow1">
                                    価格
                                </th>
                                <th id="headerStock" class="listHeaderSortable headerGrow1" data-sort-index="2">
                                    在庫数
                                    <span class="sortArrows">
                                        <span class="sortArrow" title="在庫数 昇順でソートします" data-sort-direction="1">
                                            ↑
                                        </span>
                                        <span class="sortArrow" title="在庫数 降順でソートします" data-sort-direction="-1">
                                            ↓
                                        </span>
                                    </span>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            if(items != null)
                            {
                                for(Book item : items){
                                %>
                            <tr>
                                <td class="dataControl dataCenter">
                                    <form action="/Zaiko2020/arrivalForm" method="post" class="formEditLink inline">
                                        <input type="hidden" name="id" value="<%=item.getId() %>">
                                        <span class="link buttonArrive">入荷</span>
                                    </form>
                                    <span>/</span>
                                    <form action="/Zaiko2020/shippingForm" method="post" class="formEditLink inline">
                                        <input type="hidden" name="id" value="<%=item.getId() %>">
                                        <span class="link buttonShip">出荷</span>
                                    </form>
                                    <br>
                                    <form action="/Zaiko2020/Edit" method="post" class="formEditLink inline">
                                        <input type="hidden" name="id" value="<%=item.getId() %>">
                                        <input type="hidden" name="button" value="編集">
                                        <span class="link buttonEdit">編集</span>
                                    </form>
                                </td>
                                <td class="dataName"><%=item.getName() %></td>
                                <td class="dataAuthor"><%=item.getAuthor() %></td>
                                <td class="dataPublisher"><%=item.getPublisher() %></td>
                                <td class="dataSalesDate dataCenter"><%=item.getSalesDate().format(dateFormat) %></td>
                                <td class="dataIsbn dataCenter"><%=item.getIsbn() %></td>
                                <td class="dataPrice dataRight"><%=item.getPrice() %> 円</td>
                                <!--数字をダブルクリックしたときの利便性を考慮しスペースを挿入-->
                                <td class="dataStock dataRight"><%=item.getStock() %> 冊</td>
                            </tr>
                            <%
                                }
                            }
                            %>
                        </tbody>
                    </table>
                </div>
                <div class="pages">
                    <%@ include file="part/PageMover.jsp" %>
                </div>
            </div>
        </div>
    </div>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="js/inventoryList.js"></script>
<script src="js/TabTransition.js"></script>
</html>
