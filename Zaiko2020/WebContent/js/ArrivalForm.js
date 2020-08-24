////入力チェック処理
////const checkValid = () => {
////    if ($("#count").val().search(/[^0-9]/) < 0) {
////        $("#count")[0].setCustomValidity("");
////    } else {
////        var value = parseInt($("#count").val().replace(/[^0-9].*/, ""));
////        $("#count")[0].setCustomValidity(`有効な値を入力してください。有効な値として最も近いのは${value}と${value + 1}です。`);
////    }
////    return $("#inoutForm")[0].reportValidity();
////};
//
////「実行」押下時
//$("#execute").click(function () {
////    if (checkValid()) {
//        $("#countConfirm").text(parseInt($("#count").val()));
//        $("#dialogSubmit")[0].showModal();
////    }
//});

//入力値チェック処理
const checkValid = () => {
	var value = String($("#count").val()).length;
    if (!$("#count").val()) {
        $("#count")[0].setCustomValidity(`このフィールドを入力してください。`);
    } else if (0 < $("#count").val().match(/^[\x20-\x7e]+$/) && value <= 6) {
    	$("#count")[0].setCustomValidity("");
    }else{
    	$("#count")[0].setCustomValidity(`指定されている形式で入力してください。`);
    }
    return $("#inoutForm")[0].reportValidity();
};

//「実行」押下時
$("#execute").click(function () {
    if (checkValid()) {
        $("#countConfirm").text(parseInt($("#count").val()));
//        $("#dialogSubmit")[0].showModal();
        $("#overlay, #modal").addClass("active");
    }
});

//ダイアログ内「キャンセル」押下時
$("#dialogCancel").click(function () {
//    $("#dialogSubmit")[0].close();
    $("#overlay, #modal").removeClass("active");
});

//ダイアログ内「実行」押下時
$("#dialogExecute").click(function () {
    $("#inoutForm")[0].submit();
});

//エンターキー無効化
$(function(){
    $("input"). keydown(function(e) {
        if ((e.which && e.which === 13) || (e.keyCode && e.keyCode === 13)) {
            return false;
        } else {
            return true;
        }
    });
});