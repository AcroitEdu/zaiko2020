//入力チェック処理
const checkValid = () => {
    if ($("#count").val().search(/[^0-9]/) < 0) {
        $("#count")[0].setCustomValidity("");
    } else {
        var value = parseInt($("#count").val().replace(/[^0-9].*/, ""));
        $("#count")[0].setCustomValidity(`有効な値を入力してください。有効な値として最も近いのは${value}と${value + 1}です。`);
    }
    return $("#inoutForm")[0].reportValidity();
};

//「実行」押下時
$("#execute").click(function () {
    if (checkValid()) {
        var count = parseInt($("#count").val());
        $("#countConfirm").text(count);
        $("#countHidden").val(count);
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
