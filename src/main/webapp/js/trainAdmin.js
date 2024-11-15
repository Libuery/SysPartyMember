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
        url: `../train/pageAdmin?page=${page}&size=${size}`,
        type: 'get',
        dataType: 'json',
        success: function (result) {
            if (result.code === 200) {
                let data = result.data.data
                let tableBody = $('#trainsTable tbody');
                tableBody.empty();
                if (data && data.length !== 0) {
                    data.forEach(data => {
                        let status = data.status
                        if (status === 0) {
                            tableBody.append(`
                                <tr>
                                    <td>${data.id}</td>
                                    <td>${data.name}</td>
                                    <td>${data.term}</td>
                                    <td>${data.count}</td>
                                    <td>${data.duration}</td>
                                    <td>${data.score}</td>
                                    <td>
                                    <button onclick="window.location.href = 'updateTrain.html?id=${data.id}'">修改</button>
                                    <button onclick="updateTrain(${data.id}, 1)" style="background-color: red">禁用</button>
                                    </td>
                                </tr>    
                                `)
                        } else if (status === 1) {
                            tableBody.append(`
                                <tr>
                                    <td>${data.id}</td>
                                    <td>${data.name}</td>
                                    <td>${data.term}</td>
                                    <td>${data.count}</td>
                                    <td>${data.duration}</td>
                                    <td>${data.score}</td>
                                    <td>
                                    <button onclick="window.location.href = 'updateTrain.html?id=${data.id}'">修改</button>
                                    <button onclick="updateTrain(${data.id}, 0)" style="background-color: green">启用</button>
                                    </td>
                                </tr>    
                                `)
                        } else {
                            tableBody.append(`
                                <tr>
                                    <td>${data.id}</td>
                                    <td>${data.name}</td>
                                    <td>${data.term}</td>
                                    <td>${data.count}</td>
                                    <td>${data.duration}</td>
                                    <td>${data.score}</td>
                                    <td>
                                    <button onclick="window.location.href = 'updateTrain.html?id=${data.id}'">修改</button>
                                    <button>出错了~</button>
                                    </td>
                                </tr>    
                                `)
                        }

                    });
                } else {
                    tableBody.append(`<tr><td colspan="7">暂无数据</td></tr>`)
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

function deleteTrain(id) {
    let flag = confirm("确认删除？")
    if (flag) {
        $.ajax({
            url: `../train/delete/${id}`,
            method: 'GET',
            dataType: 'json',
            success: function (result) {
                if (result.code === 200) {
                    alert("操作成功");
                    // 重新加载数据
                    loadData(currentPage, pageSize);
                }else {
                    alert(result.msg)
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

function updateTrain(id, status) {
    $.ajax({
        url: `../train/updateStatus/${id}/${status}`,
        method: 'GET',
        dataType: 'json',
        success: function (result) {
            if (result.code === 200) {
                if (status === 0) {
                    alert("启用成功")
                } else {
                    alert("禁用成功")
                }
                // 重新加载数据
                loadData(currentPage, pageSize);
            }else {
                alert(result.msg)
            }
        },
        error: function () {
            alert("网络异常");
        }
    })
}