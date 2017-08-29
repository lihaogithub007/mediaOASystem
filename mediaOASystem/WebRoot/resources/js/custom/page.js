// 表单方式分页
function gotoPage(n) {
	jQuery("#currentPage").val(n);
	jQuery("#listForm").submit();
}
