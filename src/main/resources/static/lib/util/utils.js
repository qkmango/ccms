var utils = {
    /**
     * 获取URL中的参数
     * @param key
     * @returns {string|null}
     */
    getUrlParam: function (key) {
        const reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
        const r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]);
        return null;
    },
    /**
     * 判断两个对象属性值是否相同
     * userConfig.prop 为字符串数组，指定 a和b 比较哪些属性
     * userConfig.typeConstraint 设置类型是否强制，如果为false，则将数据转为String后比较
     * @param a
     * @param b
     * @param userConfig
     * @returns {boolean}
     */
    isObjectValueEqual: function (a, b, userConfig) {
        if (a === b) return true
        let config = {
            prop: [],
            typeConstraint: true
        };

        if (typeof (userConfig) !== 'undefined') {
            if (typeof (userConfig.prop) !== 'undefined') {
                config.prop = userConfig.prop;
            }
            if (typeof (userConfig.typeConstraint) !== 'undefined') {
                config.typeConstraint = userConfig.typeConstraint;
            }
        }

        //判断是否指定比较的属性
        if (config.prop.length === 0) {
            for (var pro in a) {
                //如果为false说明类型不强制
                if (config.typeConstraint === false) {
                    if (String(a[pro]) !== String(b[pro])) {
                        return false;
                    }
                } else {
                    if (a[pro] !== b[pro]) {
                        return false;
                    }
                }
            }
        } else {
            for (let i = 0; i < config.prop.length; i++) {
                if (config.typeConstraint === false) {
                    if (String(a[config.prop[i]]) !== String(b[config.prop[i]])) {
                        return false;
                    }
                } else {
                    if (a[config.prop[i]] !== b[config.prop[i]]) {
                        return false;
                    }
                }
            }
        }
        return true;
    },

    /**
     * 去除对象中的空属性
     * @param obj
     * @returns {*}
     */
    removeEmptyProperty: function (obj) {
        for (let key in obj) {
            if (obj[key] === null || obj[key] === undefined || obj[key] === '') {
                delete obj[key];
            }
        }
        return obj;
    },

    /**
     * 改变对象属性名
     */
    changeObjKey: function (obj, oldKey, newKey) {
        obj[newKey] = obj[oldKey];
        delete obj[oldKey];
        return obj;
    },

    /**
     * 返回两个对象的不同属性
     * 返回 两个对象均有的属性，且值不同的属性
     * @param master{Object}
     * @param change{Object}
     * @returns {Object|true} 返回两个对象的不同属性，如果没有不同属性，则返回 true
     */
    diffProperty: function (master, change) {
        if (master === change) return true;
        if (master === null || change === null) return true;
        if (master === undefined || change === undefined) return true;

        let diff = {};
        for (let prop in master) {
            if (master[prop] !== change[prop]) {
                if (change[prop] === undefined || change[prop] === null) {
                    continue
                }
                diff[prop] = change[prop];
            }
        }
        if (Object.keys(diff).length === 0) {
            return true;
        }
        return diff;
    },

    /**
     * 随机数
     * 包含边界值
     * @param min 最小值
     * @param max 最大值
     * @returns {*}
     */
    random: function (min, max) {
        return Math.floor(Math.random() * max) + min;
    },

    /**
     * 获取对象的所有属性
     */
    getObjAllProperty: function (obj) {
        let props = [];
        do {
            props = props.concat(Object.getOwnPropertyNames(obj));
        } while (obj = Object.getPrototypeOf(obj));
        return props;
    },

    /**
     * 获取对象属性的个数
     */
    getObjPropertyCount: function (obj) {
        return this.getObjAllProperty(obj).length;
    }

}
