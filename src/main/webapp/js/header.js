$(document).ready(function () {
    $.get("../user/loginStatus", function (result) {
        let data = result.data
        if (data.loggedIn) {
            if (data.admin) {
                let categoriesContent = '<ul>' +
                    '<li><a href="../html/admin.html">学生信息管理</a></li> | ' +
                    '<li><a href="../html/trainAdmin.html">培训计划管理</a></li> | ' +
                    '<li><a href="../html/applyAdmin.html">申请审核</a></li></ul>';
                $("#categories").html(categoriesContent); // 将构建的列表添加到页面中

                $("#login").html(
                    `<ul style="float: right">
                     <li>欢迎您: <span style="color: red">管理员</span></li> | 
                     <li><a href="#" onclick="logout()">退出登录</a></li>
                 </ul>
                `
                );
            }else {
                let categoriesContent = '<ul>' +
                    '<li><a href="../html/index.html">主页</a></li> | ' +
                    '<li><a href="../html/train.html">参加培训</a></li> | ' +
                    '<li><a href="../html/apply.html">我的申请</a></li></ul>';
                $("#categories").html(categoriesContent); // 将构建的列表添加到页面中

                $("#login").html(
                    `<ul style="float: right">
                     <li>欢迎您: <span style="color: red">${data.username}</span></li> | 
                     <li><a href="../html/studentInfo.html"">我的信息</a></li> | 
                     <li><a href="#" onclick="logout()">退出登录</a></li>
                 </ul>
                `
                );
            }
        } else {
            $("#login").html(
                `<ul style="float: right">
                     <li><a href="login.html">登录 |</a></li>
                     <li><a href="register.html">注册</a></li>
                </ul>`
            );
        }
    });
})

function logout() {
    let flag = confirm("是否确定退出登录？")
    if (flag) {
        $.ajax({
            //几个参数需要注意一下
            type: "GET",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: "../user/logout" ,//url
            success: function (result) {
                if (result.code === 200) {
                    alert("退出成功");
                    window.location.href = 'login.html';
                }else {
                    alert("操作失败");
                }
            },
            error: function() {
                alert("网络异常");
            }
        });
    }
}