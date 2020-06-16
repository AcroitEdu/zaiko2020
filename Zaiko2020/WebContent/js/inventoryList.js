var sortable = $(".sortArrow");
sortable.click(function () {    //矢印をクリックした
    var sorting = this.parents(".listHeaderSortable");
    var index = sorting.attr("data-sort-index");
    var direction = this.attr("data-sort-direction");
    $("#sortIndex").val(index);
    $("#sortDirection").val(direction);
    $("#sortForm").submit();
});
