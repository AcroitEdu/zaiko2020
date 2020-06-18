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
var sortable = $(".sortArrow");
sortable.click(function () {    //矢印をクリックした
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
