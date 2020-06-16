<%@page contentType="text/html" pageEncoding="UTF-8" session="false" %>
<ul class="pagesBox">
    <li class="pagesCounterWeight"></li>
    <li class="pagesSpace"></li>
    <li class="pagesPrevNext">
        <form name="prevPage" action="/Zaiko2020/inventoryList" method="post">
            <input type="hidden" name="form" value="1">
            <input type="hidden" name="page" value="${currentPage - 1}">
            <a href="javascript:prevPage.submit()">前へ</a>
        </form>
    </li>
    <li class="pagesJump">
        <form name="jumpPage" action="/Zaiko2020/inventoryList" method="post">
            <input type="hidden" name="form" value="1">
            <input type="number" name="page" id="page" min="1" max="${maxPage}" value="${currentPage}">
            <span>/${maxPage}</span>
            <input type="submit" class="button" value="移動">
        </form>
    </li>
    <li class="pagesPrevNext">
        <form name="prevPage" action="/Zaiko2020/inventoryList" method="post">
            <input type="hidden" name="form" value="1">
            <input type="hidden" name="page" value="${currentPage + 1}">
            <a href="javascript:prevPage.submit()">次へ</a>
        </form>
    </li>
    <li class="pagesSpace"></li>
    <li class="pagesItemCount">${count} 件</li>
</ul>
