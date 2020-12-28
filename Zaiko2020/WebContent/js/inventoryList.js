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

//画面をスクロールした時の処理（スマホ画面のみ）
$(window).scroll(function () {
var now = $(window).scrollTop();
if (now > 200) {
  $('.pagetop').fadeIn("slow");
} else {
  $('.pagetop').fadeOut('slow');
}
});