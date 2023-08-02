package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.database.DeleteException;
import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.domain.dto.MessageDto;
import cn.qkmango.ccms.domain.entity.Message;
import cn.qkmango.ccms.domain.pagination.Flow;
import cn.qkmango.ccms.domain.pagination.PageData;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.MessageVO;

import java.util.List;

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
     * @throws InsertException 添加失败
     */
    void insert(Message message) throws InsertException;

    /**
     * 删除留言
     *
     * @param id      留言id
     * @param author 留言者账号
     * @throws DeleteException 删除失败
     */
    void delete(Integer id, Integer author) throws DeleteException;

    /**
     * 分页查询留言列表
     *
     * @param pagination 分页查询条件
     * @return 留言列表
     */
    PageData<MessageVO> list(Pagination<MessageDto> pagination);

    List<Message> list(Flow<MessageDto> flow);

    /**
     * 查询留言详情
     *
     * @param id 留言id
     * @return 留言详情
     */
    Message record(Integer id);
}
