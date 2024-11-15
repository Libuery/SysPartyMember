// 生成分页按钮
function generatePagination(totalItems, currentPage, pageSize) {
    let totalPages = Math.ceil(totalItems / pageSize);  // 计算总页数
    let pagination = $('#pagination');
    pagination.empty(); // 清空分页按钮

    // 创建上一页按钮
    if (currentPage > 1) {
        pagination.append(`<button onclick="goToPage(${currentPage - 1})">上一页</button>`);
    }

    // 创建页码按钮
    for (let i = 1; i <= totalPages; i++) {
        if (i === currentPage) {
            pagination.append(`<button class="active">${i}</button>`);
        } else {
            pagination.append(`<button onclick="goToPage(${i})">${i}</button>`);
        }
    }

    // 创建下一页按钮
    if (currentPage < totalPages) {
        pagination.append(`<button onclick="goToPage(${currentPage + 1})">下一页</button>`);
    }
}

// 分页跳转
function goToPage(page) {
    currentPage = page;
    loadData(currentPage, pageSize);  // 加载对应页的数据
}

// 每页显示数量的改变
function changePageSize() {
    pageSize = parseInt($('#pageSize').val());  // 获取选择的每页显示条数
    loadData(1, pageSize);  // 重新加载数据并从第一页开始
}