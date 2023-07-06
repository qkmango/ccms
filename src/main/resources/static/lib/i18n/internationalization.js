class Internationalization {
    /**
     * 将页面中的标签进行国际化
     * 优先级 lang > cookie > defaultLang
     * 如果没有设置lang，则从cookie中获取语言;如果cookie中找不到cookieName对应的值，则使用默认语言defaultLang
     * @param {string} lang 语言
     * @param {string} defaultLang 默认语言
     * @param {string} filePath 语言文件路径
     * @param {string} filePrefix 语言文件前缀
     * @param {string} fileSuffix 语言文件后缀
     * @param {boolean} ignoreEmptyElement 是否忽略空标签
     * @param {string} cookieName 语言cookie名称,如果没有设置lang，则从cookie中获取语言
     * @param {object} prop 国际化属性,如果配置了prop,则不会从语言文件中获取国际化属性,而是从prop中获取
     * @param {function} callback 回调函数
     * @auther qkmango
     * @date 2023-07-01
     * @version 1.0
     */
    constructor({
        lang,
        defaultLang,
        filePath,
        filePrefix,
        fileSuffix,
        ignoreEmptyElement,
        cookieName,
        prop,
        callback,
    }) {
        this.lang = lang || null;
        this.defaultLang = defaultLang || 'zh';
        this.filePath = filePath || '/i18n/';
        this.filePrefix = filePrefix || 'i18n_';
        this.fileSuffix = fileSuffix || '';
        this.ignoreEmptyElement = ignoreEmptyElement || false;
        this.cookieName = cookieName || 'locale';
        this.callback = callback || function () {};
        lang = this.getLang(lang, defaultLang, cookieName);
        this.lang = lang;
        if (prop != null && prop != undefined && typeof prop == 'object') {
            this.prop = prop[lang];
        } else {
            this.prop = null;
        }
    }

    /**
     * 将页面中的标签进行国际化
     */
    convert() {
        let elements = document.querySelectorAll('[i18n]');
        if (this.prop == null) {
            this.getProp().then((data) => {
                this.processI18nData(data, elements);
                this.callback();
            });
        } else {
            this.processI18nData(this.prop, elements);
            this.callback();
        }
    }

    /**
     * 处理国际化数据
     * @param {object} data
     * @param {object} elements
     */
    processI18nData(data, elements) {
        let i18nLang = {};
        if (data != null) {
            i18nLang = data;
        }

        for (let element of elements) {
            switch (element.nodeName) {
                // input 元素设置 value 和 placeholder 属性
                case 'INPUT':
                    if (!this.ignoreEmptyElement || (element.value != null && element.value !== '')) {
                        // 1. 设置 value 属性
                        element.value = i18nLang[element.getAttribute('i18n')];
                        // 2. 设置 placeholder 属性
                        let placeholderKey = element.getAttribute('i18n-placeholder');
                        if (placeholderKey != null && placeholderKey !== '') {
                            element.value = i18nLang[element.getAttribute('i18n')];
                            element.setAttribute('placeholder', i18nLang[placeholderKey]);
                        }

                        //删除i18n属性，防止重复翻译
                        element.removeAttribute('i18n');
                        element.removeAttribute('i18n-placeholder');
                    }
                    break;

                // 其他元素设置 innerHTML 属性
                default:
                    if (!this.ignoreEmptyElement || (element.innerHTML != null && element.innerHTML !== '')) {
                        element.innerHTML = i18nLang[element.getAttribute('i18n')];
                        element.removeAttribute('i18n');
                    }
            }
        }
    }

    /**
     * 获取语言
     * @param {string} lang 指定语言
     * @param {string} defaultLang  默认语言
     * @param {string} cookieName  语言cookie名称, 如果没有设置lang, 则从cookie中获取语言
     * @returns {string} 语言
     */
    getLang(lang, defaultLang, cookieName) {
        if (lang == null || lang == undefined || lang == '') {
            lang = this.getCookie(cookieName);
            //cookie中没有设置lang, 则使用默认语言defaultLang
            if (lang == null) {
                lang = defaultLang;
            }
        }
        return lang;
    }

    /**
     * 获取cookie
     * @param {string} name cookie名称
     * @returns {string} cookie值
     */
    getCookie(name) {
        let arr = document.cookie.split('; ');
        for (let i = 0; i < arr.length; i++) {
            let arr1 = arr[i].split('=');
            if (arr1[0] == name) {
                return arr1[1];
            }
        }
        return null;
    }

    /**
     * 获取国际化语言属性对象
     */
    getProp() {
        let url = this.filePath + this.filePrefix + this.lang + this.fileSuffix + '.json';
        return new Promise((resolve, reject) => {
            let xhr = new XMLHttpRequest();
            xhr.open('GET', url, true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        let prop = JSON.parse(xhr.responseText);
                        this.prop = prop[this.lang];
                        resolve(prop);
                    } else {
                        reject(xhr.status);
                    }
                }
            };
            xhr.send();
        });
    }
}
