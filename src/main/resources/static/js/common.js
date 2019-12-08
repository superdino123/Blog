var EventUtil = {
    /**
     * 
     * @param {节点对象} element 
     * @param {事件类型} type 
     * @param {事件处理程序} handler 
     */
    addHandler: function (element, type, handler) {
        if (element.addEventListener) {
            element.addEventListener(type, handler, false);
        } else if (element.attachEvent) {
            element.attachEvent("on" + type, handler);
        } else {
            element["on" + type] = handler;
        }
    },
    /**
     * 
     * @param {节点对象} element 
     * @param {事件类型} type 
     * @param {事件处理程序} handler 
     */
    removeHandler: function (element, type, handler) {
        if (element.removeEventListener) {
            element.removeEventListener(type, handler, false);
        } else if (element.detachEvent) {
            element.detachEvent("on" + type, handler);
        } else {
            element["on" + type] = null;
        }
    }
};

var UrlUtil = {
    /**
     * 从URL中解析出参数值
     * @param {参数名称} name 
     */
    getParamValue: function getParamValue(name) {
        const reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        const urlObj = window.location;
        var r = urlObj.href.indexOf('#') > -1 ? urlObj.hash.split("?")[1].match(reg) : urlObj.search.substr(1).match(reg);
        if (r != null) {
            return unescape(r[2]);
        }
        return null;
    }
}