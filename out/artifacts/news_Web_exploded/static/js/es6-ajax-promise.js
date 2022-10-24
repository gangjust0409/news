function request(url,commitType,data) {
    return new Promise(function (resolve,reject) {
        $.ajax({
            url: "http://localhost:8080/news/"+url,
            dataType:"json",
            data: data,
            success: function (res) {
                resolve(res)
            },
            error:function (err) {
                reject(err)
            }
        });
    });
}