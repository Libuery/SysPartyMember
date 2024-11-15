let currentPage = 1;  // 当前页
let pageSize = 5;   // 每页数据条数

$(function () {
    $("#header-placeholder").load("header.html");
});

$(document).ready(function () {
    loadData(currentPage, pageSize);
});

function loadData(page, size) {
    $.ajax({
        url: `../student/page?page=${page}&size=${size}`,
        type: 'get',
        dataType: 'json',
        success: function (result) {
            if (result.code === 200) {
                let data = result.data.data
                let tableBody = $('#studentsTable tbody');
                tableBody.empty();
                if (data && data.length !== 0) {
                    data.forEach(student => {
                        let statusStr = "未知";
                        if (student.status === 0) {
                            statusStr = "普通学生"
                        } else if (student.status === 1) {
                            statusStr = "积极分子"
                        } else if (student.status === 2) {
                            statusStr = "预备党员"
                        } else if (student.status === 3) {
                            statusStr = "党员"
                        }
                        tableBody.append(`
                        <tr>
                            <td>${student.id}</td>
                            <td>${student.name}</td>
                            <td>${student.sex}</td>
                            <td>${student.clazz}</td>
                            <td>${student.phone}</td>
                            <td>${student.department}</td>
                            <td>${student.major}</td>
                            <td>${student.identity}</td>
                            <td>${statusStr}</td>
                            <td>${student.totalScore}</td>
                            <td>
                            <button onclick="window.location.href = 'update.html?id=${student.id}'">修改</button>
                            <button onclick="deleteStudent(${student.id})">删除</button>
                            </td>
                        </tr>    
                    `)
                    });
                } else {
                    tableBody.append(`<tr><td colspan="10">暂无数据</td></tr>`)
                }

                // 显示总条数
                $('#totalCount').html(`共 ${result.data.total} 条数据`);

                // 生成分页按钮
                generatePagination(result.data.total, result.data.page, result.data.size);
            }else {
                alert(result.msg)
            }
        }
    });
}

function deleteStudent(id) {
    let flag = confirm("确认删除？")
    if (flag) {
        $.ajax({
            url: `../student/delete/${id}`,
            method: 'GET',
            dataType: 'json',
            success: function (result) {
                if (result.code === 200) {
                    alert("操作成功");
                    loadData(currentPage, pageSize);
                } else {
                    alert(result.msg);
                }

            },
            error: function () {
                alert("网络异常");
            }
        })
    } else {
        alert("取消成功")
    }

}
