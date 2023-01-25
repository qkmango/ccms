package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Message;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.MessageParam;
import cn.qkmango.ccms.domain.vo.MessageVO;

import java.util.List;
import java.util.Locale;

/**
 * 留言
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-09 15:21
 */
public interface MessageService {
    /**
     * 添加留言
     *
     * @param message 留言
     * @param locale  语言环境
     * @throws InsertException 添加失败
     */
    void insert(Message message, Locale locale) throws InsertException;

    /**
     * 删除留言
     *
     * @param message 留言
     * @param locale  语言环境
     * @throws DeleteException 删除失败
     */
    void delete(Message message, Locale locale) throws DeleteException;

    /**
     * 分页查询留言列表
     *
     * @param pagination 分页查询条件
     * @return 留言列表
     */
    R<List<MessageVO>> list(Pagination<MessageParam> pagination);

    /**
     * 查询留言详情
     *
     * @param id 留言id
     * @return 留言详情
     */
    MessageVO detail(String id);
}
