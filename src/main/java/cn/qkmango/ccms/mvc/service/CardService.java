package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.pagination.Pagination;

import java.util.List;
import java.util.Locale;

/**
 * 校园卡服务
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 20:13
 */
public interface CardService {
    /**
     * 添加一个卡，开卡
     *
     * @param user   学生
     * @param locale 语言环境
     * @return 插入异常
     * @throws InsertException 插入异常
     */
//    Card insert(User user, Locale locale) throws InsertException;

    /**
     * 分页查询卡信息
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    R<List<Card>> list(Pagination<Card> pagination);

    /**
     * 更新卡状态
     *
     * @param card   校园卡（user，lock）
     * @param locale 语言环境
     * @throws UpdateException 更新异常
     */
//    void state(Card card, Locale locale) throws UpdateException;

    /**
     * 根据用户ID查询卡详细信息
     *
     * @param account 用户ID
     * @return 详细信息
     */
    Card detail(String account);

    /**
     * 充值
     *
     * @param card   校园卡
     * @param locale 语言环境
     * @throws UpdateException 更新异常
     */
    void recharge(Card card, Locale locale) throws UpdateException;
}
