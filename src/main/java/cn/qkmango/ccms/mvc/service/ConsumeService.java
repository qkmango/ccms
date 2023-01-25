package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.QueryException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Consume;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.ConsumeParam;
import cn.qkmango.ccms.domain.vo.ConsumeDetailsVO;

import java.util.List;
import java.util.Locale;

/**
 * 消费
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-29 23:02
 */
public interface ConsumeService {
    /**
     * 添加通过POS机消费记录
     *
     * @param consume 消费记录信息
     * @param locale  语言环境
     * @throws InsertException 插入异常
     * @throws QueryException  查询异常
     */
    void insert(Consume consume, Locale locale) throws InsertException, QueryException;

    /**
     * 分页查询消费记录
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    R<List<Consume>> list(Pagination<ConsumeParam> pagination);

    /**
     * 查询消费记录详情
     *
     * @param consume 消费id和用户id
     * @return 消费记录详情
     */
    ConsumeDetailsVO detail(Consume consume);
}
