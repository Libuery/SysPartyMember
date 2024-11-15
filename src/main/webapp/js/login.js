function login() {
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "../user/login",
        data: $('#form1').serialize(),
        success: function (result) {
            if (result.code === 200) {
                alert("登录成功");
                if (result.data === 'admin') {
                    window.location.href = 'admin.html';
                } else {
                    window.location.href = 'index.html';
                }
            } else {
                alert(result.msg);
            }

        },
        error: function () {
            alert("系统异常");
        }
    });
}