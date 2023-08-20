package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.CardState;
import cn.qkmango.ccms.domain.dto.CardRechargeDto;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.pagination.PageData;
import cn.qkmango.ccms.domain.pagination.Pagination;

/**
 * 校园卡服务
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 20:13
 */
public interface CardService {
    /**
     * 分页查询卡信息
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    PageData<Card> list(Pagination<Card> pagination);

    /**
     * 根据用户ID查询卡详细信息
     *
     * @param account 用户ID
     * @return 详细信息
     */
    Card recordByAccount(Integer account);

    /**
     * 充值
     *
     * @throws UpdateException 更新异常
     */
    R recharge(CardRechargeDto dto) throws UpdateException;

    boolean state(Integer account, CardState state, Integer version);
}
