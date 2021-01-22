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
    } else if (0 < $("#count").val().match(/^[\x20-\x7e]+$/) && value <= 10) {
    	$("#count")[0].setCustomValidity("");
    }else{
    	$("#count")[0].setCustomValidity(`指定されている形式で入力してください。`);
    }
    return $("#inoutForm")[0].reportValidity();
};

//入力文字制限(半角英数字のみ許容)
var totalInputData = "";
var limitedDigits = 10;
var messagePlace = $('#inputErrorMessage');
//在庫数の入力値チェック
$(function(){
  $('input[name="count"]').on('input', function(){
    checkNumberTypeLimit($(this));
  });
});

function checkNumberTypeLimit(obj){
    var inputValue = $(obj).val();
    console.log(inputValue);
    console.log(totalInputData);
    var valueLength = inputValue.length;
    // ３．入力した文字が半角数字かどうかチェック
    if(inputValue.match(/^[0-9]+$/)){
         if(valueLength > limitedDigits){
              $(obj).val(totalInputData);
         }else{
              totalInputData = inputValue;
              messagePlace.text("非表示中");
              messagePlace.css("visibility","hidden");
         }
    }else{
         if(valueLength == 0){
              totalInputData = "";
         }else{
              $(obj).val(totalInputData);
              $('#inputErrorMessage').text("半角数字のみが入力できます。");
              messagePlace.css("visibility","visible");
         }
    }
}

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
    $(this).prop("disabled",true); //二度押し防止
//    $("#inoutForm")[0].submit();
    $("#inoutForm").submit();
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