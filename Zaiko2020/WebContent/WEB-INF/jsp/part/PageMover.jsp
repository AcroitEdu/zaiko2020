<%@ page language="java" pageEncoding="UTF-8"%>
<%//ページ移動の要素%>
<ul class="pagesBox">
    <li class="pagesCounterWeight"></li>
    <li class="pagesSpace"></li>
    <li class="pagesPrevNext">
        <form name="prevPage" action="/Zaiko2020/inventoryList" method="post">
            <input type="hidden" name="form" value="1">
            <input type="hidden" name="page" class="inputPage" value="${conditions.page - 1}" data-max="${maxPage}">
            <span class="link prevButton">前へ</span>
        </form>
    </li>
    <li class="pagesJump">
        <form name="jumpPage" action="/Zaiko2020/inventoryList" method="post">
            <input type="hidden" name="form" value="1">
            <input type="number" class="inutPageNumber" name="page" min="1" max="${maxPage}" value="${conditions.page}">
            <span>/ ${maxPage}</span>
            <input type="submit" class="button" value="移動">
        </form>
    </li>
    <li class="pagesPrevNext">
        <form name="nextPage" action="/Zaiko2020/inventoryList" method="post">
            <input type="hidden" name="form" value="1">
            <input type="hidden" name="page" class="inputPage" value="${conditions.page + 1}" data-max="${maxPage}">
            <span class="link nextButton">次へ</span>
        </form>
    </li>
    <li class="pagesSpace"></li>
    <li class="pagesItemCount">${count} 件</li>
</ul>
