var FieldMapping = {
    consume: {
        type: function (type) {
            switch (type) {
                case 'WATER':
                    return '水费';
                case 'ELECTRIC':
                    return '电费';
                case 'MEAL_EXPENSE':
                    return '餐费';
                case 'PAYMENT':
                    return '缴费';
                case 'RECHARGE':
                    return '充值';
                case 'REFUND':
                    return '退款';
                case 'OTHER':
                    return '其他';
                default :
                    return '未知';
            }
        }
    },
    payment: {
        type: function (type) {
            switch (type) {
                case 'DORMITORY_FEE':
                    return '住宿费';
                case 'OTHER':
                    return '其他';
                default :
                    return '未知';
            }
        },
        state: function (state) {
            switch (state) {
                case 'NOT_START':
                    return '未开始';
                case 'IN_PROGRESS':
                    return '进行中';
                case 'END':
                    return '已结束';
                case 'CANCEL':
                    return '已取消';
                case 'PAUSE':
                    return '已暂停';
                default :
                    return '未知';
            }
        }
    },
    record: {
        state: function (state) {
            switch (state) {
                case 'PAID':
                    return '已支付';
                case 'UNPAID':
                    return '未支付';
                case 'REFUNDED':
                    return '已退款';
                default:
                    return '未知';
            }
        }
    }
}
