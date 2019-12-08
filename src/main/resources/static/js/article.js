/**
 * 全局变量，方法间通信通过该变量调用
 */
var ArticleUtil = {
    createCORSRequest: createCORSRequestMethod,
    getArticleList: getArticleListMethod,
    getPageSize: getPageSizeMethod,
    getArticleById: getArticleByIdMethod,
    getEditArticleById: getEditArticleByIdMethod,
    putArticle: putArticleMethod,
    /**
     * 当前分页显示的文章列表
     */
    articleList: [],
    /**
     * 当前展示文章
     */
    article: {},
    /**
     * 文章分页总数
     */
    articlePageSize: 0,
    /**
     * 当前分页数
     */
    currentPageSize: 1
}

/**
 * public
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
 * public
 * 获取总页数
 */
function getPageSizeMethod() {
    var url = "http://127.0.0.1:8698/Article/articlePageSize";
    var xhr = createCORSRequestMethod("get", url);
    if (xhr) {
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                if ((xhr.status >= 200 && xhr.status < 300) || xhr.status == 304) {
                    ArticleUtil.articlePageSize = JSON.parse(xhr.responseText);
                    loadPageingHtml();
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
 * public
 * 获取所有文档信息列表
 */
function getArticleListMethod(page) {
    var url = "http://127.0.0.1:8698/Article/articleList";
    if (page != null) {
        url = url + "?page=" + page;
    }
    var xhr = createCORSRequestMethod("get", url);
    if (xhr) {
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                if ((xhr.status >= 200 && xhr.status < 300) || xhr.status == 304) {
                    ArticleUtil.articleList = JSON.parse(xhr.responseText);
                    loadArticleListHtml();
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
 * public
 * 根据文章Id获取文章详情
 * @param {文章ID} id 
 */
function getArticleByIdMethod(id) {
    var url = "http://127.0.0.1:8698/Article/articleById";
    if (id != null) {
        url = url + "?id=" + id;
        var xhr = createCORSRequestMethod("get", url);
        if (xhr) {
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if ((xhr.status >= 200 && xhr.status < 300) || xhr.status == 304) {
                        ArticleUtil.article = JSON.parse(xhr.responseText);
                        loadArticleHtml();
                    } else {
                        alert("Request was unsuccessful: " + xhr.status);
                    }
                }
            }
            xhr.send(null);
        }
        return null;
    } else {
        alert("文章ID为空");
    }
}


/**
 * public
 * 根据文章Id获取文章详情
 * @param {文章ID} id 
 */
function getEditArticleByIdMethod(id) {
    var url = "http://127.0.0.1:8698/Article/articleById";
    if (id != null) {
        url = url + "?id=" + id;
        var xhr = createCORSRequestMethod("get", url);
        if (xhr) {
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if ((xhr.status >= 200 && xhr.status < 300) || xhr.status == 304) {
                        ArticleUtil.article = JSON.parse(xhr.responseText);
                        loadEditArticleHtml();
                    } else {
                        alert("Request was unsuccessful: " + xhr.status);
                    }
                }
            }
            xhr.send(null);
        }
        return null;
    } else {
        alert("文章ID为空");
    }
}

/**
 * private
 * 加载文章到HTML
 * 标题ID:detailTitle
 * 更新时间ID:detailTime
 * 阅读数ID:detailReadCount
 * 文章ID:detailContent
 */
function loadArticleHtml() {
    var title = document.getElementById("detailTitle");
    var time = document.getElementById("detailTime");
    var readCount = document.getElementById("detailReadCount");
    var detailContent = document.getElementById("detailContent");
    title.innerText = ArticleUtil.article["title"];
    time.innerText = ArticleUtil.article["modifyTime"];
    readCount.innerText = ArticleUtil.article["readCount"];
    detailContent.innerHTML = marked(ArticleUtil.article["content"]);
}

/**
 * private
 * 加载文章到HTML
 * 标题ID:titleInput
 * 摘要ID:descInput
 * 文章ID:editortextarea
 */
function loadEditArticleHtml() {
    var titleInput = document.getElementById("titleInput");
    var descInput = document.getElementById("descInput");
    var editortextarea = document.getElementById("editortextarea");
    titleInput.value = ArticleUtil.article["title"];
    descInput.value = ArticleUtil.article["articleDesc"];
    editortextarea.value = ArticleUtil.article["content"];
}

/**
 * private
 * 添加分页的HTML内容
 * 根节点=pagingUl
 */
function loadPageingHtml() {
    var pagingUl = document.getElementById("pagingUl");
    var pageSize = ArticleUtil.articlePageSize;
    // 添加pre <ul><li>
    var preLi = document.createElement("li");
    pagingUl.appendChild(preLi);
    // <ul><li><a>
    var preA = document.createElement("a");
    preLi.appendChild(preA);
    preA.setAttribute("aria-label", "Previous");
    preA.setAttribute("role", "button");
    EventUtil.addHandler(preA, "click", function () {
        var prePageSize = ArticleUtil.currentPageSize == 1 ? 1 : ArticleUtil.currentPageSize - 1;
        ArticleUtil.getArticleList(prePageSize);
    });
    // <ul><li><a><span>
    var preSpan = document.createElement("span");
    preSpan.setAttribute("aria-hidden", "true");
    preA.appendChild(preSpan);
    preSpan.innerText = "<<";

    var liList = {};
    for (var i = 1; i <= pageSize; i++) {
        var li = document.createElement("li");
        liList[i] =li;
        pagingUl.appendChild(li);
        var a = document.createElement("a");
        li.appendChild(a);
        a.innerText = i;
        a.setAttribute("role", "button");
        EventUtil.addHandler(a, "click", function () {
            for (var j = 1; j <= pageSize; j++) {
                if (a.innerText == j) {
                    liList[j].setAttribute("class", "active");
                } else {
                    liList[j].setAttribute("class", "");
                }
            }
            ArticleUtil.getArticleList(a.innerText);
        });
    }
    // 添加next <ul><li>
    var nextLi = document.createElement("li");
    pagingUl.appendChild(nextLi);
    // <ul><li><a>
    var nextA = document.createElement("a");
    nextLi.appendChild(nextA);
    nextA.setAttribute("aria-label", "Next");
    nextA.setAttribute("role", "button");
    EventUtil.addHandler(nextA, "click", function () {
        var nextPageSize = ArticleUtil.currentPageSize == ArticleUtil.articlePageSize ? ArticleUtil.currentPageSize : ArticleUtil.currentPageSize + 1;
        ArticleUtil.getArticleList(nextPageSize);
    });
    // <ul><li><a><span>
    var nextSpan = document.createElement("span");
    nextSpan.setAttribute("aria-hidden", "true");
    nextA.appendChild(nextSpan);
    nextSpan.innerText = ">>";
}

/**
 * private
 * 添加文章列表的HTML内容
 * 根节点ID=articleListDiv
 */
function loadArticleListHtml() {
    // 获取根节点, 并清空内容
    var articleListDiv = document.getElementById("articleListDiv");
    articleListDiv.innerHTML = null;
    // 文章数据
    var arts = ArticleUtil.articleList;
    for (i = 0; i < arts.length; i++) {
        // <div><a>
        var a = document.createElement("a");
        a.setAttribute("href", "./detail.html?id=" + arts[i]["id"]);
        a.setAttribute("role", "button");
        articleListDiv.appendChild(a);
        // <div><a><panel>
        var panel = document.createElement("div");
        panel.setAttribute("class", "panel panel-success");
        a.appendChild(panel);
        // head
        var panelheading = document.createElement("div");
        panel.appendChild(panelheading);
        panelheading.setAttribute("class", "panel-heading");
        panelheading.innerText = arts[i]["title"];
        // body
        var panelbody = document.createElement("div");
        panel.appendChild(panelbody);
        panelbody.setAttribute("class", "panel-body");
        panelbody.innerHTML = marked(arts[i]["articleDesc"]);
        // foot
        var panelfoot = document.createElement("div");
        panel.appendChild(panelfoot);
        panelfoot.setAttribute("class", "panel-footer");
        panelfoot.innerHTML = arts[i]["createTime"];
    }
}

/**
 * 保存文章
 */
function putArticleMethod() {
    var editortextarea = document.getElementById("editortextarea");
    var content = editortextarea.innerText;
    var xhr = createCORSRequestMethod("post", "http://127.0.0.1:8698/Article/putArticle");
    if (xhr) {
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                if ((xhr.status >= 200 && xhr.status < 300) || xhr.status == 304) {
                    alert(xhr.responseText);
                } else {
                    alert("Request was unsuccessful: " + xhr.status);
                }
            }
        }
        xhr.setRequestHeader("Content-Type", "application/json");
        var article = {
            "id": UrlUtil.getParamValue("id"),
            "title": document.getElementById("titleInput").value,
            "articleDesc": document.getElementById("descInput").value,
            "content": content
        }
        xhr.send(JSON.stringify(article));
    }
}
