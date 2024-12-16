function getQueryVariable(variable) {
    let query = window.location.search.substring(1);
    let vars = query.split('&');
    for (let i = 0; i < vars.length; i++) {
        let pair = vars[i].split('=');
        if (decodeURIComponent(pair[0]) === variable) {
            return decodeURIComponent(pair[1]);
        }
    }
    console.log('Query variable %s not found', variable);
}

$(document).ready(function () {
    let id = getQueryVariable('id');
    if (id) {
        $.ajax({
            url: `../student/find/${id}`,
            type: 'GET',
            dataType: 'json',
            success: function (result) {
                if (result.code === 200) {
                    let data = result.data
                    $('#id').val(data.id);
                    $('#studentName').val(data.name);
                    $('#sex').val(data.sex);
                    $('#class').val(data.clazz);
                    $('#phone').val(data.phone);
                    $('#department').val(data.department);
                    $('#major').val(data.major);
                    $('#identity').val(data.identity);
                    $('#partyStage').val(data.status);
                }else {
                    alert(result.msg)
                }
            }
        })
    }
})