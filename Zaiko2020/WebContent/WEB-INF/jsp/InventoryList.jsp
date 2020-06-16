<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="java.util.List"%>
<%@ page import="jp.co.acroit.zaiko2020.book.Book" %>
<%
var currentPage = (int)session.getAttribute("page");
var maxPage = (int)session.getAttribute("maxPage");
var count = (int)session.getAttribute("count");
var items = (List<Book>)session.getAttribute("items");
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>在庫一覧</title>
</head>

<body>
    <div id="main">
        <header>
            <ul class="boxed-tabs">
                <li class="tab-current tab">在庫一覧</li>
                <li class="tab">追加</li>
                <li class="tab-disabled tab"></li>
                <li id="tab-logout tab">ログアウト</li>
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
                    <input type="submit" class="button" value="検索">
                </form>
            </div>
            <div id="error"></div>
            <div class="list">
                <div class="pages">
                    <ul class="pagesBox">
                        <li class="pagesCounterWeight"></li>
                        <li class="pagesSpace"></li>
                        <li>
                            <form name="prevPage" action="/Zaiko2020/inventoryList" method="post">
                                <input type="hidden" name="page" value="${currentPage - 1}">
                                <a href="javascript:prevPage.submit()">前へ</a>
                            </form>
                        </li>
                        <li>
                            <form name="jumpPage" action="/Zaiko2020/inventoryList" method="post">
                                <input type="number" name="page" id="page" min="1" max="${maxPage}"
                                    value="${currentPage}">
                                <span>/${maxPage}</span>
                                <input type="submit" class="button" value="移動">
                            </form>
                        </li>
                        <li>
                            <form name="prevPage" action="/Zaiko2020/inventoryList" method="post">
                                <input type="hidden" name="page" value="${currentPage + 1}">
                                <a href="javascript:prevPage.submit()">次へ</a>
                            </form>
                        </li>
                        <li class="pagesSpace"></li>
                        <li class="pagesItemCount">${count} 件</li>
                    </ul>
                </div>
                <div id="list">
                    <table id="listTable">
                        <tr>
                            <th id="headerControl">操作</th>
                            <th id="headerName">書籍名</th>
                            <th id="headerAuthor">著者</th>
                            <th id="headerPublisher">出版社</th>
                            <th id="headerSalesDate" class="listHeaderSortable">発売日</th>
                            <th id="headerIsbn" class="listHeaderSortable">ISBN</th>
                            <th id="headerPrice">価格</th>
                            <th id="headerStock" class="listHeaderSortable">在庫数</th>
                        </tr>
                        <%for(var item : items){%>
                        <tr>
                            <td class="dataControl">
                                <a href="#">入荷</a>
                                <span>/</span>
                                <a href="#">出荷</a><br />
                                <a href="#">編集</a>
                            </td>
                            <td class="dataName">${item.name}</td>
                            <td class="dataAuthor">${item.author}</td>
                            <td class="dataPublisher">${item.publisher}</td>
                            <td class="dataSalesDate">${item.salesDate}</td>
                            <td class="dataIsbn">${item.isbn}</td>
                            <td class="dataPrice">${item.price}</td>
                            <td class="dataStock">${item.stock}</td>
                        </tr>
                        <%}%>
                    </table>
                </div>
                <div class="pages">
                    <ul class="pagesBox">
                        <li class="pagesCounterWeight"></li>
                        <li class="pagesSpace"></li>
                        <li>
                            <form name="prevPage" action="/Zaiko2020/inventoryList" method="post">
                                <input type="hidden" name="page" value="${currentPage - 1}">
                                <a href="javascript:prevPage.submit()">前へ</a>
                            </form>
                        </li>
                        <li>
                            <form name="jumpPage" action="/Zaiko2020/inventoryList" method="post">
                                <input type="number" name="page" id="page" min="1" max="${maxPage}"
                                    value="${currentPage}">
                                <span>/${maxPage}</span>
                                <input type="submit" class="button" value="移動">
                            </form>
                        </li>
                        <li>
                            <form name="nextPage" action="/Zaiko2020/inventoryList" method="post">
                                <input type="hidden" name="page" value="${currentPage + 1}">
                                <a href="javascript:nextPage.submit()">次へ</a>
                            </form>
                        </li>
                        <li class="pagesSpace"></li>
                        <li class="pagesItemCount">${count} 件</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
