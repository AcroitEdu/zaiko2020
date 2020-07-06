//「実行」押下時
$("#execute").click(function () {
    if ($("#inoutForm")[0].reportValidity()) {
        $("#countConfirm").text($("#count").val());
        $("#dialogSubmit")[0].showModal();
    }
});

//ダイアログ内「キャンセル」押下時
$("#dialogCancel").click(function () {
    $("#dialogSubmit")[0].close();
});

//ダイアログ内「実行」押下時
$("#dialogExecute").click(function () {
    $("#inoutForm")[0].submit();
});
