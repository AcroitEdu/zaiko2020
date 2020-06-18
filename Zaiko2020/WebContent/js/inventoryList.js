function linkSubmit() {
    if ($(this).hasClass("disabled")) return;
    var form = $(this).parent();
    form.submit();
}
function linkDetectOutOfRange(index, element) {
    var input = $(element).parent().find(".inputPage");
    if (input.val() < 1 || input.val() > input.attr("data-max")) {
        $(element).addClass("disabled");
    }
}
var sortArrows = $(".sortArrow");
sortArrows.click(function () {    //矢印をクリックした
    var sorting = $(this).parents(".listHeaderSortable");
    var index = sorting.attr("data-sort-index");
    var direction = $(this).attr("data-sort-direction");
    $("#sortIndex").val(index);
    $("#sortDirection").val(direction);
    $("#sortForm").submit();
});
var nextButtons = $(".nextButton");
nextButtons.click(linkSubmit);
nextButtons.each(linkDetectOutOfRange);
var prevButtons = $(".prevButton");
prevButtons.click(linkSubmit);
prevButtons.each(linkDetectOutOfRange);
var selects = $("#beforeAfter, #largeOrSmall");
selects.each(function (index, element) {
    var elem = $(element);
    var value = elem.attr("data-value");
    elem.find(`option[value='${value}']`).prop("selected", true);
});
var sortIndex = $("#sortIndex").val();
var sortDirection = $("#sortDirection").val();
var sortableHeaders = $(`#listHeaders > .listHeaderSortable[data-sort-index='${sortIndex}'] > .sortArrows > .sortArrow[data-sort-direction='${sortDirection}']`);
sortableHeaders.addClass("sortArrowActive");
