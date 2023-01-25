/**
 * 日期时间相关
 * 1667059015000 --> 2022-10-29 23:56:55
 * @author qkmango
 * @version 1.0
 * @date 2022-12-05
 */

/**
 * 将时间戳转换为日期格式
 * @param timestamp{number|string} 时间戳
 * @returns {string|null} 日期格式
 */
function timestampToTime(timestamp) {

    //空返回
    if (timestamp == null || timestamp === '' || timestamp === undefined) {
        return null;
    }

    //如果传的是字符串则转为数字
    if (typeof (timestamp) === 'string') {
        timestamp = parseInt(timestamp);
    }

    //时间戳为10位需*1000，时间戳为13位的话不需乘1000
    if (timestamp.toString().length === 10) {
        timestamp = timestamp * 1000;
    }

    const date = new Date(timestamp);
    const Y = date.getFullYear() + '-';
    const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    const D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
    const h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
    const m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
    const s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
    return Y + M + D + h + m + s;
}


/**
 * 日期转时间戳
 * 2022-10-29 23:56:55 -> 1667059015000
 * @param datetime {string} 日期时间
 * @returns {number|null} 时间戳
 */
function timeToTimestamp(datetime) {
    if (datetime === '' || datetime === null || datetime === undefined) {
        return null;
    }
    return new Date(datetime).getTime();
}
