let $, layer;

layui.use(['jquery', 'layer'], function () {
    $ = layui.jquery;
    layer = layui.layer;

    //是否正在执行ajax，防止过次点击执行
    let isAjax = false;

    const id = document.querySelector('#id');
    const password = document.querySelector('#password');
    const button = document.querySelector('#login_btn');
    let permissionType = document.querySelector('#permissionType');
    const id_error_tip = document.querySelectorAll('.tip')[0];
    const pwd_error_tip = document.querySelectorAll('.tip')[1];

    id.onblur = checkId;
    password.onblur = checkPassword;

    //检查ID
    function checkId() {
        const result = layui_verify_config.id(id.value);
        if (result === false) {
            id_error_tip.innerText = '';
            return true;
        }
        id_error_tip.innerText = result;
        return false;
    }

    //检查密码
    function checkPassword() {
        const result = layui_verify_config.password(password.value);
        if (result === false) {
            pwd_error_tip.innerText = '';
            return true;
        }
        pwd_error_tip.innerText = result;
        return false;
    }

    //登陆按钮绑定监听时间 登陆
    button.onclick = function () {
        login();
        return;
        if (checkId() & checkPassword()) {
            if (isAjax) {
                console.log('无效');
                return;
            }
            console.log('有效');
            isAjax = true;
            login();
        }
    }


    const locale = Cookies.get('locale');
    if (locale === 'en_US') {
        document.querySelector('#en_US').className = 'this_locale_btn';
    } else {
        document.querySelector('#zh_CN').className = 'this_locale_btn';
    }


    //登陆
    function login() {
        console.log('ajax' + new Date())
        $.ajax({
            url: "../../account/login.do",
            async: false,
            data: {
                id: id.value.trim(),
                password: password.value.trim(),
                permissionType: permissionType.value.trim()
            },
            type: "post",
            dataType: "json",
            success: function (res) {
                if (res.success) {
                    layer.msg(res.message, {icon: 1}, end => window.location.href = "../../index.html");
                    return
                }
                layer.msg(res.message, {icon: 2}, end => isAjax = false);

            },
            error: function (jqXHR, textStatus, errorThrown) {
                layer.msg(jqXHR.status + '', {icon: 2}, end => isAjax = false);
            }
        })
    }


    // gitee认证
    document.querySelector("#gitee_auth").addEventListener("click", function () {
        $.get(`../../authentication/gitee/auth.do?permission=${permissionType.value}&purpose=login`, (res, status) => {
            if (res.success) {
                window.location.href = res.data;
                return;
            }
            layer.msg(res.message, {icon: 2});
        });
    })

})

/**
 * 更改语言
 */
function changeLocale(locale) {
    // setCookie('locale', locale,24*60*60*1000);
    Cookies.set('locale', locale, {expires: 7});
    window.location.reload();
    // $.ajax({
    //     url: '../../system/setLocale.do',
    //     data: {
    //         locale
    //     },
    //     success: function (data) {
    //         if (data.success) {
    //             window.location.reload();
    //         }
    //     }
    // })
}


