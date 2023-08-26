package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.bind.CardState;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CardDao
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 20:23
 */
@Mapper
public interface CardDao {

    /**
     * 获取未分页的记录条数
     *
     * @return 记录条数
     */
    int count();

    /**
     * 获取最新插入的 ID
     *
     * @return 最后插入的ID
     */
    int lastInsertId();

    /**
     * 插入一条校园卡信息
     *
     * @param card 校园卡
     * @return 影响的记录条数
     */
    int insert(Card card);

    /**
     * 更新校园卡状态
     */
    int state(Integer account, CardState state, Integer version);

    /**
     * 查询校园卡详细信息
     *
     * @param account 账户 id
     * @return 校园卡详细信息
     */
    Card getByAccount(Integer account);


    /**
     * 添加卡余额
     *
     * @param account 账号
     * @param amount  充值金额
     * @param version 版本号
     */
    int addBalance(Integer account, Integer amount, Integer version);

    /**
     * 查询校园卡信息
     *
     * @param pagination 分页信息
     * @return 校园卡信息
     */
    List<Card> list(Pagination<Card> pagination);


    /**
     * 消费
     */
    int consume(Long id, Integer amount, Integer version);
}
