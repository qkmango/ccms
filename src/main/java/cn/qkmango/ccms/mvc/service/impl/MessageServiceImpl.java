package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.database.DeleteException;
import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.domain.dto.MessageDto;
import cn.qkmango.ccms.domain.entity.Message;
import cn.qkmango.ccms.domain.pagination.Flow;
import cn.qkmango.ccms.domain.pagination.PageData;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.MessageVO;
import cn.qkmango.ccms.mvc.dao.MessageDao;
import cn.qkmango.ccms.mvc.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 留言
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-09 15:21
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private ReloadableResourceBundleMessageSource ms;

    @Resource
    private MessageDao dao;

    /**
     * 添加留言
     *
     * @param message 留言
     * @throws InsertException 添加失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(Message message) throws InsertException {
        message.setCreateTime(System.currentTimeMillis());
        int affectedRows = dao.insert(message);
        if (affectedRows != 1) {
            throw new InsertException(ms.getMessage("db.message.insert.failure", null, LocaleContextHolder.getLocale()));
        }
    }

    /**
     * 删除留言
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id, Integer author) throws DeleteException {
        int affectedRows = dao.delete(id, author);
        if (affectedRows != 1) {
            throw new DeleteException(ms.getMessage("db.message.delete.failure", null, LocaleContextHolder.getLocale()));
        }
    }

    /**
     * 分页查询留言列表
     *
     * @param pagination 分页查询条件
     * @return 留言列表
     */
    @Override
    public PageData<MessageVO> list(Pagination<MessageDto> pagination) {
        List<MessageVO> list = dao.list(pagination);
        int count = dao.count();
        return PageData.of(list, count);
    }

    @Override
    public List<Message> list(Flow<MessageDto> flow) {
        List<Message> list = dao.flow(flow);
        return list;
    }

    /**
     * 查询留言详情
     *
     * @param id 留言id
     * @return 留言详情
     */
    @Override
    public Message record(Integer id) {
        return dao.getRecordById(id);
    }
}
