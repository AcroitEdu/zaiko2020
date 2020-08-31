//postリクエストを送信するリンク(「次へ」等)の処理
function linkSubmit() {
    if ($(this).hasClass("disabled")) return;
    var form = $(this).parent();
    form.submit();
}

//「次へ」及び「前へ」に対する範囲チェックと範囲外の操作の無効化
function linkDetectOutOfRange(index, element) {
    var input = $(element).parent().find(".inputPage");
    var value = parseInt(input.val());
    if (value < 1 || value > parseInt(input.attr("data-max"))) {
        $(element).addClass("disabled");
    }
}

var sortArrows = $(".sortArrow");
//ソート欄の矢印をクリックした時の処理
sortArrows.click(function () {
    var sorting = $(this).parents(".listHeaderSortable");
    var index = sorting.attr("data-sort-index");
    var direction = $(this).attr("data-sort-direction");
    $("#sortIndex").val(index);
    $("#sortDirection").val(direction);
    $("#sortForm").submit();
});

//「次へ」をクリックした時の処理
var nextButtons = $(".nextButton");
nextButtons.click(linkSubmit);
//次のページが存在しないときにボタンを無効化する処理
nextButtons.each(linkDetectOutOfRange);

//「前へ」をクリックした時の処理
var prevButtons = $(".prevButton");
prevButtons.click(linkSubmit);
//前のページが存在しないときにボタンを無効化する処理
prevButtons.each(linkDetectOutOfRange);

//select要素のデフォルト選択項目の設定(検索条件の保持)
var selects = $("#beforeAfter, #largeOrSmall");
selects.each(function (index, element) {
    var elem = $(element);
    var value = elem.attr("data-value");
    elem.find(`option[value='${value}']`).prop("selected", true);
});

var selects = $("#sortItem, #sortOrder");
selects.each(function (index, element) {
    var elem = $(element);
    var value = elem.attr("data-value");
    elem.find(`option[value='${value}']`).prop("selected", true);
});

//ソート矢印をソート設定に基づきハイライトする処理
var sortIndex = $("#sortIndex").val();
var sortDirection = $("#sortDirection").val();
var sortableHeaders = $(`#listHeaders > .listHeaderSortable[data-sort-index='${sortIndex}'] > .sortArrows > .sortArrow[data-sort-direction='${sortDirection}']`);
sortableHeaders.addClass("sortArrowActive");

//「入荷」をクリックしたときの処理
$(".buttonArrive").click(linkSubmit);

//「出荷」をクリックしたときの処理
$(".buttonShip").click(linkSubmit);

//「編集」をクリックしたときの処理
$(".buttonEdit").click(linkSubmit);




$(window).scroll(function () {
  var now = $(window).scrollTop();
  if (now > 200) {
    $('.pagetop').fadeIn("slow");
  } else {
    $('.pagetop').fadeOut('slow');
  }
});



//■page topボタン

//$(function(){
//var topBtn=$('#pageTop');
//topBtn.hide();
//
//
//
////◇ボタンの表示設定
//$(window).scroll(function(){
//  if($(this).scrollTop()>80){
//
//    //---- 画面を80pxスクロールしたら、ボタンを表示する
//    topBtn.fadeIn();
//
//  }else{
//
//    //---- 画面が80pxより上なら、ボタンを表示しない
//    topBtn.fadeOut();
//
//  }
//});
//
//
//
//// ◇ボタンをクリックしたら、スクロールして上に戻る
//topBtn.click(function(){
//  $('body,html').animate({
//  scrollTop: 0},500);
//  return false;
//
//});
//
//
//});