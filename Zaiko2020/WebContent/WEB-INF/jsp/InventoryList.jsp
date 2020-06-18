<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jp.co.acroit.zaiko2020.book.Book" %>
<%@ page import="java.util.List" %>
<%
//HttpSession session = request.getSession();
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
List<Book> items = (List<Book>)session.getAttribute("items");
%>
<!DOCTYPE html>
<html lang='ja'>

<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <meta http-equiv='X-UA-Compatible' content='ie=edge'>
    <title>在庫一覧</title>
    <link href="https://unpkg.com/sanitize.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="styleInventoryList.css">
</head>

<body>
    <div id="main">
        <header>
            <ul class="boxed-tabs">
                <li class="tab-current tab">在庫一覧</li>
                <li class="tab">追加</li>
                <li class="tab-space tab"></li>
                <li class="tab-logout tab">ログアウト</li>
            </ul>
        </header>
        <div class="content">
            <div class="search-options">
                <form name="searchOptions" action="/Zaiko2020/inventoryList" method="post">
                    <ul id="flexFormWrappable">
                        <li>
                            <label for="bookName">書籍名</label>
                            <input class="flexFormItem" type="text" name="name" id="bookName">
                        </li>
                        <li>
                            <label for="author">著者</label>
                            <input class="flexFormItem" type="text" name="author" id="author">
                        </li>
                        <li>
                            <label for="publisher">出版社</label>
                            <input class="flexFormItem" type="text" name="publisher" id="publisher">
                        </li>
                        <li>
                            <label for="isbn">ISBN</label>
                            <input class="flexFormItem" type="number" name="isbn" id="isbn">
                        </li>
                        <li>
                            <label for="beforeAfter">発売日</label>
                            <div class="flexFormItem">
                                <input type="date" name="date" id="date">
                                <select name="beforeAfter" id="beforeAfter">
                                    <option value="unspecified">指定なし</option>
                                    <option value="equals">に一致</option>
                                    <option value="before">以前</option>
                                    <option value="after">以降</option>
                                </select>
                            </div>
                        </li>
                        <li>
                            <label for="largeOrSmall">発売日</label>
                            <div class="flexFormItem">
                                <input type="number" name="stock" id="stock" min="0">
                                <span>冊</span>
                                <select name="largeOrSmall" id="largeOrSmall">
                                    <option value="unspecified">指定なし</option>
                                    <option value="lt">未満</option>
                                    <option value="ltoe">以下</option>
                                    <option value="equals">に等しい</option>
                                    <option value="gtoe">以上</option>
                                    <option value="gt">より多い</option>
                                </select>
                            </div>
                        </li>
                    </ul>
                    <input type="hidden" name="form" value="0">
                    <input id="searchButton" type="submit" class="button" value="検索">
                </form>
            </div>
            <div id="error"><span>${sessionScope.error}</span></div>
            <div class="list">
                <div class="pages">
                    <%@ include file="part/PageMover.jsp" %>
                </div>
                <div id="list">
                    <form id="sortForm" action="/Zaiko2020/inventoryList" method="post">
                        <input type="hidden" id="sortIndex" name="index" value="0">
                        <input type="hidden" id="sortDirection" name="direction" value="0">
                        <input type="hidden" name="form" value="2">
                    </form>
                    <table id="listTable">
                        <thead>
                            <tr>
                                <th id="headerControl" class="headerFixed headerFit">
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
                                        <span title="発売日 昇順でソートします" class="sortArrow" data-sort-direction="1">
                                            ↑</span>
                                        <span title="発売日 降順でソートします" class="sortArrow sortArrowActive"
                                            data-sort-direction="-1">
                                            ↓</span>
                                    </span>
                                </th>
                                <th id="headerIsbn" class="listHeaderSortable headerFixed headerFit"
                                    data-sort-index="1">
                                    ISBN
                                    <span class="sortArrows">
                                        <span title="ISBN 昇順でソートします" class="sortArrow" data-sort-direction="1">
                                            ↑</span>
                                        <span title="ISBN 降順でソートします" class="sortArrow" data-sort-direction="-1">
                                            ↓</span>
                                    </span>
                                </th>
                                <th id="headerPrice" class="headerFit headerGrow1">
                                    価格
                                </th>
                                <th id="headerStock" class="listHeaderSortable headerFit headerGrow1"
                                    data-sort-index="2">
                                    在庫数
                                    <span class="sortArrows">
                                        <span title="在庫数 昇順でソートします" class="sortArrow" data-sort-direction="1">
                                            ↑</span>
                                        <span title="在庫数 降順でソートします" class="sortArrow" data-sort-direction="-1">
                                            ↓</span>
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
                                <td class="dataControl">
                                    <a href="#">入荷</a>
                                    <span>/</span>
                                    <a href="#">出荷</a><br />
                                    <a href="#">編集</a>
                                </td>
                                <td class="dataName"><%=item.getName()%></td>
                                <td class="dataAuthor"><%=item.getAuthor() %></td>
                                <td class="dataPublisher"><%=item.getPublisher() %></td>
                                <td class="dataSalesDate"><%=item.getSalesDate() %></td>
                                <td class="dataIsbn"><%=item.getIsbn() %></td>
                                <td class="dataPrice"><%=item.getPrice() %></td>
                                <td class="dataStock"><%=item.getStock() %></td>
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
<script src="js/invenoryList.js"></script>

</html>
