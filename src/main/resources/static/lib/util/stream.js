const stream = {
    /**
     * 数组按照某个属性分组，属性值为key，数组为value
     * @param arr
     * @param fun
     * @returns {{}}
     */
    groupBy: function (arr, fun) {
        const groups = {}
        arr.forEach((el) => {
            const group = fun(el)
            groups[group] = groups[group] || []
            groups[group].push(el)
        })
        return groups
    },

    /**
     * 数组中找出指定属性的最大的值
     * @param arr 数组
     * @param fun 指定属性
     * @returns {number|object}
     */
    max: function (arr, fun) {
        let max = arr[0]
        arr.forEach((el) => {
            if (fun(el) > fun(max)) {
                max = el
            }
        })
        return max
    },
    /**
     * 数组中找出指定属性的最小的值
     * @param arr ，返回数组
     * @param fun 指定属性
     * @returns {number|object}
     */
    min: function (arr, fun) {
        let min = arr[0]
        arr.forEach((el) => {
            if (fun(el) < fun(min)) {
                min = el
            }
        })
        return min
    },
    /**
     * 数组中找出指定属性的最大值和最小值
     * 返回一个对象，包含max和min
     * @param arr 数组
     * @param fun 指定属性
     * @returns {{max: number|object, min: number|object}}
     */
    maxAndMin: function (arr, fun) {
        let max = arr[0]
        let min = arr[0]
        arr.forEach((el) => {
            if (fun(el) > fun(max)) {
                max = el
            }
            if (fun(el) < fun(min)) {
                min = el
            }
        })
        return {max, min}
    }
}
