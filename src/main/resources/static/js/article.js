var ArticleMethod = {
    createCORSRequest : createCORSRequestMethod,
    getArticleList : getArticleListMethod,
    articleList : []
}

/**
 * 创建XHR对象
 * @param {HTTP请求类型} method 
 * @param {HTTP请求链接} url 
 */
function createCORSRequestMethod(method, url) {
    var xhr = new XMLHttpRequest();
    if ("withCredentials" in xhr) {
        xhr.open(method, url, true);
    } else if (typeof XDomainRequest != "undefined") {
        xhr = new XDomainRequest();
        xhr.open(method, url);
    } else {
        xhr = null;
    }
    return xhr;
}

/**
 * 获取所有文档信息列表
 */
function getArticleListMethod() {
    var xhr = createCORSRequestMethod("get", "http://127.0.0.1:8698/Article/articleList");
    if (xhr) {
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4) {
                if ((xhr.status >= 200 && xhr.status < 300) || xhr.status == 304) {
                    ArticleMethod.articleList = JSON.parse(xhr.responseText);
                } else {
                  alert("Request was unsuccessful: " + xhr.status);
                }
            }
        }
        xhr.send(null);
    }
    return null;
}

/**
 * 保存文章
 * @param {文章ID} id 
 * @param {文章正文} content 
 */
function putArticleMethod123(id, content) {
    var xhr = createCORSRequestMethod("post", "http://127.0.0.1:8698/Article/addArticle");
    if (xhr) {
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4) {
                if ((xhr.status >= 200 && xhr.status < 300) || xhr.status == 304) {
                    ArticleMethod.articleList = JSON.parse(xhr.responseText);
                } else {
                  alert("Request was unsuccessful: " + xhr.status);
                }
            }
        }
        xhr.setRequestHeader("Content-Type", "application/json");
        var article = {
            "id": id,
            "title": "test21",
            "content": content
        }
        xhr.send(article);
    }
    return null;
}
