function linkSubmit() {
    var form = $(this).parent();
    form.submit();
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
var prevButtons = $(".prevButton");
prevButtons.click(linkSubmit);
