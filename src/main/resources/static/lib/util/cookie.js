/**
 * 获取cookie
 * @param key cookie名
 * @returns {string|null} 存在则返回值，不存在则返回 null
 */
function getCookie(key) {
    if (key === undefined) return null;
    key += '=';
    let ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i].trim();
        if (c.indexOf(key) === 0) {
            return c.substring(key.length, c.length);
        }
    }
    return null;
}

/**
 * 获取cookie，并转为对象
 * @param key cookie 名
 * @returns {null|any}
 */
function getCookieForObject(key) {
    let value = getCookie(key);
    if (value === null) {
        return null;
    }
    return JSON.parse(value);
}

/**
 * 添加cookie
 * @param key cookie名
 * @param value cookie值
 * @param maxAge 有效时长（秒），默认为 -1，即浏览器关闭失效
 */
function setCookie(key, value, maxAge) {
    if (maxAge === undefined) {
        document.cookie = key + '=' + value + '; Max-Age=-1';
    }
    document.cookie = key + '=' + value + '; Max-Age=' + maxAge;
}

/**
 * 添加cookie
 * @param key cookie名
 * @param obj cookie值（对象）
 * @param maxAge 有效时长（秒），默认为 -1，即浏览器关闭失效
 */
function setCookieForObject(key, obj, maxAge) {
    if (maxAge === undefined) {
        document.cookie = key + '=' + JSON.stringify(obj) + '; Max-Age=-1';
    }
    document.cookie = key + '=' + JSON.stringify(obj) + '; Max-Age=' + maxAge;
}

/**
 * 判断cookie是否存在
 * @param key cookie名
 * @returns {boolean} true存在 false不存在
 */
function existCookie(key) {
    if (key === undefined) return false;
    const value = getCookie(key);
    if (value === null) {
        return false;
    }
    return true;
}
