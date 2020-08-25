<%@ page language="java" pageEncoding="UTF-8"%>
<%
//「次へ」や「前へ」、「移動」等の、ページ移動の機能を持つ。
//InventoryList.jspからincludeして使う。
%>
<ul class="pagesBox">
    <li id="pcPage" class="pagesCounterWeight"></li>
    <li id="pcPage"  class="pagesSpace"></li>
    <li class="pagesPrevNext">
        <form name="prevPage" action="/Zaiko2020/inventoryList" method="post">
            <input type="hidden" name="form" value="1">
            <input type="hidden" class="inputPage" name="page" value="${conditions.page - 1}" data-max="${maxPage}">
            <span class="link prevButton">前へ</span>
        </form>
    </li>
    <li id="pcPage" class="pagesJump">
        <form name="jumpPage" action="/Zaiko2020/inventoryList" method="post">
            <input type="hidden" name="form" value="1">
            <span>
<%--                 <input type="number" class="inutPageNumber" name="page" required min="1" max="${maxPage}" --%>
<%--                     value="${conditions.page}"> --%>
<%-- 				<input type="text" class="inutPageNumber" name="page" required pattern="^[1-9][0-9]*$" maxlength="9" value="${conditions.page}"> --%>
				<input type="tel" class="inutPageNumber" name="page" required pattern="^[1-9][0-9]*$" maxlength="9" value="${conditions.page}">
                <span class="pageDelimiter">/</span>
                <span class="maxPage">${maxPage}</span>
            </span>
            <input type="submit" class="button" value="移動">
        </form>
    </li>


    <li id="sumahoPage" class="pagesJump">
        <span>${conditions.page}</span>
        <span>　/　</span>
        <span>${maxPage}</span>
    </li>


    <li class="pagesPrevNext">
        <form name="nextPage" action="/Zaiko2020/inventoryList" method="post">
            <input type="hidden" name="form" value="1">
            <input type="hidden" class="inputPage" name="page" value="${conditions.page + 1}" data-max="${maxPage}">
            <span class="link nextButton">次へ</span>
        </form>
    </li>
    <li id="pcPage" class="pagesSpace"></li>
    <li id="pcPage" class="pagesItemCount">${count} 件</li>
</ul>



<!-- <div> -->

<!-- <ul class="pagesBox"> -->
<!--     <li class="pagesCounterWeight"></li> -->
<!--     <li class="pagesSpace"></li> -->
<!--     <li class="pagesPrevNext"> -->
<!--         <form name="prevPage" action="/Zaiko2020/inventoryList" method="post"> -->
<!--             <input type="hidden" name="form" value="1"> -->
<%--             <input type="hidden" class="inputPage" name="page" value="${conditions.page - 1}" data-max="${maxPage}"> --%>
<!--             <span class="link prevButton">前へ</span> -->
<!--         </form> -->
<!--     </li> -->
<!--     <li class="pagesJump"> -->
<%--         <span>${conditions.page}</span> --%>
<!--         <span>　/　</span> -->
<%--         <span>${maxPage}</span> --%>
<!--     </li> -->
<!--     <li class="pagesPrevNext"> -->
<!--         <form name="nextPage" action="/Zaiko2020/inventoryList" method="post"> -->
<!--             <input type="hidden" name="form" value="1"> -->
<%--             <input type="hidden" class="inputPage" name="page" value="${conditions.page + 1}" data-max="${maxPage}"> --%>
<!--             <span class="link nextButton">次へ</span> -->
<!--         </form> -->
<!--     </li> -->
<!--     <li class="pagesSpace"></li> -->
<%--     <li class="pagesItemCount">${count} 件</li> --%>
<!-- </ul> -->


<!-- </div> -->

