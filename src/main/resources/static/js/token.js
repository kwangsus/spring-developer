const token = searchParam('token')

if (token) {
    console.log("token: " + token);
    localStorage.setItem("access_token", token)
} else {
    console.log("token parameter 없음");
}

function searchParam(key) {
    return new URLSearchParams(location.search).get(key);
}