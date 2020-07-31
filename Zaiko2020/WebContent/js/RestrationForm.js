//「選択した書籍の復元」押下時
$(".execute").click(function () {
        $("#dialogSubmit")[0].showModal();
});

//ダイアログ内「キャンセル」押下時
$("#dialogCancel").click(function () {
    $("#dialogSubmit")[0].close();
});

//ダイアログ内「実行」押下時
$("#dialogExecute").click(function () {
    $("#check")[0].submit();
});