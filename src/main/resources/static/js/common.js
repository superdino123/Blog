var EventUtil = {
    /**
     * 
     * @param {节点对象} element 
     * @param {事件类型} type 
     * @param {事件处理程序} handler 
     */
    addHandler: function(element, type, handler) {
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
    removeHandler: function(element, type, handler) {
        if (element.removeEventListener) {
            element.removeEventListener(type, handler, false);
        } else if (element.detachEvent) {
            element.detachEvent("on" + type, handler);
        } else {
            element["on" + type] = null;
        }
    }
};
