<%@ page language="java" pageEncoding="UTF-8"%>
<%
//本の情報と入荷・出荷フォームを表示する。
//ArrivalForm.jspやShippingForm.jspからincludeして使う。
//入荷・出荷の表示切り替えは変数modeにより行う。
%>
<div id="main">
    <header>
        <span id="mode">${mode}</span>
    </header>
    <div id="error">
        <span>${sessionScope.error}</span>
    </div>
    <div id="details">
        <jsp:include page="BookDetails.jsp">
            <jsp:param value="caption" name=""/>
            <jsp:param value="book" name="${book }"/>
        </jsp:include>
    </div>
    <div id="inout">
        <form id="inoutForm" action="" method="post">
            <span>
                <label for="count">${mode}数</label>
                <input type="number" id="count" name="count" max="999999" min="1">
                <span class="unit">冊</span>
            </span>
            <br>
            <button id="execute" class="button button-main button-border">実行</button>
            <button id="cancel" class="button button-cancel button-border">キャンセル</button>
        </form>
    </div>
</div>
<dialog>
    <form id="dialogForm" action="/Zaiko2020/arrive" method="post">
        <p><span id="countConfirm" class="align-text"></span> 冊の${mode}処理を行います。</p>
        <input type="hidden" id="dialogCount" name="count">
        <input type="submit" class="button button-main" value="OK">
        <button id="dialogCancel" class="button button-cancel">キャンセル</button>
    </form>
</dialog>
