/**
 * 判断是否为金额
 * 正数，小数点后最多两位
 * @param str 金额字符串
 * @returns {boolean} 是否为金额
 */
function isAmount(str) {
    if (str === undefined) {
        return false;
    }
    str = str.toString();
    const reg = /^\d+(\.\d{1,2})?$/;
    return reg.test(str);
}

/**
 * 字符串金额扩大指定倍数
 * @param amount 字符串金额
 * @param multi{number} 扩大10^multi倍
 * @returns {null|number} 扩大后的金额
 */
function amountMulti(amount,multi) {
    //检查是否为金额
    if (!isAmount(amount)) {
        console.error('amount is not amount');
        return null;
    }
    //检查扩大的倍数是否为数字
    if (!Number.isInteger(multi)) {
        console.error('multi is not number');
        return null;
    }
    //检查是否>0
    if (multi <= 0) {
        console.error('multi is not > 0');
        return null;
    }
    let split = amount.split('.');
    if (split.length === 1) {
        for (let i = 0; i < multi; i++) {
            amount += '0';
        }
        return amount;
    }

    //整数部分
    let integer = split[0];
    //小数部分
    let decimal = split[1];
    for (let i = 0; i < multi-decimal.length; i++) {
        decimal += '0';
    }
    return integer + decimal;
}

/**
 * 获取字符串金额的小数位数
 * @param amount 字符串金额
 * @returns {null|number|*}
 */
function decimalLength(amount) {
    if (!isAmount(amount)) {
        return null;
    }

    let split = amount.split('.');
    if (split.length === 1) {
        return 0;
    }
    return split[1].length;
}

/**
 * 判断金额字符串是否在指定范围内
 * @param amount{string} 金额字符串
 * @param min{number} 最小值
 * @param max{number} 最大值
 * @returns {boolean} 是否在范围内
 */
function amountInRange(amount, min, max) {
    if (!isAmount(amount)) {
        console.log('amount is not amount');
        return false;
    }
    if (!Number.isInteger(min)) {
        console.log('min is not number');
        return false;
    }
    if (!Number.isInteger(max)) {
        console.log('max is not number');
        return false;
    }
    amount = Number.parseFloat(amount);
    console.log(amount >= amountFormat2Decimal(min) && amount <= amountFormat2Decimal(max))
    return amount >= amountFormat2Decimal(min) && amount <= amountFormat2Decimal(max);
}

/**
 * 将金额字符串格式为2位小数
 * 如 1234.56 -> 1234.56
 * 如 1234.5 -> 1234.50
 * 如 1234 -> 1234.00
 * @param amount{string|number} 金额字符串
 * @returns {string|null} 格式化后的金额
 */
function amountFormat2Decimal(amount) {
    if (amount === undefined) {
        return null;
    }
    amount = amount.toString();
    if (!isAmount(amount)) {
        console.error('amount is not amount');
        return null;
    }
    let split = amount.split('.');
    if (split.length === 1) {
        return amount + '.00';
    }
    if (split[1].length === 1) {
        return amount + '0';
    }
    return Number.parseFloat(amount);
}
