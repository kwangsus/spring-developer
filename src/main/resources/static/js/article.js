const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;

        function success() {
            alert("삭제 성공");
            location.replace("/articles");
        }

        function fail() {
            alert("삭제 실패");
            location.replace("/articles");
        }

        httpRequest("DELETE", "/api/articles/" + id, null, success, fail);

        /*fetch(`/api/articles/${id}`, {
            method: 'delete'
        }).then(() => {
            alert("삭제완");
            location.replace('/articles');
        });*/
    });
}

const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        });

        function success() {
            alert("수정 완료");
            location.replace("/articles");
        }

        function fail() {
            alert("수정 실해");
            location.replace("/articles");
        }

        httpRequest("PUT", `/api/articles/${id}`, body, success, fail);

        /*fetch(`/api/articles/${id}`, {
            method: 'put',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        }).then(() => {
            alert("수정완");
            location.replace(`/articles/${id}`);
        })*/
    });
}

const createButton = document.getElementById("create-btn");
if (createButton) {
    createButton.addEventListener("click", event => {
        body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        });

        function success() {
            alert("등록 완료");
            location.replace("/articles");
        }

        function fail() {
            alert("등록 실해");
            location.replace("/articles");
        }

        httpRequest("POST", "/api/articles", body, success, fail);

        /*fetch("/api/articles", {
            method: "post",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        }).then(() => {
            alert("등록완");
            location.replace("/articles");
        });*/
    });
}

function getCookie(key) {
    var result = null;
    var cookie = document.cookie.split(";");
    cookie.some(function (item) {
        item = item.replace(" ", "");
        var dic = item.split("=");

        if (key === dic[0]) {
            result = dic[1];
            return true
        }
    });

    return result;
}

function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method:method,
        headers: {
            Authorization: "Bearer " + localStorage.getItem("access_token"),
            "Content-Type": "application/json",
        },
        body: body,

    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }
        const refresh_token = getCookie("refresh_token");
        if(response.status === 401 && refresh_token) { //권한 거부 and db에 리프레시토큰이 있으면
            fetch("/api/token", {
                method: "POST",
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("access_token"),
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    refreshToken: getCookie("refresh_token"),
                }),
            })
                .then(res => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then(result => {
                    localStorage.setItem("access_token", result.accessToken);
                    httpRequest(method, url, body, success, fail);
                })
                .catch(error => fail());
        } else {
            return fail();
        }
    });
}