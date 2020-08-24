//「選択した書籍の復元」押下時
$(".execute").click(function () {
//        $("#dialogSubmit")[0].showModal();
        $("#overlay, #modal").addClass("active");
});

//ダイアログ内「キャンセル」押下時
$("#dialogCancel").click(function () {
//  $("#dialogSubmit")[0].close();
    $("#overlay, #modal").removeClass("active");
});

//ダイアログ内「実行」押下時
$("#dialogExecute").click(function () {
    $("#check").submit();
});