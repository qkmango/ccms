# 🚀 CCMS一卡通管理系统 Web服务端

> 👨‍💻 芒果小洛 <br/>
> 💻 [qkmango.cn](http://qkmango.cn)

![logo](doc/README/logo.svg)

## 色彩

| 名称                | 值       | 描述                           | 预览                                                                                     |
|-------------------|---------|------------------------------|----------------------------------------------------------------------------------------|
| --blue            | #0969DA | theme主题色 / my自定义 / 替换layui蓝色 | ![](https://img.shields.io/badge/-theme%20----blue-%230969DA%09?style=for-the-badge)   |
| --green           | #00965e | theme主题色 / my自定义 / 替换layui绿色 | ![](https://img.shields.io/badge/-theme%20----green-%2300965e?style=for-the-badge)     |
| --green2          | #2da44e | my自定义                        | ![](https://img.shields.io/badge/-theme%20----green2-%232da44e?style=for-the-badge)    |
| layui green       | #5FB878 | layui 官方默认表单绿色               | ![](https://img.shields.io/badge/-layui%20green-%235FB878?style=for-the-badge)         |
| layui red/danger  | #FF5722 | layui 官方默认 red/danger        | ![](https://img.shields.io/badge/-layui%20red%2Fdanger-%23FF5722?style=for-the-badge)  |
| layui orange/warm | #FFB800 | layui 官方默认 orange/warm       | ![](https://img.shields.io/badge/-layui%20orange%2Fwarm-%23FFB800?style=for-the-badge) |
| cascader success  | #67C23A | cascader js组件内部颜色            | ![](https://img.shields.io/badge/-cascader%20success-%2367C23A?style=for-the-badge)    |
| cascader warning  | #E6A23C | cascader js组件内部颜色            | ![](https://img.shields.io/badge/-cascader%20warning-%23E6A23C?style=for-the-badge)    |
| cascader danger   | #F56C6C | cascader js组件内部颜色            | ![](https://img.shields.io/badge/-cascader%20danger-%23F56C6C?style=for-the-badge)     |

## 接口规范

每个领域内获取一条记录都要通过 id 进行获取，返回结果包含此记录的所有信息<br>

- api 接口命名为 `xxx/one/record.do`
- controller 层 方法命名为 `getRecordById()`
- service 层 方法命名为 `getRecordById()`
- dao 层 方法命名为 `getRecordById()`

## 前端规范
### 页面命名与跳转规范

#### 1. 查看某个领域信息的时候，可以分为 2 种情况：

- 查看某个领域记录的所有信息
    - HTML页面命名为 `/xxx/sub/record.html`
    - 通过右侧弹窗打开
- 查看某个领域记录的综合信息
    - HTML页面命名为 `/xxx/sub/detail.html`
    - 通过右侧弹窗打开
- 如果信息较为复杂或者需要有其他的操作
    - HTML页面命名为 `/xxx/sub/detail.html`
    - 需要单独打开一个 tab

#### 2. 页面间值传递
1. 在页面内通过弹窗打开的时候，id 通过 url 传递，例如：`/xxx/sub/record.html?id=1`
2. 在页面内通过 tab 打开的时候，id 通过 localStorage 传递，例如：
    ````js
    //===原页面===
    //关闭之前打开的详情页
    const tabCloseBtn = window.top.document.querySelector(
            'li[lay-id="page/admin/account/detail.html"]>i'
    );
    if (tabCloseBtn != null) {
      tabCloseBtn.click();
    }
    
    //将id存入localStorage，并以跳转的页面 hash 为 key
    sessionStorage.setItem('#/page/admin/account/detail.html', data.id.toString());
    miniTab.openNewTabByIframe({
      href: 'page/admin/account/detail.html',
      title: '账户详情',
    });
   
    //===跳转的页面===
    //从localStorage中取出id
    const id = sessionStorage.getItem(window.top.location.hash);
    //清除localStorage中的id
    sessionStorage.removeItem(window.top.location.hash);
    ````
   

#### ajax 请求规范
所有页面必须有引入 `/lib/util/common.js` 文件，包含了一些请求相关的方法
````js
//返回完整的请求地址
common.url(api)

// jquery ajax请求设置, 会自动添加token,允许跨域携带cookie,以及请求成功和失败的处理
common.ajaxSetup(jquery)
````
在layui加载完成后必须调用 `common.ajaxSetup(jquery)` 方法设置ajax，否则会出现跨域问题，并且 layui 数据表格的请求配置无法配置相关跨域的设置，只能通过全局配置 jquery ajax 来解决
ajax 请求示例
````js
$.ajax({
    url: common.url('/department/one/record.do?id='+id),
    success: function (res) {
        if (res.success) {
            form.val('record', res.data);
            return;
        }
        layer.msg(res.message, {icon: 2});
    },
})
````