;!function (e) {
    var src = e.document.currentScript.src;
    var groupMatch = /(\S*\/)cardTable(\.min)?\.js(\?.*)?/i.exec(src);
    if (groupMatch && groupMatch.length >= 2 && (!groupMatch[3] || groupMatch[3].indexOf('css=true') !== -1)) {
      layui.link(groupMatch[1] + 'cardTable' + (groupMatch[2] || '') + '.css');
    }
  }(window);
layui.define(['table', 'laypage', 'jquery', 'element'], function (exports) {
    "use strict";
    let MOD_NAME = 'cardTable', $ = layui.jquery, laypage = layui.laypage;
    let _instances = {};  // 记录所有实例
    /* 默认参数 */
    let defaultOption = {
        elem: "#currentTableId",// 构建的模型
        url: "",// 数据 url 连接
        loading: true,//是否加载
        limit: 0, //每页数量默认是每行数量的双倍
        linenum: 4, //每行数量 2,3,4,6
        /*xs（超小屏幕，如手机）、sm（小屏幕，如平板）、md（桌面中等屏幕）、lg（桌面大型屏幕）*/
        xsLinenum: 1, smLinenum: 3, mdLinenum: 4, lgLinenum: 4, currentPage: 1,//当前页
        data: [],       //静态数据
        limits: [20, 40, 60, 80, 100],     //页码
        page: true, //是否分页
        layout: ["prev", "page", "next", "skip", "count", "limit"],//分页控件
        method: 'get', //请求方式
        elemClass: '', //表格样式
        request: {
            pageName: 'page' //页码的参数名称，默认：page
            , limitName: 'limit' //每页数据量的参数名，默认：limit
            , idName: 'id'       //主键名称，默认：id
            , titleName: 'title' //标题名称，默认：title
            , contentName: 'content' //备注名称，默认：content
            , timeName: 'time' //时间名称，默认：time
        }, response: {
            statusName: 'code' //规定数据状态的字段名称，默认：code
            , statusCode: 0 //规定成功的状态码，默认：0
            , msgName: 'msg' //规定状态信息的字段名称，默认：msg
            , countName: 'count' //规定数据总数的字段名称，默认：count
            , dataName: 'data' //规定数据列表的字段名称，默认：data
        }, // 完 成 函 数
        done: function () {
        }, //时间格式化
        timestampToTime: function (timestamp) {
            return layui.util.toDateString(timestamp, 'yyyy-MM-dd HH:mm:ss');
        },
        clickEvent: function(item) {
        },
        iconHtml:'<i class="layui-icon">&#xe702;</i>'
    };
    let card = function (opt) {
        _instances[opt.elem.substring(1)] = this;
        this.reload(opt);
    };
    /** 参数设置 */
    card.prototype.initOptions = function (opt) {
        this.option = $.extend(true, {}, defaultOption, opt);
        if (!this.option.limit || this.option.limit == 0) {
            this.option.limit = this.option.limits[0];
        }
        if (!this.option.limits || this.option.limits.length == 0) {
            this.option.limits = [this.option.limit];
        }
    };
    card.prototype.init = function () {
        let option = this.option;
        let url = option.url;
        let html = "";
        html += option.loading == true ? '<div class="ew-table-loading">' : '<div class="ew-table-loading layui-hide">';
        html += '<i class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop"></i></div>';
        $(option.elem).html(html);
        // 根据请求方式获取数据
        html = "";
        if (!!url) {
            let param = {};
            if (!!option.page) {
                param[option.request.limitName] = option.limit;
                param[option.request.pageName] = option.currentPage;

            }
            if (!!option.where) {
                for (let key in option.where) {
                    param[key] = option.where[key];
                }
            }

            let data = getData(url, param, option.method);
            data = initData(data, option);
            if (data.code !== option.response.statusCode) {
                option.data = [];
                option.count = 0;
            } else {
                option.data = data.data;
                option.count = option.data.length;
                option.count = data[option.response.countName];
            }

        } else {
            if (!option.alldata) {
                option.alldata = option.data;
            }
            if (option.page) {
                let data = [];
                option.count = option.alldata.length;
                for (let i = (option.currentPage - 1) * option.limit; i < option.currentPage * option.limit && i < option.alldata.length; i++) {
                    data.push(option.alldata[i]);
                }
                option.data = data;
            }
        }
        // 根据结果进行相应结构的创建
        if (!!option.data && option.data.length > 0) {
            html = createComponent(option.elem.substring(1), option.data, option);
            html += "<div id='cardpage'></div>";
        } else {
            html = "<p>没有数据</p>";
        }
        $(option.elem).html(html);
        if (option.page) {
            // 初始化分页组件
            laypage.render({
                elem: 'cardpage',
                count: option.count,
                limit: option.limit,
                limits: option.limits,
                curr: option.currentPage,
                layout: option.layout,
                jump: function (obj, first) {
                    option.limit = obj.limit;
                    option.currentPage = obj.curr;
                    if (!first) {
                        _instances[option.elem.substring(1)].reload(option);
                    }
                }
            });
        }

        let icons = document.querySelectorAll('.icon_click');
        for(let i=0;i<icons.length;i++) {
            const icon = icons[i];
            const index = icon.getAttribute('index')
            const item = _instances.currentTableId.option.data[index];
            icon.onclick = function() {
                option.clickEvent(item)
            }
        }
        //执行渲染后的自定义操作
        option.done();
    }
    card.prototype.reload = function (opt) {
        this.initOptions(this.option ? $.extend(true, this.option, opt) : opt);
        this.init();  // 初始化表格
    }

    function createComponent(elem, data, option) {
        let html = "<div class='cloud-card-component'>"
        let content = createCards(elem, data, option);
        // let page = "";
        // content = content + page;
        html += content + "</div>"
        return html;
    }

    /** 创建指定数量的卡片 */
    function createCards(elem, data, option) {
        let content = "<div class='layui-row layui-col-space10'>";
        for (let i = 0; i < data.length; i++) {
            content += createCard(elem, data[i], i, option);
        }
        content += "</div>";
        return content;
    }

    /** 创建一个卡片 */
    function createCard(elem, item, no, option) {
        // console.log(no);
        // console.log(_instances);
        let xsLine = 12 / option.xsLinenum;
        let smLine = 12 / option.smLinenum;
        let mdLine = 12 / option.mdLinenum;
        let lgLine = 12 / option.lgLinenum;
        return `<div class="layui-col-md${mdLine} layui-col-sm${smLine} layui-col-xs${xsLine} layui-col-lg${lgLine} ${option.elemClass}">
                    <div class="layui-card">
                        <div class="layui-card-header layui-bg-green2-tint">
                            ${item.title}
                            ${option.iconHtml===false
                                ?''
                                :'<span class="icon_click table_card_message_icon" index="'+no+'">'+option.iconHtml+'</span>'
                            }
                            <span class="timeago" datetime="${item.time}"></span>
                        </div>
                        <div class="layui-card-body message_content"><span class='datetime'>${option.timestampToTime(item.time)}</span><br/>${item.content}</div>
                    </div>
                </div>`;
    }

    /** 格式化返回参数 */
    function initData(tempData, option) {
        let data = {};
        data.code = tempData[option.response.statusName];
        data.msg = tempData[option.response.msgName];
        data.count = tempData[option.response.countName];
        let dataList = tempData[option.response.dataName];
        data.data = [];
        for (let i = 0; i < dataList.length; i++) {
            let item = {};
            item.id = dataList[i][option.request.idName];
            item.title = dataList[i][option.request.titleName];
            item.content = dataList[i][option.request.contentName];
            item.time = dataList[i][option.request.timeName];
            data.data.push(item);
        }
        return data;
    }

    /** 同 步 请 求 获 取 数 据 */
    function getData(url, param, method) {
        $.ajaxSettings.async = false;
        let redata = null;
        try {
            if (method == "post" || method == "POST") {
                $.ajax({
                    url: url,
                    type: 'post',
                    data: JSON.stringify(param),
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    success: function (res) {
                        redata = res;
                    }
                })
            } else {
                $.get(url, param, function (res) {
                    redata = res
                }, 'json');
            }
        } catch (e) {
            console.error(e)
        }
        return redata;
    }

    //卡片点击事件
    window.cardTableCheckedCard = function (elem, obj) {
        $(obj).addClass('layui-table-click').siblings().removeClass('layui-table-click');
        let item = {};
        item.id = obj.id;
        item.title = $(obj).find('h2')[0].innerHTML;
        item.content = $(obj).find('.project-list-item-text')[0].innerHTML;
        item.time = $(obj).find('.time')[0].innerHTML;
        _instances[elem.id].option.checkedItem = item;
    }
    /** 对外提供的方法 */
    let tt = {
        /* 渲染 */
        render: function (options) {
            return new card(options);
        }, /* 重载 */
        reload: function (id, opt) {
            _instances[id].option.checkedItem = null;
            _instances[id].reload(opt);
        }, /* 获取选中数据 */
        getChecked: function (id) {
            let option = _instances[id].option;
            let data = option.checkedItem;
            let item = {};
            if (!data) {
                return null;
            }
            item[option.request.idName] = data.id;
            item[option.request.titleName] = data.title;
            item[option.request.contentName] = data.content;
            item[option.request.timeName] = data.time;
            return item;
        }, /* 获取表格数据 */
        getAllData: function (id) {
            let option = _instances[id].option;
            let data = [];
            for (let i = 0; i < option.data.length; i++) {
                let item = {};
                item[option.request.idName] = option.data[i].id;
                item[option.request.titleName] = option.data[i].title;
                item[option.request.contentName] = option.data[i].content;
                item[option.request.timeName] = option.data[i].time;
                data.push(item);
            }
            return data;
        },
    }
    exports(MOD_NAME, tt);
})
