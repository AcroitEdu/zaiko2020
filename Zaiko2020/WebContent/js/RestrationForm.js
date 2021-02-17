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

//「選択した書籍の復元」押下時
$(".singleBookEcxecute").click(function () {
//        $("#dialogSubmit")[0].showModal();
        $("#overlay, #modal2").addClass("active");
});

//ダイアログ内「キャンセル」押下時
$("#dialogCancel2").click(function () {
//  $("#dialogSubmit")[0].close();
    $("#overlay, #modal2").removeClass("active");
});

//ダイアログ内「実行」押下時
$("#dialogExecute2").click(function () {
    $("#Restoration").submit();
});

//チェックボックスの全選択・解除
//初期状態・全選択時にマウスホバーで表示される文字列
var onMessage = '全解除';
var offMessage = '全選択';

//処理
$("#allAction").click(function () {
    var text = $(this).attr('title');
    console.log(text);
    if (text == onMessage) {
        $("#allAction").attr('title',offMessage);
        console.log('check01');
        $('.allChecked').prop('checked', false);
        console.log('check02');
    }
    else {
        $("#allAction").attr('title',onMessage);
        $('.allChecked').prop('checked', true);
    }
});
