package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Message;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.MessageParam;
import cn.qkmango.ccms.domain.vo.MessageVO;
import cn.qkmango.ccms.mvc.dao.MessageDao;
import cn.qkmango.ccms.mvc.service.MessageService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private MessageDao messageDao;

    /**
     * 添加留言
     *
     * @param message 留言
     * @param locale  语言环境
     * @throws InsertException 添加失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(Message message, Locale locale) throws InsertException {
        message.setCreateTime(new Date());
        int affectedRows = messageDao.insert(message);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.message.insert.failure", null, locale));
        }
    }

    /**
     * 删除留言
     *
     * @param message 留言
     * @param locale  语言环境
     * @throws DeleteException 删除失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Message message, Locale locale) throws DeleteException {
        int affectedRows = messageDao.delete(message);
        if (affectedRows != 1) {
            throw new DeleteException(messageSource.getMessage("db.message.delete.failure", null, locale));
        }
    }

    /**
     * 分页查询留言列表
     *
     * @param pagination 分页查询条件
     * @return 留言列表
     */
    @Override
    public R<List<MessageVO>> list(Pagination<MessageParam> pagination) {
        List<MessageVO> list = messageDao.list(pagination);
        int count = messageDao.count();
        return R.success(list).setCount(count);
    }

    /**
     * 查询留言详情
     *
     * @param id 留言id
     * @return 留言详情
     */
    @Override
    public MessageVO detail(String id) {
        return messageDao.detail(id);
    }
}
