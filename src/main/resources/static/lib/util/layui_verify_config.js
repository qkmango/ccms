/**
 * layui表单验证配置对象
 * @author qkmango
 */

const layui_verify_config = {
    //整数
    int: function (value, item) {
        return value % 1 === 0 ? false : "取值为整数"
    },
    // 年级验证
    grade: function (value, item) {
        if (value === '') {
            return false;
        }
        return value >= 2000 && value <= 2100 ? false : '年级取值范围为2000-2100';
    },

    // 日期时间范围验证
    datetime: function (value, item) {
        if (value === '' || value === null || value === undefined) {
            return false;
        }
        if (/^20\d\d-\d\d-\d\d \d\d:\d\d:\d\d - 20\d\d-\d\d-\d\d \d\d:\d\d:\d\d$/.test(value)) {
            return false;
        }
        let split = value.split(' - ');
        if (split[0] >= '2000-01-01 00:00:00' && split[1] <= '2099-12-31 23:59:59') {
            return false;
        }
        return '日期时间取值范围为2000-01-01 00:00:00 - 2099-12-31 23:59:59';
    },

    //学号
    id: function (value, item) {
        if (value === '' || value === null) {
            return false;
        }
        return /^\d{10}$/.test(value) ? false : "账号为10位数字";
    },
    idCard: function (value, item) {
        if (value === '' || value === null) {
            return false;
        }
        return /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/.test(value) === true ? false : "身份证号不合法";
    },
    password: function (value, item) {
        if (value === '' || value === null) {
            return false;
        }
        //必须包含字母、数字和特殊字符,8-16位
        return /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~!@&%#_])[a-zA-Z0-9~!@&%#_]{8,16}$/.test(value) ? false : "密码为8-16位大小写字母数字特殊字符";
    },
    messageContent: function (value, item) {
        return value.length <= 200 ? false : "内容长度不能超过200";
    },

    // 金额
    price: function (value, item) {
        return /^\d+$/.test(value) ? false : "金额必须大于0";
    },
    payment_id: function (value, item) {
        if (value === '' || value === null || value === undefined) {
            return false;
        }
        return /^\d+$/.test(value) ? false : "缴费项目ID必须为数字";
    },
    captcha: function (value, item) {
        if (value === '' || value === null || value === undefined) {
            return false;
        }
        return /^[A-Za-z0-9]{5}$/.test(value) ? false : "验证码为5位数字和字母";
    }

}
