package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.entity.Consume;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.CardInfoParam;
import cn.qkmango.ccms.domain.vo.UserAndCardVO;
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
     * 根据学号查询校园卡信息
     *
     * @param user 学号
     * @return 校园卡信息
     */
    Card getCardByUserId(String user);

    /**
     * 更新校园卡状态
     *
     * @param card 校园卡
     * @return 影响的记录条数
     */
    int state(Card card);

    /**
     * 查询校园卡详细信息
     *
     * @param card 校园卡
     * @return 校园卡详细信息
     */
    UserAndCardVO detail(Card card);

    /**
     * 更新校园卡余额
     *
     * @param consume 消费信息
     * @return
     */
    int updateBalance(Consume consume);

    /**
     * 充值
     *
     * @param card 校园卡（学号，充值金额）
     * @return
     */
    int recharge(Card card);

    /**
     * 查询校园卡信息
     *
     * @param pagination 分页信息
     * @return 校园卡信息
     */
    List<UserAndCardVO> list(Pagination<CardInfoParam> pagination);
}
